package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroPosition;
import br.com.fiap.dao.been.EuroUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class EuroUserRequestCreateDto {
    private String re;
    private String username;
    private String name;
    private String email;
    private String cpf;
    private BigDecimal number;
    private Long positionId;

    public EuroUser toModel(EuroPosition position) {
        EuroUser euroUser = new ModelMapper().map(this, EuroUser.class);
        euroUser.setPosition(position);
        euroUser.setId(null);
        return euroUser;
    }
}

