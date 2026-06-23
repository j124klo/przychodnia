package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Lek;

@Getter @Setter
public class LekDTO extends RepresentationModel<LekDTO> {
    private Integer id;
    private String nazwa;

    public LekDTO(Lek lek) {
        this.id = lek.getId();
        this.nazwa = lek.getNazwaLeku();
    }
}