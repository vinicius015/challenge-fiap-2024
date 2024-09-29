package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroAnswer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroAnswerResponseDto {
    private Long id;
    private String answer;
    private boolean correct;
    private EuroQuestionResponseDto question;

    public EuroAnswerResponseDto toDto(EuroAnswer euroAnswer) {
        return new ModelMapper().map(euroAnswer, EuroAnswerResponseDto.class);
    }
}
