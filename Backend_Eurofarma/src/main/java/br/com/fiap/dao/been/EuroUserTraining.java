package br.com.fiap.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EURO_USER_TRAINING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EuroUserTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DT_START_TRAINING", nullable = false)
    private LocalDateTime startTraining = LocalDateTime.now();

    @Column(name = "DT_FINISH_TRAINING")
    private LocalDateTime finishTraining;

    @ManyToOne
    @JoinColumn(name = "EURO_USER_ID", nullable = false)
    private EuroUser euroUser;

    @ManyToOne
    @JoinColumn(name = "EURO_TRAINING_ID", nullable = false)
    private EuroTraining euroTraining;

    @OneToOne(mappedBy = "euroUserTraining")
    @JsonIgnore
    private EuroSignature euroSignature;
    @PrePersist
    public void prePersist() {
        if (this.startTraining == null) {
            this.startTraining = LocalDateTime.now();
        }
    }

}
