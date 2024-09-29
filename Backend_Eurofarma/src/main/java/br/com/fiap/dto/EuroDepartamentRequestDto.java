package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroDepartament;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroDepartamentRequestDto {
    private String department;
    private String description;

    public EuroDepartament toModel() {
        return new ModelMapper().map(this, EuroDepartament.class);
    }
}