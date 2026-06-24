package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Choroba;

@Getter @Setter
public class ChorobaDTO extends RepresentationModel<ChorobaDTO> {
    private String icd10;
    private String nazwaChoroby;

    public ChorobaDTO(Choroba choroba) {
        this.icd10 = choroba.getIcd10();
        this.nazwaChoroby = choroba.getNazwaChoroby();
    }
}