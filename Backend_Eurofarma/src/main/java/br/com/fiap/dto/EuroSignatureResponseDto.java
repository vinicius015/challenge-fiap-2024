package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroSignature;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class EuroSignatureResponseDto {
    private Long id;
    private boolean signed;
    private String link;
    private LocalDateTime dtSigned;
    private EuroUserTrainingResponseDto userTraining;

    public EuroSignatureResponseDto toDto(EuroSignature euroSignature) {
        return new ModelMapper().map(euroSignature, EuroSignatureResponseDto.class);
    }
}
