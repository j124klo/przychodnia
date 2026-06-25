package pl.polsl.przychodnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.przychodnia.dto.WizytaDTO;
import pl.polsl.przychodnia.services.WizytaService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/wizyty")
@RequiredArgsConstructor
public class WizytaController {

    private final WizytaService wizytaService;

    @GetMapping
    public ResponseEntity<CollectionModel<WizytaDTO>> wylistujWizyty() {
        List<WizytaDTO> wizyty = wizytaService.wylistujWizyty().stream()
                .map(this::dodajLinkiHateoas)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).wylistujWizyty()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WizytaDTO> pobierzWizyte(@PathVariable Integer id) {
        try {
            WizytaDTO dto = wizytaService.pobierzWizyte(id);
            return ResponseEntity.ok(dodajLinkiHateoas(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pacjent/{pesel}")
    public ResponseEntity<CollectionModel<WizytaDTO>> pobierzHistoriePacjenta(@PathVariable String pesel) {
        List<WizytaDTO> wizyty = wizytaService.pobierzHistoriePacjenta(pesel).stream()
                .map(this::dodajLinkiHateoas)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).pobierzHistoriePacjenta(pesel)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/grafik/{pwzId}")
    public ResponseEntity<CollectionModel<WizytaDTO>> pobierzGrafikLekarza(
            @PathVariable String pwzId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<WizytaDTO> wizyty = wizytaService.pobierzGrafikLekarza(pwzId, data).stream()
                .map(this::dodajLinkiHateoas)
                .collect(Collectors.toList());

        CollectionModel<WizytaDTO> model = CollectionModel.of(wizyty);
        model.add(linkTo(methodOn(WizytaController.class).pobierzGrafikLekarza(pwzId, data)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<WizytaDTO> dodajWizyte(@RequestBody WizytaDTO wejsciowyDto) {
        try {
            WizytaDTO noweDto = wizytaService.utworzWizyte(wejsciowyDto);
            return ResponseEntity.ok(dodajLinkiHateoas(noweDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WizytaDTO> aktualizujWizyte(@PathVariable Integer id, @RequestBody WizytaDTO wejsciowyDto) {
        try {
            WizytaDTO zaktualizowaneDto = wizytaService.aktualizujWizyte(id, wejsciowyDto);
            return ResponseEntity.ok(dodajLinkiHateoas(zaktualizowaneDto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> usunWizyte(@PathVariable Integer id) {
        try {
            wizytaService.usunWizyte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metoda pomocnicza przyjmująca DTO, pracująca wyłącznie na mapowanych listach ID
    private WizytaDTO dodajLinkiHateoas(WizytaDTO dto) {
        dto.add(linkTo(methodOn(WizytaController.class).pobierzWizyte(dto.getId())).withSelfRel());
        
        if (dto.getPacjentPesel() != null) {
            dto.add(linkTo(methodOn(PacjentController.class).pobierzPacjenta(dto.getPacjentPesel())).withRel("pacjent"));
        }
        if (dto.getPersonelPwzIds() != null) {
            dto.getPersonelPwzIds().forEach(pwzId -> 
                dto.add(linkTo(methodOn(PersonelController.class).pobierzPracownika(pwzId)).withRel("personel"))
            );
        }
        if (dto.getChorobyIcd10() != null) {
            dto.getChorobyIcd10().forEach(icd10 -> 
                dto.add(linkTo(methodOn(ChorobaController.class).pobierzChorobe(icd10)).withRel("choroba"))
            );
        }
        if (dto.getBadaniaIds() != null) {
            dto.getBadaniaIds().forEach(id -> 
                dto.add(linkTo(methodOn(BadanieController.class).pobierzBadanie(id)).withRel("badanie"))
            );
        }
        if (dto.getLekiIds() != null) {
            dto.getLekiIds().forEach(id -> 
                dto.add(linkTo(methodOn(LekController.class).pobierzLek(id)).withRel("lek"))
            );
        }
        return dto;
    }
}