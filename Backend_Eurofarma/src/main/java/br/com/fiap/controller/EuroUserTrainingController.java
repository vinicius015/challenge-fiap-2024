package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroUserTraining;
import br.com.fiap.dao.been.EuroTraining;
import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.repository.EuroUserTrainingRepository;
import br.com.fiap.dao.repository.EuroTrainingRepository;
import br.com.fiap.dao.repository.EuroUserRepository;
import br.com.fiap.dto.EuroUserTrainingRequestCreateDto;
import br.com.fiap.dto.EuroUserTrainingRequestUpdateDto;
import br.com.fiap.dto.EuroUserTrainingResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-trainings")
public class EuroUserTrainingController {

    @Autowired
    private EuroUserTrainingRepository euroUserTrainingRepository;

    @Autowired
    private EuroUserRepository euroUserRepository;

    @Autowired
    private EuroTrainingRepository euroTrainingRepository;

    @GetMapping
    public ResponseEntity<List<EuroUserTrainingResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroUserTrainingRepository.findAll().stream()
                        .map(training -> new EuroUserTrainingResponseDto().toDto(training))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroUserTrainingResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroUserTrainingRepository.findById(id)
                        .map(training -> new EuroUserTrainingResponseDto().toDto(training))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroUserTrainingRequestCreateDto dto) {
        Optional<EuroUser> user = euroUserRepository.findById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        Optional<EuroTraining> training = euroTrainingRepository.findById(dto.getTrainingId());
        if (training.isEmpty()) {
            return ResponseEntity.badRequest().body("Treinamento não encontrado.");
        }

        EuroUserTraining savedUserTraining = euroUserTrainingRepository.save(dto.toModel(user.get(), training.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new EuroUserTrainingResponseDto().toDto(savedUserTraining));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroUserTrainingRequestUpdateDto dto) {
        Optional<EuroUserTraining> euroUserTraining = euroUserTrainingRepository.findById(id);

        if (euroUserTraining.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        return ResponseEntity.ok().body(
                new EuroUserTrainingResponseDto().toDto(
                        euroUserTrainingRepository.save(dto.toModel(euroUserTraining.get()))
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroUserTrainingRepository.existsById(id)) {
            euroUserTrainingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
