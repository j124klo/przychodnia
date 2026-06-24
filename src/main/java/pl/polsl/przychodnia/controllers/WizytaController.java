package pl.polsl.przychodnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.WizytaDTO;
import pl.polsl.przychodnia.entities.Wizyta;
import pl.polsl.przychodnia.repositories.WizytaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/wizyty")
@Transactional
public class WizytaController {

    @Autowired
    private WizytaRepository wizytaRepository;

    // POST /wizyty - Planowanie standardowej nowej wizyty
    @PostMapping
    public ResponseEntity<WizytaDTO> umowWizyta(@RequestBody Wizyta wizyta) {
        Wizyta zapisana = wizytaRepository.save(wizyta);
        return ResponseEntity.ok(konwertujNaDtoZLinkami(zapisana));
    }

    // GET /wizyty/{id} - Pobranie szczegółów pojedynczej wizyty
    @GetMapping("/{id}")
    public ResponseEntity<WizytaDTO> pobierzWizyte(@PathVariable Integer id) {
        return wizytaRepository.findById(id)
                .map(wizyta -> ResponseEntity.ok(konwertujNaDtoZLinkami(wizyta)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /wizyty/pacjent/{pesel} - Scenariusz Zaawansowany 1: Historia medyczna
    // pacjenta
    @GetMapping("/pacjent/{pesel}")
    public ResponseEntity<CollectionModel<WizytaDTO>> pobierzHistoriePacjenta(@PathVariable String pesel) {
        List<WizytaDTO> wizyty = wizytaRepository.findByPacjentPesel(pesel).stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).pobierzHistoriePacjenta(pesel)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    // GET /wizyty/grafik/{pwzId} - Scenariusz Zaawansowany 2: Harmonogram lekarza
    // na dany dzień
    @GetMapping("/grafik/{pwzId}")
    public ResponseEntity<CollectionModel<WizytaDTO>> pobierzGrafikLekarza(
            @PathVariable String pwzId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<WizytaDTO> wizyty = wizytaRepository.findByPersonelPwzIdAndData(pwzId, data).stream()
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).pobierzGrafikLekarza(pwzId, data)).withSelfRel());
        return ResponseEntity.ok(model);
    }


    // GET /wizyty - Wylistowanie wszystkich wizyt (READ ALL)
    @GetMapping
    public ResponseEntity<CollectionModel<WizytaDTO>> wylistujWizyty() {
        List<WizytaDTO> wizyty = StreamSupport.stream(wizytaRepository.findAll().spliterator(), false)
                .map(this::konwertujNaDtoZLinkami)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).wylistujWizyty()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    // PUT /wizyty/{id} - Aktualizacja szczegółów wizyty (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<WizytaDTO> aktualizujWizyte(@PathVariable Integer id, @RequestBody Wizyta zaktualizowanaWizyta) {
        return wizytaRepository.findById(id)
                .map(wizyta -> {
                    wizyta.setData(zaktualizowanaWizyta.getData());
                    wizyta.setAdresPrzychodni(zaktualizowanaWizyta.getAdresPrzychodni());
                    wizyta.setDiagnoza(zaktualizowanaWizyta.getDiagnoza());
                    
                    Wizyta zapisana = wizytaRepository.save(wizyta);
                    return ResponseEntity.ok(konwertujNaDtoZLinkami(zapisana));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /wizyty/{id} - Usunięcie wizyty (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunWizyte(@PathVariable Integer id) {
        if (wizytaRepository.existsById(id)) {
            wizytaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Pomocnicza metoda budująca strukturę HATEOAS
    private WizytaDTO konwertujNaDtoZLinkami(Wizyta wizyta) {
        WizytaDTO dto = new WizytaDTO(wizyta);
        
        // Link self do konkretnej wizyty
        dto.add(linkTo(methodOn(WizytaController.class).pobierzWizyte(wizyta.getId())).withSelfRel());
        
        // Relacyjny link do pacjenta
        if (wizyta.getPacjent() != null) {
            dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(wizyta.getPacjent().getPesel())).withRel("pacjent"));
        }

        // Linki do przypisanego personelu
        if (wizyta.getPersonel() != null) {
            wizyta.getPersonel().forEach(personel -> 
                dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(personel.getPwzId())).withRel("personel"))
            );
        }

        // Linki do zdiagnozowanych chorób
        if (wizyta.getChoroby() != null) {
            wizyta.getChoroby().forEach(choroba -> 
                // Uwaga: upewnij się, że getKod() to poprawna nazwa gettera dla ICD10 w klasie Choroba
                dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(choroba.getIcd10())).withRel("choroba"))
            );
        }

        // Linki do zleconych badań
        if (wizyta.getBadania() != null) {
            wizyta.getBadania().forEach(badanie -> 
                dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(badanie.getId())).withRel("badanie"))
            );
        }

        // Linki do przypisanych leków
        if (wizyta.getLeki() != null) {
            wizyta.getLeki().forEach(lek -> 
                dto.add(linkTo(methodOn(LekController.class).pobierzLek(lek.getId())).withRel("lek"))
            );
        }

        return dto;
    }
}