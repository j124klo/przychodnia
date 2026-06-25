package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.PersonelDTO;
import pl.polsl.przychodnia.entities.Personel;
import pl.polsl.przychodnia.repositories.PersonelRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonelService {

    private final PersonelRepository personelRepository;

    public List<Personel> wylistujPersonel() {
        return StreamSupport.stream(personelRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Personel pobierzPracownika(String pwzId) {
        return personelRepository.findById(pwzId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pracownika o PWZ ID: " + pwzId));
    }

    public Personel utworzPracownika(PersonelDTO dto) {
        if (personelRepository.existsById(dto.getPwzId())) {
            throw new RuntimeException("Pracownik o podanym PWZ ID juz istnieje: " + dto.getPwzId());
        }
        Personel personel = new Personel();
        personel.setPwzId(dto.getPwzId());
        mapujPola(dto, personel);
        return personelRepository.save(personel);
    }

    public Personel aktualizujPracownika(String pwzId, PersonelDTO dto) {
        Personel personel = personelRepository.findById(pwzId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono pracownika o PWZ ID: " + pwzId));
        
        mapujPola(dto, personel);
        return personelRepository.save(personel);
    }

    public void usunPracownika(String pwzId) {
        if (!personelRepository.existsById(pwzId)) {
            throw new RuntimeException("Nie znaleziono pracownika o PWZ ID: " + pwzId);
        }
        personelRepository.deleteById(pwzId);
    }

    private void mapujPola(PersonelDTO dto, Personel personel) {
        personel.setImie(dto.getImie());
        personel.setNazwisko(dto.getNazwisko());
        personel.setZawod(dto.getZawod());
    }
}