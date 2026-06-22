package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Personel;

@Repository
public interface PersonelRepository extends CrudRepository<Personel, String> {
    // Klucz główny to String (PWZ ID)
}