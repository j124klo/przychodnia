package pl.polsl.przychodnia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.przychodnia.entities.Lek;

@Repository
public interface LekRepository extends CrudRepository<Lek, Integer> {}