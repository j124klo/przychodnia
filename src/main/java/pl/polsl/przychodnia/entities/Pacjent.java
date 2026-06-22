package pl.polsl.przychodnia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pacjent {

    @Id // Brak GeneratedValue, bo PESEL wpisujemy ręcznie
    private String pesel;

    private String imie;
    private String nazwisko;
    private String plec;
    private LocalDate dataUr;
    private Integer wzrost;
    private Double masa;
    private String grupaKrwi;

    // Kaskadowość do scenariusza z umawianiem pierwszej wizyty
    @OneToMany(mappedBy = "pacjent", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Wizyta> wizyty;
}