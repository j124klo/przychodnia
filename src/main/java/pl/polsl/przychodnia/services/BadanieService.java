package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.BadanieDTO;
import pl.polsl.przychodnia.entities.Badanie;
import pl.polsl.przychodnia.repositories.BadanieRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class BadanieService {

    private final BadanieRepository badanieRepository;

    public List<Badanie> wylistujBadania() {
        return StreamSupport.stream(badanieRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Badanie pobierzBadanie(Integer id) {
        return badanieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono badania o ID: " + id));
    }

    public Badanie utworzBadanie(BadanieDTO dto) {
        Badanie badanie = new Badanie();
        mapujPola(dto, badanie);
        return badanieRepository.save(badanie);
    }

    public Badanie aktualizujBadanie(Integer id, BadanieDTO dto) {
        Badanie badanie = badanieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono badania o ID: " + id));
        
        mapujPola(dto, badanie);
        return badanieRepository.save(badanie);
    }

    public void usunBadanie(Integer id) {
        if (!badanieRepository.existsById(id)) {
            throw new RuntimeException("Nie znaleziono badania o ID: " + id);
        }
        badanieRepository.deleteById(id);
    }

    private void mapujPola(BadanieDTO dto, Badanie badanie) {
        badanie.setNazwaBadania(dto.getNazwaBadania());
    }
}