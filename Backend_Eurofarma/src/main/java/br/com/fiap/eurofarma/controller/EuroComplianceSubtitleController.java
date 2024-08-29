package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroComplianceSubtitle;
import br.com.fiap.eurofarma.dao.been.EuroComplianceTitle;
import br.com.fiap.eurofarma.dao.repository.EuroComplianceSubtitleRepository;
import br.com.fiap.eurofarma.dao.repository.EuroComplianceTitleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/complianceSubtitles")
public class EuroComplianceSubtitleController {
    @Autowired
    private EuroComplianceSubtitleRepository euroComplianceSubtitleRepository;

    @Autowired
    private EuroComplianceTitleRepository euroComplianceTitleRepository;

    @GetMapping("/listAll")
    public ResponseEntity<List<EuroComplianceSubtitle>> listAll() {
        List<EuroComplianceSubtitle> subtitles = euroComplianceSubtitleRepository.findAll();
        return ResponseEntity.ok(subtitles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroComplianceSubtitle> getById(@PathVariable Long id) {
        Optional<EuroComplianceSubtitle> subtitle = euroComplianceSubtitleRepository.findById(id);
        return subtitle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EuroComplianceSubtitle euroComplianceSubtitle) {
        // Verifique se o título de conformidade está presente
        if (euroComplianceSubtitle.getComplianceTitle() == null || euroComplianceSubtitle.getComplianceTitle().getId() == null) {
            return ResponseEntity.badRequest().body("Título de conformidade é obrigatório.");
        }

        // Verifique se o título de conformidade existe no banco de dados
        Optional<EuroComplianceTitle> existingTitle = euroComplianceTitleRepository.findById(euroComplianceSubtitle.getComplianceTitle().getId());
        if (existingTitle.isEmpty()) {
            return ResponseEntity.badRequest().body("Título de conformidade não encontrado.");
        } else {
            euroComplianceSubtitle.setComplianceTitle(existingTitle.get());
        }

        // Salve a entidade EuroComplianceSubtitle
        try {
            EuroComplianceSubtitle savedSubtitle = euroComplianceSubtitleRepository.save(euroComplianceSubtitle);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSubtitle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o subtítulo de conformidade: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<EuroComplianceSubtitle> update(@PathVariable Long id, @Valid @RequestBody EuroComplianceSubtitle euroComplianceSubtitleDetails) {
        return euroComplianceSubtitleRepository.findById(id)
                .map(existingSubtitle -> {
                    existingSubtitle.setSubtitle(euroComplianceSubtitleDetails.getSubtitle());
                    existingSubtitle.setContent(euroComplianceSubtitleDetails.getContent());
                    existingSubtitle.setComplianceTitle(euroComplianceSubtitleDetails.getComplianceTitle());
                    EuroComplianceSubtitle updatedSubtitle = euroComplianceSubtitleRepository.save(existingSubtitle);
                    return ResponseEntity.ok(updatedSubtitle);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroComplianceSubtitleRepository.existsById(id)) {
            euroComplianceSubtitleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
