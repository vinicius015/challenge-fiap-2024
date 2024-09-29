package br.com.fiap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@ToString
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String status;
    private String message;

}
