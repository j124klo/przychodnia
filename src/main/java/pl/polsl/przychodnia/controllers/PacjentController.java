package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.PacjentDTO;
import pl.polsl.przychodnia.entities.Pacjent;
import pl.polsl.przychodnia.repositories.PacjentRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {

    @Autowired
    private PacjentRepository pacjentRepository;

    // POST /pacjenci - Dodanie nowego pacjenta (Scenariusz 1 i 2)
    @PostMapping
    public ResponseEntity<PacjentDTO> dodajPacjenta(@RequestBody Pacjent pacjent) {
        // Dzięki cascade = CascadeType.ALL w encji Pacjent, jeśli w JSONie prześlesz
        // obiekt pacjenta wraz z zagnieżdżoną listą wizyt, Spring zapisze je
        // automatycznie!
        Pacjent zapisany = pacjentRepository.save(pacjent);

        PacjentDTO dto = new PacjentDTO(zapisany);
        dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(dto.getPesel())).withSelfRel());
        return ResponseEntity.ok(dto);
    }

    // GET /pacjenci/{pesel} - Pobranie profilu pacjenta
    @GetMapping("/{pesel}")
    public ResponseEntity<PacjentDTO> pobierzPacjenta(@PathVariable String pesel) {
        return pacjentRepository.findById(pesel)
                .map(pacjent -> {
                    PacjentDTO dto = new PacjentDTO(pacjent);
                    // Link do samego siebie (self)
                    dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(pesel)).withSelfRel());
                    // HATEOAS: Link relacyjny do historii wizyt tego pacjenta
                    dto.add(linkTo(methodOn(WizytaController.class).pobierzHistoriePacjenta(pesel))
                            .withRel("historia_wizyt"));
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /pacjenci - Wylistowanie wszystkich pacjentów (READ ALL)
    @GetMapping
    public ResponseEntity<CollectionModel<PacjentDTO>> wylistujPacjentow() {
        List<PacjentDTO> pacjenci = StreamSupport.stream(pacjentRepository.findAll().spliterator(), false)
                .map(p -> {
                    PacjentDTO dto = new PacjentDTO(p);
                    dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(p.getPesel())).withSelfRel());
                    // Link do historii wizyt w HATEOAS
                    dto.add(linkTo(methodOn(WizytaController.class).pobierzHistoriePacjenta(p.getPesel())).withRel("historia_wizyt"));
                    return dto;
                })
                .collect(Collectors.toList());

        CollectionModel<PacjentDTO> model = CollectionModel.of(pacjenci);
        model.add(linkTo(methodOn(PacjentController.class).wylistujPacjentow()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    // PUT /pacjenci/{pesel} - Aktualizacja danych pacjenta (UPDATE)
    @PutMapping("/{pesel}")
    public ResponseEntity<PacjentDTO> aktualizujPacjenta(@PathVariable String pesel, @RequestBody Pacjent zaktualizowanyPacjent) {
        return pacjentRepository.findById(pesel)
                .map(pacjent -> {
                    // Aktualizujemy wybrane pola (PESELu nie zmieniamy, bo to klucz główny)
                    pacjent.setImie(zaktualizowanyPacjent.getImie());
                    pacjent.setNazwisko(zaktualizowanyPacjent.getNazwisko());
                    pacjent.setDataUr(zaktualizowanyPacjent.getDataUr());
                    // Jeśli macie w encji więcej pól (jak wzrost, masa), można je tu dopisać
                    
                    Pacjent zapisany = pacjentRepository.save(pacjent);
                    
                    PacjentDTO dto = new PacjentDTO(zapisany);
                    dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(dto.getPesel())).withSelfRel());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

     // DELETE /pacjenci/{pesel} - Usunięcie kartoteki
    @DeleteMapping("/{pesel}")
    public ResponseEntity<Void> usunPacjenta(@PathVariable String pesel) {
        if (pacjentRepository.existsById(pesel)) {
            pacjentRepository.deleteById(pesel);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}