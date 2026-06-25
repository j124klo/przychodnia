package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.PacjentDTO;
import pl.polsl.przychodnia.entities.Pacjent;
import pl.polsl.przychodnia.repositories.PacjentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PacjentService {

    private final PacjentRepository pacjentRepository;

    public List<Pacjent> wylistujPacjentow() {
        return StreamSupport.stream(pacjentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Pacjent pobierzPacjenta(String pesel) {
        return pacjentRepository.findById(pesel)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pacjenta o PESELu: " + pesel));
    }

    public Pacjent utworzPacjenta(PacjentDTO dto) {
        if (pacjentRepository.existsById(dto.getPesel())) {
            throw new RuntimeException("Pacjent o podanym PESELu juz istnieje: " + dto.getPesel());
        }
        Pacjent pacjent = new Pacjent();
        pacjent.setPesel(dto.getPesel());
        mapujPola(dto, pacjent);
        return pacjentRepository.save(pacjent);
    }

    public Pacjent aktualizujPacjenta(String pesel, PacjentDTO dto) {
        Pacjent pacjent = pacjentRepository.findById(pesel)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pacjenta o PESEL: " + pesel));
        
        mapujPola(dto, pacjent);
        return pacjentRepository.save(pacjent);
    }

    public void usunPacjenta(String pesel) {
        if (!pacjentRepository.existsById(pesel)) {
            throw new RuntimeException("Nie znaleziono pacjenta o PESELu: " + pesel);
        }
        pacjentRepository.deleteById(pesel);
    }

    // Pomocnicze mapowanie z DTO wejściowego do Encji bazodanowej
    private void mapujPola(PacjentDTO dto, Pacjent pacjent) {
        pacjent.setImie(dto.getImie());
        pacjent.setNazwisko(dto.getNazwisko());
        pacjent.setPlec(dto.getPlec());
        pacjent.setDataUr(dto.getDataUr());
        pacjent.setWzrost(dto.getWzrost());
        pacjent.setMasa(dto.getMasa());
        pacjent.setGrupaKrwi(dto.getGrupaKrwi());
    }
}