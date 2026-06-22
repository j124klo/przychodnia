package pl.polsl.przychodnia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.polsl.przychodnia.entities.Wizyta;

import java.time.LocalDate;

@Getter
@Setter
public class WizytaDTO extends RepresentationModel<WizytaDTO> {
    private Integer id;
    private String adresPrzychodni;
    private LocalDate data;
    private String diagnoza;

    public WizytaDTO(Wizyta wizyta) {
        this.id = wizyta.getId();
        this.adresPrzychodni = wizyta.getAdresPrzychodni();
        this.data = wizyta.getData();
        this.diagnoza = wizyta.getDiagnoza();
    }
}