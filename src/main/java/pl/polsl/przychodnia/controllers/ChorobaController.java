package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.ChorobaDTO;
import pl.polsl.przychodnia.entities.Choroba;
import pl.polsl.przychodnia.services.ChorobaService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/choroby")
@RequiredArgsConstructor
public class ChorobaController {

    private final ChorobaService chorobaService;

    @GetMapping
    public ResponseEntity<CollectionModel<ChorobaDTO>> wylistujChoroby() {
        List<ChorobaDTO> choroby = chorobaService.wylistujChoroby().stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<ChorobaDTO> model = CollectionModel.of(choroby);
        model.add(linkTo(methodOn(ChorobaController.class).wylistujChoroby()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{icd10}")
    public ResponseEntity<ChorobaDTO> pobierzChorobe(@PathVariable String icd10) {
        try {
            Choroba choroba = chorobaService.pobierzChorobe(icd10);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(choroba));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ChorobaDTO> dodajChorobe(@RequestBody ChorobaDTO wejsciowyDto) {
        try {
            Choroba nowa = chorobaService.utworzChorobe(wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(nowa));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{icd10}")
    public ResponseEntity<ChorobaDTO> aktualizujChorobe(@PathVariable String icd10, @RequestBody ChorobaDTO wejsciowyDto) {
        try {
            Choroba zaktualizowana = chorobaService.aktualizujChorobe(icd10, wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(zaktualizowana));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{icd10}")
    public ResponseEntity<Void> usunChorobe(@PathVariable String icd10) {
        try {
            chorobaService.usunChorobe(icd10);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ChorobaDTO konwertujNaDtoZLinkami(Choroba choroba) {
        ChorobaDTO dto = new ChorobaDTO(choroba);
        dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(choroba.getIcd10())).withSelfRel());
        return dto;
    }
}