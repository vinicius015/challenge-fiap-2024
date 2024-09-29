package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroTrainingResponseDto {

    private Long id;

    private String name;

    public EuroTrainingResponseDto toDto(EuroTraining euroTraining) {
        return new ModelMapper().map(euroTraining, EuroTrainingResponseDto.class);
    }
}
