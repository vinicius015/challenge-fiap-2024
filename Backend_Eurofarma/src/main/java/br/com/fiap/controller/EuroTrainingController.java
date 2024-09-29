package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroTraining;
import br.com.fiap.dao.repository.EuroTrainingRepository;
import br.com.fiap.dto.EuroTrainingRequestCreateDto;
import br.com.fiap.dto.EuroTrainingRequestUpdateDto;
import br.com.fiap.dto.EuroTrainingResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class EuroTrainingController {

    @Autowired
    private EuroTrainingRepository euroTrainingRepository;

    @GetMapping
    public ResponseEntity<List<EuroTrainingResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroTrainingRepository.findAll().stream()
                        .map(training -> new EuroTrainingResponseDto().toDto(training))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroTrainingResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroTrainingRepository.findById(id)
                        .map(training -> new EuroTrainingResponseDto().toDto(training))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroTrainingRequestCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new EuroTrainingResponseDto().toDto(
                        euroTrainingRepository.save(
                                dto.toModel()
                        )
                )
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroTrainingRequestUpdateDto dto) {
        if (!euroTrainingRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        EuroTraining updatedTraining = dto.toModel();
        updatedTraining.setId(id);

        return ResponseEntity.ok().body(
                new EuroTrainingResponseDto().toDto(
                        euroTrainingRepository.save(updatedTraining)
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroTrainingRepository.existsById(id)) {
            euroTrainingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
