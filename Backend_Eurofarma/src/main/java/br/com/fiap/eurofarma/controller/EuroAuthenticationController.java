package br.com.fiap.eurofarma.controller;

import br.com.fiap.eurofarma.controller.dto.AuthenticationRequestDTO;
import br.com.fiap.eurofarma.controller.dto.AuthenticationResponseDTO;
import br.com.fiap.eurofarma.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class EuroAuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO dto) {
        boolean isAuthenticated = authenticationService.authenticate(
                dto.getUsername(),
                dto.getEmail(),
                dto.getCpf(),
                dto.getNumber(),
                dto.getPassword()
        );

        if (isAuthenticated) {
            AuthenticationResponseDTO response = new AuthenticationResponseDTO("Authenticated", "Authentication successful");
            return ResponseEntity.ok(response);
        } else {
            AuthenticationResponseDTO response = new AuthenticationResponseDTO("Failed", "Invalid credentials or inactive account");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
