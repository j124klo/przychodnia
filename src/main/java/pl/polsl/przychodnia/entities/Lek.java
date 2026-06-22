package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Lek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nazwaLeku;
    private String dawka;
    private String substancjaAktywna;

    @ManyToMany(mappedBy = "leki")
    @JsonIgnore
    private Set<Wizyta> wizyty;
}