package br.com.fiap.service;


import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.repository.EuroUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EuroUserRepository euroUserRepository;

    public boolean authenticate(EuroUser euroUser) {
        Optional<EuroUser> userOptional = findUserByCriteria(euroUser);

        if (userOptional.isPresent()) {
            EuroUser user = userOptional.get();

            if (euroUser.getEuroAuth().getPass().equals(user.getEuroAuth().getPass()) && user.getEuroAuth().getActivated()) {
                user.getEuroAuth().setLastAuth(LocalDateTime.now());
                euroUserRepository.save(user);
                return true;
            }
        }

        return false;
    }

    private Optional<EuroUser> findUserByCriteria(EuroUser euroUser) {
        if (euroUser.getUsername() != null) {
            return euroUserRepository.findByUsername(euroUser.getUsername());
        } else if (euroUser.getEmail() != null) {
            return euroUserRepository.findByEmail(euroUser.getEmail());
        } else if (euroUser.getCpf() != null) {
            return euroUserRepository.findByCpf(euroUser.getCpf());
        } else if (euroUser.getNumber() != null) {
            return euroUserRepository.findByNumber(euroUser.getNumber());
        }
        return Optional.empty();
    }
}