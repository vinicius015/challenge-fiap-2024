package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroDepartament;
import br.com.fiap.dao.been.EuroSignature;
import br.com.fiap.dao.been.EuroUserTraining;
import br.com.fiap.dao.repository.EuroSignatureRepository;
import br.com.fiap.dao.repository.EuroUserTrainingRepository;
import br.com.fiap.dto.EuroSignatureRequestCreateDto;
import br.com.fiap.dto.EuroSignatureRequestUpdateDto;
import br.com.fiap.dto.EuroSignatureResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/signatures")
public class EuroSignatureController {

    @Autowired
    private EuroSignatureRepository euroSignatureRepository;

    @Autowired
    private EuroUserTrainingRepository euroUserTrainingRepository;

    @GetMapping
    public ResponseEntity<List<EuroSignatureResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroSignatureRepository.findAll().stream()
                        .map(signature -> new EuroSignatureResponseDto().toDto(signature))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroSignatureResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroSignatureRepository.findById(id)
                        .map(signature -> new EuroSignatureResponseDto().toDto(signature))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroSignatureRequestCreateDto dto) {
        Optional<EuroUserTraining> userTraining = euroUserTrainingRepository.findById(dto.getUserTrainingId());
        if (userTraining.isEmpty()) {
            return ResponseEntity.badRequest().body("Treinamento do usuário não encontrado.");
        }

        EuroSignature savedSignature = euroSignatureRepository.save(dto.toModel(userTraining.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new EuroSignatureResponseDto().toDto(savedSignature));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroSignatureRequestUpdateDto dto) {
        if (!euroSignatureRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroUserTraining> existingUserTraining = euroUserTrainingRepository.findById(id);
        if (existingUserTraining.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }
        EuroSignature updatedSignature = dto.toModel(existingUserTraining.get());
        updatedSignature.setId(id);

        return ResponseEntity.ok().body(
                new EuroSignatureResponseDto().toDto(
                        euroSignatureRepository.save(updatedSignature)
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroSignatureRepository.existsById(id)) {
            euroSignatureRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
