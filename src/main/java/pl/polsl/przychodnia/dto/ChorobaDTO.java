package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Choroba;

@Getter @Setter
public class ChorobaDTO extends RepresentationModel<ChorobaDTO> {
    private String kod; // Założenie: Klucz główny to String
    private String nazwa;

    public ChorobaDTO(Choroba choroba) {
        this.kod = choroba.getIcd10(); // Dostosuj gettery do swojej encji
        this.nazwa = choroba.getNazwaChoroby();
    }
}