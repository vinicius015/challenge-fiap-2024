package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroAnswer;
import br.com.fiap.dao.been.EuroQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroAnswerRequestUpdateDto {
    private String answer;
    private boolean correct;
    private Long questionId;
    public EuroAnswer toModel(EuroQuestion euroQuestion) {
        EuroAnswer euroAnswer = new ModelMapper().map(this,EuroAnswer.class);
        euroAnswer.setEuroQuestion(euroQuestion);
        return euroAnswer;
    }
}
