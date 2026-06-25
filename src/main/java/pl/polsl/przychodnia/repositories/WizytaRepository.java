package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Wizyta;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WizytaRepository extends CrudRepository<Wizyta, Integer> {

    // Scenariusz 1: Historia medyczna pacjenta (wyszukiwanie wizyt po PESELu pacjenta)
    List<Wizyta> findByPacjentPesel(String pesel);

    // Scenariusz 2: Grafik personelu (wyszukiwanie wizyt danego lekarza w danym dniu)
    List<Wizyta> findByPersonelPwzIdAndData(String pwzId, LocalDate data);
}