package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AviationGraphRepository<T> extends OptionalSaveGraphRepository<T> {

    T findByName(String name);

    T findByIcaoCode(String icaoCode);

    T findByIataCode(String iataCode);
}
