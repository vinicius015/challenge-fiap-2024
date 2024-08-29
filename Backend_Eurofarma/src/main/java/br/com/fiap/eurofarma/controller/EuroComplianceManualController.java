package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.controller.dto.EuroComplianceManualDTO;
import br.com.fiap.eurofarma.dao.been.EuroComplianceManual;
import br.com.fiap.eurofarma.dao.been.EuroComplianceManualKey;
import br.com.fiap.eurofarma.dao.been.EuroComplianceTitle;
import br.com.fiap.eurofarma.dao.been.EuroUser;
import br.com.fiap.eurofarma.dao.repository.EuroComplianceManualRepository;
import br.com.fiap.eurofarma.dao.repository.EuroComplianceTitleRepository;
import br.com.fiap.eurofarma.dao.repository.EuroUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/complianceManuals")
public class EuroComplianceManualController {

    @Autowired
    private EuroComplianceManualRepository euroComplianceManualRepository;
    @Autowired
    private EuroUserRepository euroUserRepository;
    @Autowired
    private EuroComplianceTitleRepository euroComplianceTitleRepository;
    @GetMapping("/listAll")
    public ResponseEntity<List<EuroComplianceManual>> listAll() {
        List<EuroComplianceManual> manuals = euroComplianceManualRepository.findAll();
        return ResponseEntity.ok(manuals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroComplianceManual> getById(@PathVariable EuroComplianceManualKey id) {
        Optional<EuroComplianceManual> euroComplianceManual = euroComplianceManualRepository.findById(id);
        return euroComplianceManual.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<EuroComplianceManual> save(@Valid @RequestBody EuroComplianceManualDTO dto) {
        EuroUser user = euroUserRepository.findById(dto.getUserId()).orElseThrow();
        EuroComplianceTitle complianceTitle = euroComplianceTitleRepository.findById(dto.getComplianceTitleId()).orElseThrow();

        EuroComplianceManual manual = EuroComplianceManual.builder()
                .id(new EuroComplianceManualKey(dto.getUserId(), dto.getComplianceTitleId()))
                .user(user)
                .complianceTitle(complianceTitle)
                .build();

        EuroComplianceManual savedManual = euroComplianceManualRepository.save(manual);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManual);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<EuroComplianceManual> update(@PathVariable EuroComplianceManualKey id, @Valid @RequestBody EuroComplianceManual euroComplianceManualDetails) {
        return euroComplianceManualRepository.findById(id)
                .map(existingManual -> {
                    existingManual.setUser(euroComplianceManualDetails.getUser());
                    existingManual.setComplianceTitle(euroComplianceManualDetails.getComplianceTitle());
                    EuroComplianceManual updatedManual = euroComplianceManualRepository.save(existingManual);
                    return ResponseEntity.ok(updatedManual);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable EuroComplianceManualKey id) {
        if (euroComplianceManualRepository.existsById(id)) {
            euroComplianceManualRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
