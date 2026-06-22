package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Pacjent;

import java.time.LocalDate;

@Getter
@Setter
public class PacjentDTO extends RepresentationModel<PacjentDTO> {
    private String pesel;
    private String imie;
    private String nazwisko;
    private LocalDate dataUr;

    // Konstruktor mapujący Encję na DTO
    public PacjentDTO(Pacjent pacjent) {
        this.pesel = pacjent.getPesel();
        this.imie = pacjent.getImie();
        this.nazwisko = pacjent.getNazwisko();
        this.dataUr = pacjent.getDataUr();
    }
}