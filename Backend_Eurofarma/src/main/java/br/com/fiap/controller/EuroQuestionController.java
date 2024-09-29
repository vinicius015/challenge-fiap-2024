package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroQuestion;
import br.com.fiap.dao.been.EuroTraining;
import br.com.fiap.dao.repository.EuroQuestionRepository;
import br.com.fiap.dao.repository.EuroTrainingRepository;
import br.com.fiap.dto.EuroQuestionRequestCreateDto;
import br.com.fiap.dto.EuroQuestionRequestUpdateDto;
import br.com.fiap.dto.EuroQuestionResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class EuroQuestionController {

    @Autowired
    private EuroQuestionRepository euroQuestionRepository;

    @Autowired
    private EuroTrainingRepository euroTrainingRepository;

    @GetMapping
    public ResponseEntity<List<EuroQuestionResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroQuestionRepository.findAll().stream()
                        .map(question -> new EuroQuestionResponseDto().toDto(question))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroQuestionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroQuestionRepository.findById(id)
                        .map(question -> new EuroQuestionResponseDto().toDto(question))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroQuestionRequestCreateDto dto) {
        if (dto.getTrainingId() == null) {
            return ResponseEntity.badRequest().body("Treinamento é obrigatório.");
        }

        Optional<EuroTraining> training = euroTrainingRepository.findById(dto.getTrainingId());
        if (training.isEmpty()) {
            return ResponseEntity.badRequest().body("Treinamento não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new EuroQuestionResponseDto().toDto(
                        euroQuestionRepository.save(
                                dto.toModel(training.get())
                        )
                )
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroQuestionRequestUpdateDto dto) {
        if (!euroQuestionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroTraining> existingTraining = euroTrainingRepository.findById(dto.getTrainingId());
        if (existingTraining.isEmpty()) {
            return ResponseEntity.badRequest().body("Treinamento não encontrado.");
        }

        EuroQuestion updatedQuestion = dto.toModel(existingTraining.get());
        updatedQuestion.setId(id);

        return ResponseEntity.ok().body(
                new EuroQuestionResponseDto().toDto(
                        euroQuestionRepository.save(updatedQuestion)
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroQuestionRepository.existsById(id)) {
            euroQuestionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
