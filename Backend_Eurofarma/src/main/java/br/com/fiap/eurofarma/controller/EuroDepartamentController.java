package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroDepartament;
import br.com.fiap.eurofarma.dao.repository.EuroDepartamentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departaments")
public class EuroDepartamentController {

    @Autowired
    private EuroDepartamentRepository euroDepartamentRepository;

    @GetMapping("/listAll")
    public List<EuroDepartament> listAll() {
        return euroDepartamentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroDepartament> getById(@PathVariable Long id) {
        Optional<EuroDepartament> euroDepartament = euroDepartamentRepository.findById(id);
        return euroDepartament.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EuroDepartament euroDepartament) {
        try {
            EuroDepartament savedDepartament = euroDepartamentRepository.save(euroDepartament);
            return ResponseEntity.ok(savedDepartament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving departament: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroDepartament euroDepartamentDetails) {
        Optional<EuroDepartament> optionalDepartament = euroDepartamentRepository.findById(id);
        if (optionalDepartament.isPresent()) {
            EuroDepartament existingDepartament = optionalDepartament.get();
            existingDepartament.setDepartment(euroDepartamentDetails.getDepartment());
            existingDepartament.setDescription(euroDepartamentDetails.getDescription());

            try {
                EuroDepartament updatedDepartament = euroDepartamentRepository.save(existingDepartament);
                return ResponseEntity.ok(updatedDepartament);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating departament: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroDepartamentRepository.existsById(id)) {
            try {
                euroDepartamentRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
