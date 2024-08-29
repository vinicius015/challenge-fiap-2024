package br.com.fiap.eurofarma.dao.repository;

import br.com.fiap.eurofarma.dao.been.EuroComplianceManual;
import br.com.fiap.eurofarma.dao.been.EuroComplianceManualKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuroComplianceManualRepository extends JpaRepository<EuroComplianceManual, EuroComplianceManualKey> {
}