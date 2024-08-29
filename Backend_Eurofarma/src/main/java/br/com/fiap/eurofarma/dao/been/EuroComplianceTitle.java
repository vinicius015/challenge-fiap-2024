package br.com.fiap.eurofarma.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "EURO_COMPLIANCE_TITLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroComplianceTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_TITLE", length = 256, nullable = false)
    private String title;

    @Lob
    @Column(name = "T_CONTENT")
    private String content;

    @Column(name = "N_VERSION", precision = 10, scale = 0, nullable = false)
    private BigDecimal version;

    @OneToMany(mappedBy = "complianceTitle")
    @JsonIgnore
    private List<EuroComplianceSubtitle> subtitles;

    @OneToMany(mappedBy = "complianceTitle")
    @JsonIgnore
    private List<EuroComplianceManual> complianceManuals;
}
