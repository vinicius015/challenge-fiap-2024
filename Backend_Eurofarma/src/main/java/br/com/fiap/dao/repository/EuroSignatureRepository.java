package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroSignatureRepository extends JpaRepository<EuroSignature, Long> {
}