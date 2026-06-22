package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Badanie;

@Repository
public interface BadanieRepository extends CrudRepository<Badanie, Integer> {
    // Klucz główny to Integer (ID badania)
}