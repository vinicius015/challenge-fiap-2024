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
public class AuthenticationRequestDTO {
    private String username;
    private String email;
    private String cpf;
    private Integer number;
    private String password;

    public EuroUser toUserModel() {
        EuroUser euroUser = new ModelMapper().map(this, EuroUser.class);
        if (euroUser.getEuroAuth() == null) {
            euroUser.setEuroAuth(new EuroAuth());
        }
        euroUser.getEuroAuth().setPass(this.password);
        return euroUser;
    }
}
