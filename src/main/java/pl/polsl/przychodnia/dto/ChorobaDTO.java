package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.polsl.przychodnia.entities.Choroba;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"icd10", "nazwaChoroby"})
public class ChorobaDTO extends RepresentationModel<ChorobaDTO> {
    private String icd10;
    private String nazwaChoroby;

    public ChorobaDTO(Choroba choroba) {
        this.icd10 = choroba.getIcd10();
        this.nazwaChoroby = choroba.getNazwaChoroby();
    }
}