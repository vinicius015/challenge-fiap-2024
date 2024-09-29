package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroQuestionResponseDto {
    private Long id;
    private String question;
    private EuroTrainingResponseDto training;

    public EuroQuestionResponseDto toDto(EuroQuestion euroQuestion) {
        return new ModelMapper().map(euroQuestion, EuroQuestionResponseDto.class);
    }
}
