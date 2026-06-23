package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.ChorobaDTO;
import pl.polsl.przychodnia.entities.Choroba;
import pl.polsl.przychodnia.repositories.ChorobaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/choroby")
public class ChorobaController {

    @Autowired
    private ChorobaRepository chorobaRepository;

    @PostMapping
    public ResponseEntity<ChorobaDTO> dodajChorobe(@RequestBody Choroba choroba) {
        Choroba zapisana = chorobaRepository.save(choroba);
        ChorobaDTO dto = new ChorobaDTO(zapisana);
        dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(dto.getKod())).withSelfRel());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{kod}")
    public ResponseEntity<ChorobaDTO> pobierzChorobe(@PathVariable String kod) {
        return chorobaRepository.findById(kod)
                .map(choroba -> {
                    ChorobaDTO dto = new ChorobaDTO(choroba);
                    dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(kod)).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ChorobaDTO>> wylistujChoroby() {
        List<ChorobaDTO> choroby = StreamSupport.stream(chorobaRepository.findAll().spliterator(), false)
                .map(c -> {
                    ChorobaDTO dto = new ChorobaDTO(c);
                    dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(c.getIcd10())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<ChorobaDTO> model = CollectionModel.of(choroby);
        model.add(linkTo(methodOn(ChorobaController.class).wylistujChoroby()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{kod}")
    public ResponseEntity<ChorobaDTO> aktualizujChorobe(@PathVariable String kod, @RequestBody Choroba zaktualizowanaChoroba) {
        return chorobaRepository.findById(kod)
                .map(choroba -> {
                    choroba.setNazwaChoroby(zaktualizowanaChoroba.getNazwaChoroby()); // Uaktualnij pozostałe pola wg potrzeb
                    Choroba zapisana = chorobaRepository.save(choroba);
                    ChorobaDTO dto = new ChorobaDTO(zapisana);
                    dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(dto.getKod())).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{kod}")
    public ResponseEntity<Void> usunChorobe(@PathVariable String kod) {
        if (chorobaRepository.existsById(kod)) {
            chorobaRepository.deleteById(kod);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}