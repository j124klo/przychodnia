package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.ChorobaDTO;
import pl.polsl.przychodnia.entities.Choroba;
import pl.polsl.przychodnia.repositories.ChorobaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class ChorobaService {

    private final ChorobaRepository chorobaRepository;

    public List<Choroba> wylistujChoroby() {
        return StreamSupport.stream(chorobaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Choroba pobierzChorobe(String icd10) {
        return chorobaRepository.findById(icd10)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono choroby o kodzie ICD10: " + icd10));
    }

    public Choroba utworzChorobe(ChorobaDTO dto) {
        if (chorobaRepository.existsById(dto.getIcd10())) {
            throw new RuntimeException("Choroba o podanym kodzie ICD10 juz istnieje: " + dto.getIcd10());
        }
        Choroba choroba = new Choroba();
        choroba.setIcd10(dto.getIcd10());
        mapujPola(dto, choroba);
        return chorobaRepository.save(choroba);
    }

    public Choroba aktualizujChorobe(String icd10, ChorobaDTO dto) {
        Choroba choroba = chorobaRepository.findById(icd10)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono choroby o kodzie ICD10: " + icd10));
        
        mapujPola(dto, choroba);
        return chorobaRepository.save(choroba);
    }

    public void usunChorobe(String icd10) {
        if (!chorobaRepository.existsById(icd10)) {
            throw new RuntimeException("Nie znaleziono choroby o kodzie ICD10: " + icd10);
        }
        chorobaRepository.deleteById(icd10);
    }

    private void mapujPola(ChorobaDTO dto, Choroba choroba) {
        choroba.setNazwaChoroby(dto.getNazwaChoroby());
    }
}