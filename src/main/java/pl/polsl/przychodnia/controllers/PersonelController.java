package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.PersonelDTO;
import pl.polsl.przychodnia.entities.Personel;
import pl.polsl.przychodnia.services.PersonelService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/personel")
@RequiredArgsConstructor
public class PersonelController {

    private final PersonelService personelService;

    @GetMapping
    public ResponseEntity<CollectionModel<PersonelDTO>> wylistujPersonel() {
        List<PersonelDTO> personel = personelService.wylistujPersonel().stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<PersonelDTO> model = CollectionModel.of(personel);
        model.add(linkTo(methodOn(PersonelController.class).wylistujPersonel()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{pwzId}")
    public ResponseEntity<PersonelDTO> pobierzPracownika(@PathVariable String pwzId) {
        try {
            Personel personel = personelService.pobierzPracownika(pwzId);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(personel));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PersonelDTO> dodajPracownika(@RequestBody PersonelDTO wejsciowyDto) {
        try {
            Personel nowy = personelService.utworzPracownika(wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(nowy));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{pwzId}")
    public ResponseEntity<PersonelDTO> aktualizujPracownika(@PathVariable String pwzId, @RequestBody PersonelDTO wejsciowyDto) {
        try {
            Personel zaktualizowany = personelService.aktualizujPracownika(pwzId, wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(zaktualizowany));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{pwzId}")
    public ResponseEntity<Void> usunPracownika(@PathVariable String pwzId) {
        try {
            personelService.usunPracownika(pwzId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PersonelDTO konwertujNaDtoZLinkami(Personel personel) {
        PersonelDTO dto = new PersonelDTO(personel);
        dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(personel.getPwzId())).withSelfRel());
        return dto;
    }
}