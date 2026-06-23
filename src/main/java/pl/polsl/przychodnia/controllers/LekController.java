package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.LekDTO;
import pl.polsl.przychodnia.entities.Lek;
import pl.polsl.przychodnia.repositories.LekRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/leki")
public class LekController {

    @Autowired
    private LekRepository lekRepository;

    @PostMapping
    public ResponseEntity<LekDTO> dodajLek(@RequestBody Lek lek) {
        Lek zapisany = lekRepository.save(lek);
        LekDTO dto = new LekDTO(zapisany);
        dto.add(linkTo(methodOn(LekController.class).pobierzLek(dto.getId())).withSelfRel());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LekDTO> pobierzLek(@PathVariable Integer id) {
        return lekRepository.findById(id)
                .map(lek -> {
                    LekDTO dto = new LekDTO(lek);
                    dto.add(linkTo(methodOn(LekController.class).pobierzLek(id)).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CollectionModel<LekDTO>> wylistujLeki() {
        List<LekDTO> leki = StreamSupport.stream(lekRepository.findAll().spliterator(), false)
                .map(l -> {
                    LekDTO dto = new LekDTO(l);
                    dto.add(linkTo(methodOn(LekController.class).pobierzLek(l.getId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<LekDTO> model = CollectionModel.of(leki);
        model.add(linkTo(methodOn(LekController.class).wylistujLeki()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LekDTO> aktualizujLek(@PathVariable Integer id, @RequestBody Lek zaktualizowanyLek) {
        return lekRepository.findById(id)
                .map(lek -> {
                    lek.setNazwaLeku(zaktualizowanyLek.getNazwaLeku());
                    Lek zapisany = lekRepository.save(lek);
                    LekDTO dto = new LekDTO(zapisany);
                    dto.add(linkTo(methodOn(LekController.class).pobierzLek(dto.getId())).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunLek(@PathVariable Integer id) {
        if (lekRepository.existsById(id)) {
            lekRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}