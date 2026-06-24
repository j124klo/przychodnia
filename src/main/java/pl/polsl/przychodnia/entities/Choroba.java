package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Choroba {

    @Id // Kod ICD-10 wpisujemy ręcznie (np. J00)
    private String icd10;

    private String nazwaChoroby;

    @ManyToMany(mappedBy = "choroby")
    @JsonIgnore
    private Set<Wizyta> wizyty;
}