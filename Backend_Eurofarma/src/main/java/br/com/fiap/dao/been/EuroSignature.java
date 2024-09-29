package br.com.fiap.dao.been;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EURO_SIGNATURE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EuroSignature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "B_SIGNED",nullable = false)
    private boolean signed;

    @Column(name = "T_LINK")
    private String link;

    @Column(name = "DT_SIGNED")
    private LocalDateTime dtSigned;

    @OneToOne
    @JoinColumn(name = "EURO_SIGNATURE_ID", nullable = false)
    private EuroUserTraining euroUserTraining;
}
