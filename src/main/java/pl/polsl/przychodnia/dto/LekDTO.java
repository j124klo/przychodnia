package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Lek;

@Getter @Setter
public class LekDTO extends RepresentationModel<LekDTO> {
    private Integer id;
    private String nazwaLeku;
    private String dawka;
    private String substancjaAktywna;

    public LekDTO(Lek lek) {
        this.id = lek.getId();
        this.nazwaLeku = lek.getNazwaLeku(); 
        this.dawka = lek.getDawka();
        this.substancjaAktywna = lek.getSubstancjaAktywna();
    }
}