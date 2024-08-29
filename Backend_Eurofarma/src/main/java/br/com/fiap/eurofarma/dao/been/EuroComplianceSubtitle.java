package br.com.fiap.eurofarma.dao.been;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EURO_COMPLIANCE_SUBTITLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroComplianceSubtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_SUBTITLE", length = 256, nullable = false)
    private String subtitle;

    @Lob
    @Column(name = "T_CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "EURO_COMPLIANCE_TITLE_ID", nullable = false)
    private EuroComplianceTitle complianceTitle;
}
