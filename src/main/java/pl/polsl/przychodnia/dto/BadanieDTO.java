package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.polsl.przychodnia.entities.Badanie;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "nazwaBadania"})
public class BadanieDTO extends RepresentationModel<BadanieDTO> {
    private Integer id;
    private String nazwaBadania;

    public BadanieDTO(Badanie badanie) {
        this.id = badanie.getId();
        this.nazwaBadania = badanie.getNazwaBadania();
    }
}