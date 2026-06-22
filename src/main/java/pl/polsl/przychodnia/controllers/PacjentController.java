package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.PacjentDTO;
import pl.polsl.przychodnia.entities.Pacjent;
import pl.polsl.przychodnia.repositories.PacjentRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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