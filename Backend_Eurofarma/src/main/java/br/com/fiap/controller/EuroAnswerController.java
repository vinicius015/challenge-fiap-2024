package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroAnswer;
import br.com.fiap.dao.been.EuroQuestion;
import br.com.fiap.dao.repository.EuroAnswerRepository;
import br.com.fiap.dao.repository.EuroQuestionRepository;
import br.com.fiap.dto.EuroAnswerRequestCreateDto;
import br.com.fiap.dto.EuroAnswerRequestUpdateDto;
import br.com.fiap.dto.EuroAnswerResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/answers")
public class EuroAnswerController {

    @Autowired
    private EuroAnswerRepository euroAnswerRepository;

    @Autowired
    private EuroQuestionRepository euroQuestionRepository;

    @GetMapping
    public ResponseEntity<List<EuroAnswerResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroAnswerRepository.findAll().stream()
                        .map(answer -> new EuroAnswerResponseDto().toDto(answer))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroAnswerResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroAnswerRepository.findById(id)
                        .map(answer -> new EuroAnswerResponseDto().toDto(answer))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroAnswerRequestCreateDto dto) {
        Optional<EuroQuestion> question = euroQuestionRepository.findById(dto.getQuestionId());
        if (question.isEmpty()) {
            return ResponseEntity.badRequest().body("Questão não encontrada.");
        }

        EuroAnswer savedAnswer = euroAnswerRepository.save(dto.toModel(question.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new EuroAnswerResponseDto().toDto(savedAnswer));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroAnswerRequestUpdateDto dto) {
        if (!euroAnswerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroQuestion> exisEuroQuestion = euroQuestionRepository.findById(dto.getQuestionId());
        if (exisEuroQuestion.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }

        EuroAnswer updatedAnswer = dto.toModel(exisEuroQuestion.get());
        updatedAnswer.setId(id);

        return ResponseEntity.ok().body(
                new EuroAnswerResponseDto().toDto(
                        euroAnswerRepository.save(updatedAnswer)
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroAnswerRepository.existsById(id)) {
            euroAnswerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
