package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.BadanieDTO;
import pl.polsl.przychodnia.entities.Badanie;
import pl.polsl.przychodnia.repositories.BadanieRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/badania")
public class BadanieController {

    @Autowired
    private BadanieRepository badanieRepository;

    @PostMapping
    public ResponseEntity<BadanieDTO> dodajBadanie(@RequestBody Badanie badanie) {
        Badanie zapisane = badanieRepository.save(badanie);
        BadanieDTO dto = new BadanieDTO(zapisane);
        dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(dto.getId())).withSelfRel());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadanieDTO> pobierzBadanie(@PathVariable Integer id) {
        return badanieRepository.findById(id)
                .map(badanie -> {
                    BadanieDTO dto = new BadanieDTO(badanie);
                    dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(id)).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CollectionModel<BadanieDTO>> wylistujBadania() {
        List<BadanieDTO> badania = StreamSupport.stream(badanieRepository.findAll().spliterator(), false)
                .map(b -> {
                    BadanieDTO dto = new BadanieDTO(b);
                    dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(b.getId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<BadanieDTO> model = CollectionModel.of(badania);
        model.add(linkTo(methodOn(BadanieController.class).wylistujBadania()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BadanieDTO> aktualizujBadanie(@PathVariable Integer id, @RequestBody Badanie zaktualizowaneBadanie) {
        return badanieRepository.findById(id)
                .map(badanie -> {
                    badanie.setNazwaBadania(zaktualizowaneBadanie.getNazwaBadania());
                    Badanie zapisane = badanieRepository.save(badanie);
                    BadanieDTO dto = new BadanieDTO(zapisane);
                    dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(dto.getId())).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunBadanie(@PathVariable Integer id) {
        if (badanieRepository.existsById(id)) {
            badanieRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}