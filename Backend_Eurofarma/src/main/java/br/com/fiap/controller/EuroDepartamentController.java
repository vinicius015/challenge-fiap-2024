package br.com.fiap.controller;

import br.com.fiap.dao.repository.EuroDepartamentRepository;
import br.com.fiap.dto.EuroDepartamentRequestDto;
import br.com.fiap.dto.EuroDepartamentRequestUpdateDto;
import br.com.fiap.dto.EuroDepartamentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departaments")
public class EuroDepartamentController {

    @Autowired
    private EuroDepartamentRepository euroDepartamentRepository;

    @GetMapping
    public ResponseEntity<List<EuroDepartamentResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroDepartamentRepository.findAll().stream()
                .map(euroDepartament -> new EuroDepartamentResponseDto().toDto(euroDepartament))
                .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroDepartamentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroDepartamentRepository.findById(id)
                        .map(euroDepartament -> new EuroDepartamentResponseDto().toDto(euroDepartament))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<EuroDepartamentResponseDto> save(@Valid @RequestBody EuroDepartamentRequestDto dto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new EuroDepartamentResponseDto().toDto(
                            euroDepartamentRepository.save(
                                    dto.toModel()
                            )
                    )
            );
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroDepartamentRequestUpdateDto dto) {
        if (!euroDepartamentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado"); // Resposta 404 com mensagem
        }
        return ResponseEntity.ok().body(
                new EuroDepartamentResponseDto().toDto(
                        euroDepartamentRepository.save(
                                dto.toModel(id)
                        )
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            if (euroDepartamentRepository.existsById(id)) {
                euroDepartamentRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
