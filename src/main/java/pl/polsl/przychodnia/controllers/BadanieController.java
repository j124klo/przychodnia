package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.BadanieDTO;
import pl.polsl.przychodnia.entities.Badanie;
import pl.polsl.przychodnia.services.BadanieService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/badania")
@RequiredArgsConstructor
public class BadanieController {

    private final BadanieService badanieService;

    @GetMapping
    public ResponseEntity<CollectionModel<BadanieDTO>> wylistujBadania() {
        List<BadanieDTO> badania = badanieService.wylistujBadania().stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<BadanieDTO> model = CollectionModel.of(badania);
        model.add(linkTo(methodOn(BadanieController.class).wylistujBadania()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadanieDTO> pobierzBadanie(@PathVariable Integer id) {
        try {
            Badanie badanie = badanieService.pobierzBadanie(id);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(badanie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BadanieDTO> dodajBadanie(@RequestBody BadanieDTO wejsciowyDto) {
        try {
            Badanie nowe = badanieService.utworzBadanie(wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(nowe));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BadanieDTO> aktualizujBadanie(@PathVariable Integer id, @RequestBody BadanieDTO wejsciowyDto) {
        try {
            Badanie zaktualizowane = badanieService.aktualizujBadanie(id, wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(zaktualizowane));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunBadanie(@PathVariable Integer id) {
        try {
            badanieService.usunBadanie(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private BadanieDTO konwertujNaDtoZLinkami(Badanie badanie) {
        BadanieDTO dto = new BadanieDTO(badanie);
        dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(badanie.getId())).withSelfRel());
        return dto;
    }
}