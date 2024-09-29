package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroQuestion;
import br.com.fiap.dao.been.EuroTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroQuestionRequestCreateDto {
    private String question;
    private Long trainingId;

    public EuroQuestion toModel(EuroTraining euroTraining) {
        EuroQuestion euroQuestion = new ModelMapper().map(this, EuroQuestion.class);
        euroQuestion.setId(null);
        euroQuestion.setEuroTraining(euroTraining);
        return euroQuestion;
    }
}
