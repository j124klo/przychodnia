package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pacjent {

    @Id
    private String pesel;

    private String imie;
    private String nazwisko;
    private String plec;
    private LocalDate dataUr;
    private Integer wzrost;
    private Double masa;
    private String grupaKrwi;

    @OneToMany(mappedBy = "pacjent", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Wizyta> wizyty;
}