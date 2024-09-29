package br.com.fiap.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EURO_QUESTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EuroQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "T_QUESTION",nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "EURO_TRAINING_ID", nullable = false)
    private EuroTraining euroTraining;

    @OneToMany(mappedBy = "euroQuestion")
    @JsonIgnore
    private List<EuroAnswer> euroAnswer = new ArrayList<>();
}
