package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroQuestionRepository extends JpaRepository<EuroQuestion, Long> {
}