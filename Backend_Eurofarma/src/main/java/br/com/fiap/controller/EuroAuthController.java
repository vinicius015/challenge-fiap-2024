package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroAuth;
import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.repository.EuroAuthRepository;
import br.com.fiap.dao.repository.EuroUserRepository;
import br.com.fiap.dto.EuroAuthRequestDto; // Adicione este DTO para as requisições
import br.com.fiap.dto.EuroAuthRequestUpdateDto;
import br.com.fiap.dto.EuroAuthResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auths")
public class EuroAuthController {

    @Autowired
    private EuroAuthRepository euroAuthRepository;

    @Autowired
    private EuroUserRepository euroUserRepository;

    @GetMapping
    public ResponseEntity<List<EuroAuthResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroAuthRepository.findAll().parallelStream()
                        .map(euroAuth -> new EuroAuthResponseDto().toDto(euroAuth))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroAuthResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroAuthRepository.findById(id)
                        .map(euroAuth -> new EuroAuthResponseDto().toDto(euroAuth))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }
    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<EuroAuthResponseDto> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroAuthRepository.findByEuroUserId(id)
                        .map(euroAuth -> new EuroAuthResponseDto().toDto(euroAuth))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroAuthRequestDto dto) {
        if (dto.getEuroUserId() == null) {
            return ResponseEntity.badRequest().body("Usuário é obrigatório.");
        }

        Optional<EuroUser> existingUser = euroUserRepository.findById(dto.getEuroUserId());
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new EuroAuthResponseDto().toDto(
                        euroAuthRepository.save(
                                dto.toModel(
                                        existingUser.get()
                                )
                        )
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a autenticação: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroAuthRequestDto dto) {
        if (!euroAuthRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroUser> existingUser = euroUserRepository.findById(dto.getEuroUserId());
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        EuroAuth euroAuth = dto.toModel(existingUser.get());
        euroAuth.setId(id);

        try {
            EuroAuth updatedAuth = euroAuthRepository.save(euroAuth);
            return ResponseEntity.ok(new EuroAuthResponseDto().toDto(updatedAuth));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a autenticação: " + e.getMessage());
        }
    }
    @PutMapping("/alterByUserId/{id}")
    public ResponseEntity<?> alterByUserId(@PathVariable Long id, @Valid @RequestBody EuroAuthRequestUpdateDto dto) {
        Optional<EuroAuth> euroAuthNotAlter = euroAuthRepository.findByEuroUserId(id);
        if (euroAuthNotAlter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AuthId não encontrado");
        }

        Optional<EuroUser> existingUser = euroUserRepository.findById(id);
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        EuroAuth euroAuth = dto.toModel(existingUser.get());
        euroAuth.setId(euroAuthNotAlter.get().getId());

        try {
            EuroAuth updatedAuth = euroAuthRepository.save(euroAuth);
            return ResponseEntity.ok(new EuroAuthResponseDto().toDto(updatedAuth));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a autenticação: " + e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroAuthRepository.existsById(id)) {
            try {
                euroAuthRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteByUserId/{id}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long id) {
        if (euroAuthRepository.existsByEuroUserId(id)) {
            try {
                euroAuthRepository.deleteByEuroUserId(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
