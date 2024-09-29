package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroSignature;
import br.com.fiap.dao.been.EuroUserTraining;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EuroSignatureRequestUpdateDto {
    private boolean signed;
    private String link;
    private LocalDateTime dtSigned;
    private Long userTrainingId;

    public EuroSignature toModel(EuroUserTraining euroUserTraining) {
        EuroSignature euroSignature = new ModelMapper().map(this,EuroSignature.class);
        euroSignature.setEuroUserTraining(euroUserTraining);
        return euroSignature;
    }
}
