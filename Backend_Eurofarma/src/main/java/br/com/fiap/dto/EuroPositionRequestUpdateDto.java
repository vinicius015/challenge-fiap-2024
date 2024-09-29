package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroDepartament;
import br.com.fiap.dao.been.EuroPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroPositionRequestUpdateDto {
    private String position;
    private Long departamentId;
    public EuroPosition toModel(EuroDepartament departament) {
        EuroPosition euroPosition = new ModelMapper().map(this, EuroPosition.class);
        euroPosition.setDepartament(departament);
        return euroPosition;
    }
}