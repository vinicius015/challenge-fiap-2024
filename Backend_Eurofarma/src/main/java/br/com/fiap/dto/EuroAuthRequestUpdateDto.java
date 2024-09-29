package br.com.fiap.dto;

import br.com.fiap.dao.been.EuroAuth;
import br.com.fiap.dao.been.EuroUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
public class EuroAuthRequestUpdateDto {
    private String pass;
    private Boolean activated;
    public EuroAuth toModel(EuroUser euroUser) {
        EuroAuth euroAuth = new ModelMapper().map(this, EuroAuth.class);
        euroAuth.setEuroUser(euroUser);
        return euroAuth;
    }
}
