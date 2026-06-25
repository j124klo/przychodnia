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

    @ManyToOne
    private Pacjent pacjent;

    @ManyToMany
    private Set<Personel> personel;

    @ManyToMany
    private Set<Choroba> choroby;

    @ManyToMany
    private Set<Badanie> badania;

    @ManyToMany
    private Set<Lek> leki;
}