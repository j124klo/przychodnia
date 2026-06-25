package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.polsl.przychodnia.entities.Wizyta;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "adresPrzychodni", "data", "diagnoza", "recepta", "skierowanie", "pacjentPesel", "personelPwzIds", "chorobyIcd10", "badaniaIds", "lekiIds"})
public class WizytaDTO extends org.springframework.hateoas.RepresentationModel<WizytaDTO> {
    private Integer id;
    private String adresPrzychodni;
    private LocalDate data;
    private String diagnoza;
    private String recepta;
    private String skierowanie;

    // Pola przydatne przy odbieraniu danych (POST/PUT) z żądania
    private String pacjentPesel;
    private List<String> personelPwzIds;
    private List<String> chorobyIcd10;
    private List<Integer> badaniaIds;
    private List<Integer> lekiIds;

    // Konstruktor mapujący z Encji na DTO (używany przy GET)
    public WizytaDTO(Wizyta wizyta) {
        this.id = wizyta.getId();
        this.adresPrzychodni = wizyta.getAdresPrzychodni();
        this.data = wizyta.getData();
        this.diagnoza = wizyta.getDiagnoza();
        this.recepta = wizyta.getRecepta();
        this.skierowanie = wizyta.getSkierowanie();
        
        if (wizyta.getPacjent() != null) {
            this.pacjentPesel = wizyta.getPacjent().getPesel();
        }
        if (wizyta.getPersonel() != null) {
            this.personelPwzIds = wizyta.getPersonel().stream().map(p -> p.getPwzId()).collect(Collectors.toList());
        }
        if (wizyta.getChoroby() != null) {
            this.chorobyIcd10 = wizyta.getChoroby().stream().map(c -> c.getIcd10()).collect(Collectors.toList());
        }
        if (wizyta.getBadania() != null) {
            this.badaniaIds = wizyta.getBadania().stream().map(b -> b.getId()).collect(Collectors.toList());
        }
        if (wizyta.getLeki() != null) {
            this.lekiIds = wizyta.getLeki().stream().map(l -> l.getId()).collect(Collectors.toList());
        }
    }
}