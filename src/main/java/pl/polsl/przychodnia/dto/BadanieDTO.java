package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Badanie;

@Getter @Setter
public class BadanieDTO extends RepresentationModel<BadanieDTO> {
    private Integer id;
    private String nazwa;

    public BadanieDTO(Badanie badanie) {
        this.id = badanie.getId();
        this.nazwa = badanie.getNazwaBadania();
    }
}