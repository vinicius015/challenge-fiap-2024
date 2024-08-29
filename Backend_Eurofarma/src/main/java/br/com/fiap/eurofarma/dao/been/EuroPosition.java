package br.com.fiap.eurofarma.dao.been;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "EURO_POSITION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuroPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "T_POSITION", length = 256, nullable = false)
    private String position;

    @ManyToOne
    @JoinColumn(name = "EURO_DEPARTAMENT_ID", nullable = false)
    private EuroDepartament departament;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private List<EuroUser> users;
}
