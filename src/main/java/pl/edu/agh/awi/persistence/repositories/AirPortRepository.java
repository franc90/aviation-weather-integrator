package pl.edu.agh.awi.persistence.repositories;


import pl.edu.agh.awi.persistence.model.AirPort;


public interface AirPortRepository extends AviationGraphRepository<AirPort> {

    @Override
    AirPort findByName(String name);

    @Override
    AirPort findByIcaoCode(String icaoCode);

    @Override
    AirPort findByIataCode(String iataCode);
}
