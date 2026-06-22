package pl.polsl.przychodnia.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Wizyta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String adresPrzychodni;
    private LocalDate data;
    private String diagnoza;
    private String recepta;
    private String skierowanie;

    // --- RELACJA M:1 (Wiele wizyt, jeden pacjent) ---
    @ManyToOne
    private Pacjent pacjent;

    // --- RELACJE M:N (Wiele do Wielu) obsługiwane automatycznie przez Springa ---
    @ManyToMany
    private Set<Personel> personel;

    @ManyToMany
    private Set<Choroba> choroby;

    @ManyToMany
    private Set<Badanie> badania;

    @ManyToMany
    private Set<Lek> leki;
}