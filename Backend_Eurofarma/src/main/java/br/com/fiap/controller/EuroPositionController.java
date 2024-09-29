package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroDepartament;
import br.com.fiap.dao.been.EuroPosition;
import br.com.fiap.dao.repository.EuroDepartamentRepository;
import br.com.fiap.dao.repository.EuroPositionRepository;
import br.com.fiap.dto.EuroPositionRequestCreateDto;
import br.com.fiap.dto.EuroPositionRequestUpdateDto;
import br.com.fiap.dto.EuroPositionResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<EuroPositionResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroPositionRepository.findAll().stream()
                        .map(position -> new EuroPositionResponseDto().toDto(position))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroPositionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroPositionRepository.findById(id)
                        .map(position -> new EuroPositionResponseDto().toDto(position))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroPositionRequestCreateDto dto) {
        if (dto.getDepartamentId() == null) {
            return ResponseEntity.badRequest().body("Departamento é obrigatório.");
        }

        Optional<EuroDepartament> departament = euroDepartamentRepository.findById(dto.getDepartamentId());
        if (departament.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new EuroPositionResponseDto().toDto(
                        euroPositionRepository.save(
                                dto.toModel(departament.get())
                        )
                )
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroPositionRequestUpdateDto dto) {
        if (!euroPositionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroDepartament> existingDepartament = euroDepartamentRepository.findById(dto.getDepartamentId());
        if (existingDepartament.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }

        EuroPosition updatedPosition = dto.toModel(existingDepartament.get());
        updatedPosition.setId(id);

        return ResponseEntity.ok().body(
                new EuroPositionResponseDto().toDto(
                        euroPositionRepository.save(updatedPosition)
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroPositionRepository.existsById(id)) {
            euroPositionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
