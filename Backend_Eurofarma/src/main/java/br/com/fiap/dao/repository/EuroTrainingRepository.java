package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroTrainingRepository extends JpaRepository<EuroTraining, Long> {
}