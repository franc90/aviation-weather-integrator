package pl.edu.agh.awi.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;
import pl.edu.agh.awi.persistence.model.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AirPortRepositoryTest {

    private static final String AIR_PORT_NAME = "airport1";
    private static final Date TIMESTAMP = new Date();

    @Autowired
    private AirPortRepository airPortRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    @Transactional
    public void shouldSaveAirPortWithRelations() {
        AirPort airPort = createAirport();
        airPortRepository.save(airPort);
        AirPort airPortFromDB = airPortRepository.findBySchemaPropertyValue("name", AIR_PORT_NAME);
        neo4jTemplate.fetch(airPortFromDB);
        assertNotNull(airPortFromDB);
        assertEquals(AIR_PORT_NAME, airPortFromDB.getName());
        assertAirportRelations(airPortFromDB);
    }

    private AirPort createAirport() {
        Metar metar = new Metar();
        metar.setTimestamp(TIMESTAMP);

        Taf taf = new Taf();
        taf.setTimestamp(TIMESTAMP);

        AirSigment airSigment = new AirSigment();
        airSigment.setTimestamp(TIMESTAMP);

        return ModelBuilder.build(AirPort::new, a -> {
            a.setName(AIR_PORT_NAME);
            a.setCity("city");
            a.setCountry("country");
            a.setIataCode("iata");
            a.setIcaoCode("icaou");
            a.setLatitude(Double.valueOf("13.3"));
            a.setLongitude(Double.valueOf("45"));
            a.setNumberOfRunways(13);
            a.addMetar(metar);
            a.addTaf(taf);
            a.addAirSigment(airSigment);
        });
    }

    private void assertAirportRelations(AirPort airPort) {
        int expectedSize = 1;
        Set<Metar> metars = airPort.getMetars();
        Set<Taf> tafs = airPort.getTafs();
        Set<AirSigment> airSigments = airPort.getAirSigments();
        assertSize(expectedSize, metars, tafs, airSigments);
        assertTimestamp(metars, tafs);
        assertTimestamp(airSigments);
    }

    private void assertSize(int expectedSize, Set<?>... sets) {
        Stream.of(sets)
                .forEach(set -> assertTrue(set.size() == expectedSize));
    }

    @SafeVarargs
    private final void assertTimestamp(Set<? extends AbstractForecast>... sets) {
        Stream.of(sets)
                .forEach(set -> set.stream()
                        .allMatch(forecast -> TIMESTAMP.equals(forecast.getTimestamp())));
    }

     private  void assertTimestamp(Set<AirSigment> set) {
       set.stream()
               .allMatch(forecast -> TIMESTAMP.equals(forecast.getTimestamp()));
    }

}