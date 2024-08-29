package br.com.fiap.eurofarma.service;

import br.com.fiap.eurofarma.dao.been.EuroUser;
import br.com.fiap.eurofarma.dao.repository.EuroUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EuroUserRepository euroUserRepository;

    public boolean authenticate(String username, String email, String cpf, Integer number, String password) {
        Optional<EuroUser> userOptional = findUserByCriteria(username, email, cpf, number);

        if (userOptional.isPresent()) {
            EuroUser user = userOptional.get();

            if (password.equals(user.getEuroAuth().getPass()) && user.getEuroAuth().getActivated()) {
                user.getEuroAuth().setLastAuth(LocalDateTime.now());
                euroUserRepository.save(user);
                return true;
            }
        }

        return false;
    }

    private Optional<EuroUser> findUserByCriteria(String username, String email, String cpf, Integer number) {
        if (username != null) {
            return euroUserRepository.findByUsername(username);
        } else if (email != null) {
            return euroUserRepository.findByEmail(email);
        } else if (cpf != null) {
            return euroUserRepository.findByCpf(cpf);
        } else if (number != null) {
            return euroUserRepository.findByNumber(number);
        }
        return Optional.empty();
    }
}