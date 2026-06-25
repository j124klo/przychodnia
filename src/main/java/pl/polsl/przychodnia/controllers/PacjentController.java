package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.PacjentDTO;
import pl.polsl.przychodnia.entities.Pacjent;
import pl.polsl.przychodnia.services.PacjentService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pacjenci")
@RequiredArgsConstructor
public class PacjentController {

    private final PacjentService pacjentService;

    @GetMapping
    public ResponseEntity<CollectionModel<PacjentDTO>> wylistujPacjentow() {
        List<PacjentDTO> pacjenci = pacjentService.wylistujPacjentow().stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<PacjentDTO> model = CollectionModel.of(pacjenci);
        model.add(linkTo(methodOn(PacjentController.class).wylistujPacjentow()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<PacjentDTO> pobierzPacjenta(@PathVariable String pesel) {
        try {
            Pacjent pacjent = pacjentService.pobierzPacjenta(pesel);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(pacjent));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PacjentDTO> dodajPacjenta(@RequestBody PacjentDTO wejsciowyDto) {
        try {
            Pacjent nowyPacjent = pacjentService.utworzPacjenta(wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(nowyPacjent));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{pesel}")
    public ResponseEntity<PacjentDTO> aktualizujPacjenta(@PathVariable String pesel, @RequestBody PacjentDTO wejsciowyDto) {
        try {
            Pacjent zaktualizowany = pacjentService.aktualizujPacjenta(pesel, wejsciowyDto);
            return ResponseEntity.ok(konwertujNaDtoZLinkami(zaktualizowany));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{pesel}")
    public ResponseEntity<Void> usunPacjenta(@PathVariable String pesel) {
        try {
            pacjentService.usunPacjenta(pesel);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Pomocnicza metoda budująca strukturę HATEOAS
    private PacjentDTO konwertujNaDtoZLinkami(Pacjent pacjent) {
        PacjentDTO dto = new PacjentDTO(pacjent);
        
        // Link self do profilu pacjenta
        dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(pacjent.getPesel())).withSelfRel());
        
        // Dodatkowe relacyjne URI: Link przekierowujący do historii wizyt tego pacjenta
        dto.add(linkTo(methodOn(WizytaController.class).pobierzHistoriePacjenta(pacjent.getPesel())).withRel("historia_wizyt"));
        
        return dto;
    }
}