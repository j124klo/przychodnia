package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Badanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nazwaBadania;
    private String placeholder;

    @ManyToMany(mappedBy = "badania")
    @JsonIgnore
    private Set<Wizyta> wizyty;
}