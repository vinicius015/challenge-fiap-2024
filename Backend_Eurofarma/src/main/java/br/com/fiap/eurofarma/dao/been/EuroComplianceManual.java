package br.com.fiap.eurofarma.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EURO_COMPLIANCE_MANUAL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"EURO_USER_ID", "EURO_COMPLIANCE_TITLE_ID"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroComplianceManual {

    @EmbeddedId
    private EuroComplianceManualKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "EURO_USER_ID", nullable = false)
    private EuroUser user;

    @ManyToOne
    @MapsId("complianceTitleId")
    @JoinColumn(name = "EURO_COMPLIANCE_TITLE_ID", nullable = false)
    private EuroComplianceTitle complianceTitle;
}
