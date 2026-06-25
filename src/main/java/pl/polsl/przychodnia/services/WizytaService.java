package pl.polsl.przychodnia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.przychodnia.dto.WizytaDTO;
import pl.polsl.przychodnia.entities.*;
import pl.polsl.przychodnia.repositories.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class WizytaService {

    private final WizytaRepository wizytaRepository;
    private final PacjentRepository pacjentRepository;
    private final PersonelRepository personelRepository;
    private final ChorobaRepository chorobaRepository;
    private final BadanieRepository badanieRepository;
    private final LekRepository lekRepository;

    public List<WizytaDTO> wylistujWizyty() {
        return StreamSupport.stream(wizytaRepository.findAll().spliterator(), false)
                .map(WizytaDTO::new) // Tworzymy DTO wewnątrz otwartej transakcji!
                .collect(Collectors.toList());
    }

    public WizytaDTO pobierzWizyte(Integer id) {
        Wizyta wizyta = wizytaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wizyty o ID: " + id));
        return new WizytaDTO(wizyta);
    }

    public List<WizytaDTO> pobierzHistoriePacjenta(String pesel) {
        return wizytaRepository.findByPacjentPesel(pesel).stream()
                .map(WizytaDTO::new)
                .collect(Collectors.toList());
    }

    public List<WizytaDTO> pobierzGrafikLekarza(String pwzId, LocalDate data) {
        return wizytaRepository.findByPersonelPwzIdAndData(pwzId, data).stream()
                .map(WizytaDTO::new)
                .collect(Collectors.toList());
    }

    public WizytaDTO utworzWizyte(WizytaDTO dto) {
        Wizyta wizyta = new Wizyta();
        mapujProstePola(dto, wizyta);
        aktualizujRelacjeWizyty(dto, wizyta);
        Wizyta zapisana = wizytaRepository.save(wizyta);
        return new WizytaDTO(zapisana);
    }

    public WizytaDTO aktualizujWizyte(Integer id, WizytaDTO dto) {
        Wizyta wizyta = wizytaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wizyty o ID: " + id));
        
        mapujProstePola(dto, wizyta);
        aktualizujRelacjeWizyty(dto, wizyta);
        
        Wizyta zapisana = wizytaRepository.save(wizyta);
        return new WizytaDTO(zapisana);
    }

    public void usunWizyte(Integer id) {
        if (!wizytaRepository.existsById(id)) {
            throw new RuntimeException("Nie znaleziono wizyty o ID: " + id);
        }
        wizytaRepository.deleteById(id);
    }

    private void mapujProstePola(WizytaDTO dto, Wizyta wizyta) {
        wizyta.setAdresPrzychodni(dto.getAdresPrzychodni());
        wizyta.setData(dto.getData());
        wizyta.setDiagnoza(dto.getDiagnoza());
        wizyta.setRecepta(dto.getRecepta());
        wizyta.setSkierowanie(dto.getSkierowanie());
    }

    private void aktualizujRelacjeWizyty(WizytaDTO dto, Wizyta wizyta) {
        // 1. Relacja z Pacjentem (Many-to-One)
        if (dto.getPacjentPesel() != null) {
            Pacjent pacjent = pacjentRepository.findById(dto.getPacjentPesel())
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono pacjenta o PESELu: " + dto.getPacjentPesel()));
            wizyta.setPacjent(pacjent);
        }

        // 2. Relacja z Personelem (Many-to-Many)
        if (dto.getPersonelPwzIds() != null) {
            Set<Personel> personelList = new HashSet<>();
            for (String pwzId : dto.getPersonelPwzIds()) {
                Personel p = personelRepository.findById(pwzId)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono pracownika o PWZ ID: " + pwzId));
                personelList.add(p);
            }
            wizyta.setPersonel(personelList);
        }

        // 3. Relacja z Chorobami (Many-to-Many)
        if (dto.getChorobyIcd10() != null) {
            Set<Choroba> chorobyList = new HashSet<>();
            for (String icd10 : dto.getChorobyIcd10()) {
                Choroba c = chorobaRepository.findById(icd10)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono choroby o ICD10: " + icd10));
                chorobyList.add(c);
            }
            wizyta.setChoroby(chorobyList);
        }

        // 4. Relacja z Badaniami (Many-to-Many)
        if (dto.getBadaniaIds() != null) {
            Set<Badanie> badaniaList = new HashSet<>();
            for (Integer badanieId : dto.getBadaniaIds()) {
                Badanie b = badanieRepository.findById(badanieId)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono badania o ID: " + badanieId));
                badaniaList.add(b);
            }
            wizyta.setBadania(badaniaList);
        }

        // 5. Relacja z Lekami (Many-to-Many)
        if (dto.getLekiIds() != null) {
            Set<Lek> lekiList = new HashSet<>();
            for (Integer lekId : dto.getLekiIds()) {
                Lek l = lekRepository.findById(lekId)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono leku o ID: " + lekId));
                lekiList.add(l);
            }
            wizyta.setLeki(lekiList);
        }
    }
}