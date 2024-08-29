package br.com.fiap.eurofarma.dao.been;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EURO_AUTH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "T_PASS", nullable = false)
    private String pass;

    @Column(name = "B_ACTIVED", nullable = false)
    private Boolean activated;

    @Column(name = "DT_LAST_AUTH")
    private LocalDateTime lastAuth;

    @Column(name = "DT_GENERATED")
    private LocalDateTime generated;

    @OneToOne
    @JoinColumn(name = "EURO_USER_ID", nullable = false)
    private EuroUser euroUser;


    @PrePersist
    protected void onCreate() {
        if (generated == null) {
            generated = LocalDateTime.now();
        }
    }
}
