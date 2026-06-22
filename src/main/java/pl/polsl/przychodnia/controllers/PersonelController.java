package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.PersonelDTO;
import pl.polsl.przychodnia.entities.Personel;
import pl.polsl.przychodnia.repositories.PersonelRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/personel")
public class PersonelController {

    @Autowired
    private PersonelRepository personelRepository;

    // POST /personel - Dodanie nowego pracownika medycznego
    @PostMapping
    public ResponseEntity<PersonelDTO> dodajPracownika(@RequestBody Personel personel) {
        Personel zapisany = personelRepository.save(personel);
        PersonelDTO dto = new PersonelDTO(zapisany);
        dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(dto.getPwzId())).withSelfRel());
        return ResponseEntity.ok(dto);
    }

    // GET /personel/{pwzId} - Pobranie danych konkretnego pracownika
    @GetMapping("/{pwzId}")
    public ResponseEntity<PersonelDTO> pobierzPracownika(@PathVariable String pwzId) {
        return personelRepository.findById(pwzId)
                .map(personel -> {
                    PersonelDTO dto = new PersonelDTO(personel);
                    dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(pwzId)).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /personel - Listowanie całego personelu przychodni (Użycie
    // CollectionModel dla HATEOAS)
    @GetMapping
    public ResponseEntity<CollectionModel<PersonelDTO>> wylistujPersonel() {
        List<PersonelDTO> personelList = StreamSupport.stream(personelRepository.findAll().spliterator(), false)
                .map(p -> {
                    PersonelDTO dto = new PersonelDTO(p);
                    dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(p.getPwzId())).withSelfRel());
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<PersonelDTO> model = CollectionModel.of(personelList);
        model.add(linkTo(methodOn(PersonelController.class).wylistujPersonel()).withSelfRel());
        return ResponseEntity.ok(model);
    }
}