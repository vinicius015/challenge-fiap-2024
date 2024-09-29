package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class EuroUserResponseDto {
    private Long id;
    private String re;
    private String username;
    private String name;
    private String email;
    private String cpf;
    private BigDecimal number;
    private EuroPositionResponseDto position;

    public EuroUserResponseDto toDto(EuroUser euroUser) {
        EuroUserResponseDto responseDto = new ModelMapper().map(euroUser, EuroUserResponseDto.class);
        responseDto.setPosition(new EuroPositionResponseDto().toDto(euroUser.getPosition()));
        return responseDto;
    }
}
