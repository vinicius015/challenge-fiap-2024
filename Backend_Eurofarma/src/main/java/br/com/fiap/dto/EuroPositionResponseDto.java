package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroPositionResponseDto {
    private Long id;
    private String position;
    private EuroDepartamentResponseDto departament;

    public EuroPositionResponseDto toDto(EuroPosition euroPosition) {
        return new ModelMapper().map(euroPosition, EuroPositionResponseDto.class);
    }
}