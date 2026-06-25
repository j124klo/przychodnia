package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Personel {

    @Id
    private String pwzId;

    private String imie;
    private String nazwisko;
    private String zawod;

    @ManyToMany(mappedBy = "personel")
    @JsonIgnore
    private Set<Wizyta> wizyty;
}