package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroComplianceTitle;
import br.com.fiap.eurofarma.dao.repository.EuroComplianceTitleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/complianceTitles")
public class EuroComplianceTitleController {

    @Autowired
    private EuroComplianceTitleRepository euroComplianceTitleRepository;

    @GetMapping("/listAll")
    public List<EuroComplianceTitle> listAll() {
        return euroComplianceTitleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroComplianceTitle> getById(@PathVariable Long id) {
        Optional<EuroComplianceTitle> euroComplianceTitle = euroComplianceTitleRepository.findById(id);
        return euroComplianceTitle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EuroComplianceTitle euroComplianceTitle) {
        try {
            EuroComplianceTitle savedTitle = euroComplianceTitleRepository.save(euroComplianceTitle);
            return ResponseEntity.ok(savedTitle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving compliance title: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroComplianceTitle euroComplianceTitleDetails) {
        Optional<EuroComplianceTitle> optionalTitle = euroComplianceTitleRepository.findById(id);
        if (optionalTitle.isPresent()) {
            EuroComplianceTitle existingTitle = optionalTitle.get();
            existingTitle.setTitle(euroComplianceTitleDetails.getTitle());
            existingTitle.setContent(euroComplianceTitleDetails.getContent());
            existingTitle.setVersion(euroComplianceTitleDetails.getVersion());

            try {
                EuroComplianceTitle updatedTitle = euroComplianceTitleRepository.save(existingTitle);
                return ResponseEntity.ok(updatedTitle);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating compliance title: " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroComplianceTitleRepository.existsById(id)) {
            try {
                euroComplianceTitleRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
