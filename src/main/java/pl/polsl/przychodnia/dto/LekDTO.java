package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.polsl.przychodnia.entities.Lek;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "nazwaLeku", "substancjaAktywna", "dawka"})
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