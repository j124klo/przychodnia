package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Choroba;

@Repository
public interface ChorobaRepository extends CrudRepository<Choroba, String> {
    // Klucz główny to String (ICD-10)
}