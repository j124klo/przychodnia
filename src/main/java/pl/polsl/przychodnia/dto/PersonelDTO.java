package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Personel;

@Getter
@Setter
public class PersonelDTO extends RepresentationModel<PersonelDTO> {
    private String pwzId;
    private String imie;
    private String nazwisko;
    private String zawod;

    public PersonelDTO(Personel personel) {
        this.pwzId = personel.getPwzId();
        this.imie = personel.getImie();
        this.nazwisko = personel.getNazwisko();
        this.zawod = personel.getZawod();
    }
}