package br.com.fiap.eurofarma.dao.been;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroComplianceManualKey implements Serializable {

    @Column(name = "EURO_USER_ID")
    private Long userId;

    @Column(name = "EURO_COMPLIANCE_TITLE_ID")
    private Long complianceTitleId;
}
