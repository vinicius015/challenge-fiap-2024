package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroUserTraining;
import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.been.EuroTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EuroUserTrainingRequestCreateDto {
    private Long userId;
    private Long trainingId;
    private LocalDateTime finishTraining;

    public EuroUserTraining toModel(EuroUser euroUser, EuroTraining euroTraining) {
        return EuroUserTraining.builder()
                .euroUser(euroUser)
                .euroTraining(euroTraining)
                .finishTraining(this.finishTraining)
                .id(null)
                .build();
    }

}
