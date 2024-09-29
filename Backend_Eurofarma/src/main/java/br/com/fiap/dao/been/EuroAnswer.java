package br.com.fiap.dao.been;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EURO_ANSWER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EuroAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "T_ANSWER",nullable = false)
    private String answer;

    @Column(name = "B_CORRECT",nullable = false)
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "EURO_QUESTION_ID")
    private EuroQuestion euroQuestion;
}
