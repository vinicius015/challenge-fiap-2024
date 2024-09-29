package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroTrainingRequestCreateDto {

    private String name;

    public EuroTraining toModel() {
        EuroTraining euroTraining = new ModelMapper().map(this,EuroTraining.class);
        euroTraining.setId(null);
        return euroTraining;
    }
}
