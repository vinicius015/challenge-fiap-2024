package br.com.fiap.eurofarma.dao.repository;

import br.com.fiap.eurofarma.dao.been.EuroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EuroUserRepository extends JpaRepository<EuroUser, Long> {
    Optional<EuroUser> findByNumber(Integer number);

    Optional<EuroUser> findByCpf(String cpf);

    Optional<EuroUser> findByEmail(String email);

    Optional<EuroUser> findByUsername(String username);
}
