package br.com.fiap.eurofarma.dao.repository;

import br.com.fiap.eurofarma.dao.been.EuroComplianceTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroComplianceTitleRepository extends JpaRepository<EuroComplianceTitle, Long> {
}