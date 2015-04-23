package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AviationGraphRepository<T> extends OptionalSaveGraphRepository<T> {

    Iterable<T> findByName(String name);

    Iterable<T> findByIcaoCode(String icaoCode);

    Iterable<T> findByIataCode(String iataCode);
}
