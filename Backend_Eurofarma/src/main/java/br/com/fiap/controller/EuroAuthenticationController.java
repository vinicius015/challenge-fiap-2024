package br.com.fiap.controller;

import br.com.fiap.dto.AuthenticationRequestDTO;
import br.com.fiap.dto.AuthenticationResponseDTO;
import br.com.fiap.service.AuthenticationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class EuroAuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO dto) {
        boolean isAuthenticated = authenticationService.authenticate(dto.toUserModel());

        if (isAuthenticated) {
            AuthenticationResponseDTO response = new AuthenticationResponseDTO("Authenticated", "Authentication successful");
            return ResponseEntity.ok(response);
        } else {
            AuthenticationResponseDTO response = new AuthenticationResponseDTO("Failed", "Invalid credentials or inactive account");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
