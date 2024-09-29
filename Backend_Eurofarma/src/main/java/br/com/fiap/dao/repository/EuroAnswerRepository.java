package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroAnswerRepository extends JpaRepository<EuroAnswer, Long> {
}