package pl.edu.agh.awi.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Metar;
import pl.edu.agh.awi.persistence.model.ModelBuilder;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AirPortRepositoryTest {

    private static final String AIR_PORT_NAME = "airport1";

    @Autowired
    private AirPortRepository airPortRepository;

    @Test
    @Transactional
    public void shouldSaveAirPortWithWeatherInfo() {
        AirPort airPort = createAirport();
        airPortRepository.save(airPort);
        AirPort airPortFromDB = airPortRepository.findBySchemaPropertyValue("name", AIR_PORT_NAME);
        assertNotNull(airPortFromDB);
        assertAirportRelations(airPortFromDB);
    }

    private AirPort createAirport() {
        return ModelBuilder.build(AirPort::new, a -> {
            a.setName(AIR_PORT_NAME);
            a.setCity("city");
            a.setCountry("country");
            a.setIataCode("iata");
            a.setIcaoCode("icaou");
            a.setLatitude(Double.valueOf("13.3"));
            a.setLongitude(Double.valueOf("45"));
            a.setNumberOfRunways(13);
            a.addMetar(new Metar());
        });
    }

    private void assertAirportRelations(AirPort airPort) {
        Set<Metar> weatherInfo = airPort.getMetars();
        assertFalse(weatherInfo.isEmpty());
        assertWeatherInfoRelations(weatherInfo.iterator().next());
    }

    private void assertWeatherInfoRelations(Metar weatherInfo) {
        assertNotNull(weatherInfo);
        assertNotNull(weatherInfo.getTimeStamp());
    }


}