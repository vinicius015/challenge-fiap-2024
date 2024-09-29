package br.com.fiap.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EURO_TRAINING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EuroTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_NAME",nullable = false)
    private String name;

    @OneToMany(mappedBy = "euroTraining")
    @JsonIgnore
    private List<EuroQuestion> euroQuestion = new ArrayList<>();

    @OneToMany(mappedBy = "euroTraining")
    @JsonIgnore
    private List<EuroUserTraining> euroUserTrainings = new ArrayList<>();
}
