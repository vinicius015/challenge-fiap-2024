package br.com.fiap.eurofarma.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String email;
    private String cpf;
    private Integer number;
    private String password;
}