package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroDepartament;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroDepartamentRequestUpdateDto {
    private String department;
    private String description;

    public EuroDepartament toModel(Long id) {
        EuroDepartament euroDepartament = new ModelMapper().map(this, EuroDepartament.class);
        euroDepartament.setId(id);
        return euroDepartament;
    }
}