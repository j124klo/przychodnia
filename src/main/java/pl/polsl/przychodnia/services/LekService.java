package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.LekDTO;
import pl.polsl.przychodnia.entities.Lek;
import pl.polsl.przychodnia.repositories.LekRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class LekService {

    private final LekRepository lekRepository;

    public List<Lek> wylistujLeki() {
        return StreamSupport.stream(lekRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Lek pobierzLek(Integer id) {
        return lekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono leku o ID: " + id));
    }

    public Lek utworzLek(LekDTO dto) {
        Lek lek = new Lek();
        mapujPola(dto, lek);
        return lekRepository.save(lek);
    }

    public Lek aktualizujLek(Integer id, LekDTO dto) {
        Lek lek = lekRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono leku o ID: " + id));
        
        mapujPola(dto, lek);
        return lekRepository.save(lek);
    }

    public void usunLek(Integer id) {
        if (!lekRepository.existsById(id)) {
            throw new RuntimeException("Nie znaleziono leku o ID: " + id);
        }
        lekRepository.deleteById(id);
    }

    private void mapujPola(LekDTO dto, Lek lek) {
        lek.setNazwaLeku(dto.getNazwaLeku());
        lek.setDawka(dto.getDawka());
        lek.setSubstancjaAktywna(dto.getSubstancjaAktywna());
    }
}