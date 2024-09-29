package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroUserTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EuroUserTrainingResponseDto {
    private Long id;
    private LocalDateTime startTraining;
    private LocalDateTime finishTraining;
    private EuroUserResponseDto user;
    private EuroTrainingResponseDto training;

    public EuroUserTrainingResponseDto toDto(EuroUserTraining euroUserTraining) {
        return new ModelMapper().map(euroUserTraining, EuroUserTrainingResponseDto.class);
    }
}
