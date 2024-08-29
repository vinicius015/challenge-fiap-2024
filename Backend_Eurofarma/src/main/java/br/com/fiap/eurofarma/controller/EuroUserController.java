package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroPosition;
import br.com.fiap.eurofarma.dao.been.EuroUser;
import br.com.fiap.eurofarma.dao.repository.EuroPositionRepository;
import br.com.fiap.eurofarma.dao.repository.EuroUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class EuroUserController {

    @Autowired
    private EuroUserRepository euroUserRepository;
    @Autowired
    private EuroPositionRepository euroPositionRepository;

    @GetMapping("/listAll")
    public List<EuroUser> listAll() {
        return euroUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroUser> getById(@PathVariable Long id) {
        Optional<EuroUser> euroUser = euroUserRepository.findById(id);
        return euroUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EuroUser euroUser) {
        if (euroUser.getPosition() == null || euroUser.getPosition().getId() == null) {
            return ResponseEntity.badRequest().body("Posição é obrigatória.");
        }

        // Verifique se a posição existe no banco de dados
        Optional<EuroPosition> existingPosition = euroPositionRepository.findById(euroUser.getPosition().getId());
        if (existingPosition.isEmpty()) {
            return ResponseEntity.badRequest().body("Posição não encontrada.");
        } else {
            euroUser.setPosition(existingPosition.get());
        }

        try {
            EuroUser savedUser = euroUserRepository.save(euroUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroUser euroUserDetails) {
        Optional<EuroUser> euroUser = euroUserRepository.findById(id);
        if (euroUser.isPresent()) {
            EuroUser existingUser = euroUser.get();
            existingUser.setName(euroUserDetails.getName());
            existingUser.setEmail(euroUserDetails.getEmail());
            // Update other fields as needed

            try {
                EuroUser updatedUser = euroUserRepository.save(existingUser);
                return ResponseEntity.ok(updatedUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroUserRepository.existsById(id)) {
            try {
                euroUserRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
