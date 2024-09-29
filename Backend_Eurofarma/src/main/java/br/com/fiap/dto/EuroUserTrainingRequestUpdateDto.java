package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroUserTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EuroUserTrainingRequestUpdateDto {
    private LocalDateTime startTraining;
    private LocalDateTime finishTraining;

    public EuroUserTraining toModel(EuroUserTraining euroUserTraining) {
        
        euroUserTraining.setStartTraining(this.startTraining);
        euroUserTraining.setFinishTraining(this.finishTraining);

        return euroUserTraining;
    }
}
