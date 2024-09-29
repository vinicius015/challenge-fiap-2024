package br.com.fiap.dao.repository;

import br.com.fiap.dao.been.EuroDepartament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroDepartamentRepository extends JpaRepository<EuroDepartament, Long> {
}