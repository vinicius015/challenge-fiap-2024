package br.com.fiap.eurofarma.dao.repository;

import br.com.fiap.eurofarma.dao.been.EuroAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroAuthRepository extends JpaRepository<EuroAuth, Long> {
}