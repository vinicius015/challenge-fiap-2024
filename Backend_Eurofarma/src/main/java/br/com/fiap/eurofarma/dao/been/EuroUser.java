package br.com.fiap.eurofarma.dao.been;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "EURO_USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = "T_USERNAME")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "T_NAME", length = 256, nullable = false)
    private String name;

    @Column(name = "T_EMAIL", length = 256)
    private String email;

    @Column(name = "T_CPF", length = 14)
    private String cpf;

    @Column(name = "T_NUMBER", precision = 10, scale = 0)
    private BigDecimal number;

    @ManyToOne
    @JoinColumn(name = "EURO_POSITION_ID", nullable = false)
    private EuroPosition position;

    @OneToOne(mappedBy = "euroUser")
    @JsonIgnore
    private EuroAuth euroAuth;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<EuroComplianceManual> complianceManuals;
}