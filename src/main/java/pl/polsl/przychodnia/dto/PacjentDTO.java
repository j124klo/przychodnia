package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.polsl.przychodnia.entities.Pacjent;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"pesel", "imie", "nazwisko", "plec", "dataUr", "wzrost", "masa", "grupaKrwi"})
public class PacjentDTO extends RepresentationModel<PacjentDTO> {
    private String pesel;
    private String imie;
    private String nazwisko;
    private String plec;
    private LocalDate dataUr;
    private Integer wzrost;
    private Double masa;
    private String grupaKrwi;

    public PacjentDTO(Pacjent pacjent) {
        this.pesel = pacjent.getPesel();
        this.imie = pacjent.getImie();
        this.nazwisko = pacjent.getNazwisko();
        this.plec = pacjent.getPlec();
        this.dataUr = pacjent.getDataUr();
        this.wzrost = pacjent.getWzrost();
        this.masa = pacjent.getMasa();
        this.grupaKrwi = pacjent.getGrupaKrwi();
    }
}