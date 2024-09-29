package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroAuth;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroAuthResponseDto {
    private Long id;
    private String pass;
    private Boolean activated;
    private String lastAuth;
    private String generated;
    private EuroUserResponseDto euroUser;
    public EuroAuthResponseDto toDto(EuroAuth euroAuth) {
        EuroAuthResponseDto responseDto = new ModelMapper().map(euroAuth, EuroAuthResponseDto.class);
        responseDto.setEuroUser(new EuroUserResponseDto().toDto(euroAuth.getEuroUser()));
        return responseDto;
    }
}