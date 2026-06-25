package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.LekDTO;
import pl.polsl.przychodnia.entities.Lek;
import pl.polsl.przychodnia.services.LekService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/leki")
@RequiredArgsConstructor
public class LekController {

    private final LekService lekService;

    @GetMapping
    public ResponseEntity<CollectionModel<LekDTO>> wylistujLeki() {
        List<LekDTO> leki = lekService.wylistujLeki().stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<LekDTO> model = CollectionModel.of(leki);
        model.add(linkTo(methodOn(LekController.class).wylistujLeki()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LekDTO> pobierzLek(@PathVariable Integer id) {
        try {
            Lek lek = lekService.pobierzLek(id);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(lek));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LekDTO> dodajLek(@RequestBody LekDTO wejsciowyDto) {
        try {
            Lek nowy = lekService.utworzLek(wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(nowy));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LekDTO> aktualizujLek(@PathVariable Integer id, @RequestBody LekDTO wejsciowyDto) {
        try {
            Lek zaktualizowany = lekService.aktualizujLek(id, wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(zaktualizowany));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunLek(@PathVariable Integer id) {
        try {
            lekService.usunLek(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private LekDTO konwertujNaDtoZLinkami(Lek lek) {
        LekDTO dto = new LekDTO(lek);
        dto.add(linkTo(methodOn(LekController.class).pobierzLek(lek.getId())).withSelfRel());
        return dto;
    }
}