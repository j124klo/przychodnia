package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Pacjent;

@Repository
public interface PacjentRepository extends CrudRepository<Pacjent, String> {}