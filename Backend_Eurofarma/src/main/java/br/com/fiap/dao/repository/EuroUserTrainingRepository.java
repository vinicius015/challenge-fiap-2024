package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroUserTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroUserTrainingRepository extends JpaRepository<EuroUserTraining, Long> {
}