package br.com.fiap.eurofarma.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "EURO_DEPARTAMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroDepartament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_DEPARTAMENT", length = 256, nullable = false)
    private String department;

    @Lob
    @Column(name = "T_DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "departament")
    @JsonIgnore
    private List<EuroPosition> positions;

}
