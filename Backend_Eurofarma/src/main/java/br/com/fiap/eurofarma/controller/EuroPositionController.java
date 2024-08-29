package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroDepartament;
import br.com.fiap.eurofarma.dao.been.EuroPosition;
import br.com.fiap.eurofarma.dao.repository.EuroDepartamentRepository;
import br.com.fiap.eurofarma.dao.repository.EuroPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/positions")
public class EuroPositionController {

    @Autowired
    private EuroPositionRepository euroPositionRepository;

    @Autowired
    private EuroDepartamentRepository euroDepartamentRepository;

    @GetMapping("/listAll")
    public List<EuroPosition> listAll() {
        return euroPositionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroPosition> getById(@PathVariable Long id) {
        Optional<EuroPosition> euroPosition = euroPositionRepository.findById(id);
        return euroPosition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EuroPosition euroPosition) {
        if (euroPosition.getDepartament() == null || euroPosition.getDepartament().getId() == null) {
            return ResponseEntity.badRequest().body("Departamento é obrigatório.");
        }

        // Verifique se o departamento existe no banco de dados
        Optional<EuroDepartament> existingDepartament = euroDepartamentRepository.findById(euroPosition.getDepartament().getId());
        if (existingDepartament.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }else {
            euroPosition.setDepartament(existingDepartament.get());
        }

        EuroPosition savedPosition = euroPositionRepository.save(euroPosition);
        return ResponseEntity.ok(savedPosition);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EuroPosition> update(@PathVariable Long id, @RequestBody EuroPosition euroPositionDetails) {
        Optional<EuroPosition> optionalEuroPosition = euroPositionRepository.findById(id);
        if (optionalEuroPosition.isPresent()) {
            EuroPosition existingPosition = optionalEuroPosition.get();

            // Update fields
            existingPosition.setPosition(euroPositionDetails.getPosition());
            existingPosition.setDepartament(euroPositionDetails.getDepartament());
            // Note: You might need to update users as well if needed

            EuroPosition updatedPosition = euroPositionRepository.save(existingPosition);
            return ResponseEntity.ok(updatedPosition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroPositionRepository.existsById(id)) {
            euroPositionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
