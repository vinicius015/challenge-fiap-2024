package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroDepartament;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroDepartamentResponseDto {
    private Long id;
    private String department;
    private String description;

    public EuroDepartamentResponseDto toDto(EuroDepartament euroDepartament) {
        return new ModelMapper().map(euroDepartament, EuroDepartamentResponseDto.class);
    }
}