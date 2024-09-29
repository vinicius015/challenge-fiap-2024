package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EuroAuthRepository extends JpaRepository<EuroAuth, Long> {
    Optional<EuroAuth> findByEuroUserId(Long id);

    @Transactional
    void deleteByEuroUserId(Long id);

    boolean existsByEuroUserId(Long id);
}