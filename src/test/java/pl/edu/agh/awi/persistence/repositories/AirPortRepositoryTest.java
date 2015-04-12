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
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.ModelBuilder;
import pl.edu.agh.awi.persistence.model.weather_condition.AbstractWeatherCondition;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AirPortRepositoryTest {

    private static final String AIR_PORT_NAME = "airport1";
    private static final String METAR = "METAR" ;
    private static final String TAF = "TAF" ;
    private static final Date TIMESTAMP = new Date();
    private static final Date VALID_FROM = new Date();
    private static final Date VALID_TO =  new Date();

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
        neo4jTemplate.fetch(airPortFromDB.getMetars());
        neo4jTemplate.fetch(airPortFromDB.getTafs());
        neo4jTemplate.fetch(airPortFromDB.getAirSigmets());
        assertNotNull(airPortFromDB);
        assertEquals(AIR_PORT_NAME, airPortFromDB.getName());
        assertAirportRelations(airPortFromDB);
    }

    private AirPort createAirport() {
        Metar metar = createMetar();
        Taf taf = createTaf();
        AirSigmet airSigmet = createAirSigmet();

        return ModelBuilder.build(AirPort::new, a -> {
            a.setName(AIR_PORT_NAME);
            a.setCity("city");
            a.setCountry("country");
            a.setIataCode("iata");
            a.setIcaoCode("icaou");
            a.setLatitude(Double.valueOf("13.3"));
            a.setLongitude(Double.valueOf("45"));
            a.setNumberOfRunways(13);
            a.addTaf(taf);
            a.addMetar(metar);
            a.addAirSigmet(airSigmet);
        });
    }

    private Metar createMetar() {
        Metar metar = new Metar();
        metar.setInfoType(METAR);
        return metar;
    }

    private Taf createTaf() {
        Taf taf = new Taf();
        taf.setInfoType(TAF);
        taf.setValidFrom(VALID_FROM);
        taf.setValidTo(VALID_TO);
        return taf;
    }

    private AirSigmet createAirSigmet() {
        AirSigmet airSigmet = new AirSigmet();
        airSigmet.setTimestamp(TIMESTAMP);
        return airSigmet;
    }

    private void assertAirportRelations(AirPort airPort) {
        int expectedSize = 1;
        Set<Metar> metars = airPort.getMetars();
        Set<Taf> tafs = airPort.getTafs();
        Set<AirSigmet> airSigmets = airPort.getAirSigmets();
        assertSize(expectedSize, metars, tafs, airSigmets);
        assertWeatherCondition(metars, METAR);
        assertWeatherCondition(tafs, TAF);
        assertTafs(tafs);
        assertTimestamp(airSigmets);
    }


    private void assertSize(int expectedSize, Set<?>... sets) {
        Stream.of(sets)
                .forEach(set -> assertTrue(set.size() == expectedSize));
    }

    private void assertTafs(Set<Taf> tafs) {
        assertTrue(tafs.stream()
                .allMatch(taf -> VALID_FROM.equals(taf.getValidFrom()) && VALID_TO.equals(taf.getValidTo())));
    }

    private void assertWeatherCondition(Set<? extends AbstractWeatherCondition> weatherConditions, String expectedType) {
        assertTrue(weatherConditions.stream()
                .allMatch(cond -> expectedType.equals(cond.getInfoType())));
    }

     private  void assertTimestamp(Set<AirSigmet> set) {
       assertTrue(set.stream()
               .allMatch(forecast -> TIMESTAMP.equals(forecast.getTimestamp())));
    }

}