package br.com.fiap.eurofarma.dao.repository;

import br.com.fiap.eurofarma.dao.been.EuroPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroPositionRepository extends JpaRepository<EuroPosition, Long> {
}
