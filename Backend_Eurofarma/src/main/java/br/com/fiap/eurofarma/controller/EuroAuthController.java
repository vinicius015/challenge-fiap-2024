package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.dao.been.EuroAuth;
import br.com.fiap.eurofarma.dao.been.EuroUser;
import br.com.fiap.eurofarma.dao.repository.EuroAuthRepository;
import br.com.fiap.eurofarma.dao.repository.EuroUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auths")
public class EuroAuthController {

    @Autowired
    private EuroAuthRepository euroAuthRepository;

    @Autowired
    private EuroUserRepository euroUserRepository;

    @GetMapping("/listAll")
    public List<EuroAuth> listAll() {
        return euroAuthRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EuroAuth> getById(@PathVariable Long id) {
        Optional<EuroAuth> euroAuth = euroAuthRepository.findById(id);
        return euroAuth.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EuroAuth euroAuth) {
        // Verifique se o usuário está presente
        if (euroAuth.getEuroUser() == null || euroAuth.getEuroUser().getId() == null) {
            return ResponseEntity.badRequest().body("Usuário é obrigatório.");
        }

        // Verifique se o usuário existe no banco de dados
        Optional<EuroUser> existingUser = euroUserRepository.findById(euroAuth.getEuroUser().getId());
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        } else {
            euroAuth.setEuroUser(existingUser.get());
        }

        // Salve a entidade EuroAuth
        try {
            EuroAuth savedAuth = euroAuthRepository.save(euroAuth);
            return ResponseEntity.ok(savedAuth);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a autenticação: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EuroAuth> update(@PathVariable Long id, @RequestBody EuroAuth euroAuthDetails) {
        Optional<EuroAuth> euroAuth = euroAuthRepository.findById(id);
        if (euroAuth.isPresent()) {
            EuroAuth existingAuth = euroAuth.get();
            existingAuth.setPass(euroAuthDetails.getPass());
            existingAuth.setActivated(euroAuthDetails.getActivated());
            existingAuth.setLastAuth(euroAuthDetails.getLastAuth());
            existingAuth.setGenerated(euroAuthDetails.getGenerated());
            existingAuth.setEuroUser(euroAuthDetails.getEuroUser());

            EuroAuth updatedAuth = euroAuthRepository.save(existingAuth);
            return ResponseEntity.ok(updatedAuth);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (euroAuthRepository.existsById(id)) {
            euroAuthRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
