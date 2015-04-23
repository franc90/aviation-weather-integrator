package pl.edu.agh.awi.persistence.repositories;

import pl.edu.agh.awi.persistence.model.AirLine;


public interface AirLineRepository extends AviationGraphRepository<AirLine> {

    @Override
    Iterable<AirLine> findByName(String name);

    @Override
    Iterable<AirLine> findByIcaoCode(String icaoCode);

    @Override
    Iterable<AirLine> findByIataCode(String iataCode);
}
