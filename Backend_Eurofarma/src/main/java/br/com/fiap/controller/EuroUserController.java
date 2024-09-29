package br.com.fiap.controller;

import br.com.fiap.dao.been.EuroPosition;
import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.repository.EuroPositionRepository;
import br.com.fiap.dao.repository.EuroUserRepository;
import br.com.fiap.dto.EuroUserRequestCreateDto;
import br.com.fiap.dto.EuroUserRequestUpdateDto;
import br.com.fiap.dto.EuroUserResponseDto;
import br.com.fiap.service.FileReadSave;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class EuroUserController {

    @Autowired
    private EuroUserRepository euroUserRepository;

    @Autowired
    private EuroPositionRepository euroPositionRepository;

    @Autowired
    private FileReadSave fileReadSave;

    @GetMapping
    public ResponseEntity<List<EuroUserResponseDto>> listAll() {
        return ResponseEntity.ok().body(
                euroUserRepository.findAll().stream()
                        .map(user -> new EuroUserResponseDto().toDto(user))
                        .toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<EuroUserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                euroUserRepository.findById(id)
                        .map(user -> new EuroUserResponseDto().toDto(user))
                        .orElseThrow(() -> new RuntimeException("Id inexistente"))
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EuroUserRequestCreateDto dto) {
        if (dto.getPositionId() == null) {
            return ResponseEntity.badRequest().body("Posição é obrigatória.");
        }

        Optional<EuroPosition> existingPosition = euroPositionRepository.findById(dto.getPositionId());
        if (existingPosition.isEmpty()) {
            return ResponseEntity.badRequest().body("Posição não encontrada.");
        }

        EuroUser euroUser = dto.toModel(existingPosition.get());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new EuroUserResponseDto().toDto(
                            euroUserRepository.save(euroUser)
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EuroUserRequestUpdateDto dto) {
        if (!euroUserRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
        }

        Optional<EuroPosition> existingPosition = euroPositionRepository.findById(dto.getPositionId());
        if (existingPosition.isEmpty()) {
            return ResponseEntity.badRequest().body("Posição não encontrada.");
        }

        EuroUser updatedUser = dto.toModel(existingPosition.get());
        updatedUser.setId(id);

        try {
            return ResponseEntity.ok().body(
                    new EuroUserResponseDto().toDto(
                            euroUserRepository.save(updatedUser)
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroUserRepository.existsById(id)) {
            try {
                euroUserRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio.");
        }

        try {
            if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
                return fileReadSave.processCSV(file);
            } else if (file.getOriginalFilename().endsWith(".txt")) {
                return fileReadSave.processTxt(file);
            } else {
                return ResponseEntity.badRequest().body("Formato de arquivo não suportado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
