package pl.edu.agh.awi.persistence.repositories;

import pl.edu.agh.awi.persistence.model.AirLine;


public interface AirLineRepository extends AviationGraphRepository<AirLine> {

    @Override
    AirLine findByName(String name);

    @Override
    AirLine findByIcaoCode(String icaoCode);

    @Override
    AirLine findByIataCode(String iataCode);
}
