package pl.edu.agh.awi.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.ModelBuilder;
import pl.edu.agh.awi.persistence.model.weather_condition.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AirPortRepositoryTest extends AbstractAviationGraphRepositoryTest<AirPort> {

    private static final String AIR_PORT_NAME = "airport1";
    private static final String METAR = "METAR" ;
    private static final String TAF = "TAF" ;
    private static final Date TIMESTAMP = new Date();
    private static final Date VALID_FROM = new Date();
    private static final Date VALID_TO =  new Date();
    public static final Double LATITUDE = Double.valueOf("13.3");
    private static final String NAME_PROPERTY = "name";

    @Autowired
    private AirPortRepository airPortRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    @Transactional
    public void shouldSaveAirPortWithoutRelations() {
        saveAirPort();
        AirPort airPortFromDB = findAirPortByName();
        assertEmptyRelations(Metar.class, Taf.class, Flight.class);
        assertEquals(AIR_PORT_NAME, airPortFromDB.getName());
        assertTrue(Double.compare(LATITUDE, airPortFromDB.getLatitude()) == 0);
    }

    @Test
    @Transactional
    public void shouldUpdateAirPortWithWeatherRelations() {
        saveAirPort();
        addRelations();
        AirPort airPortFromDB = findAirPortByName();
        neo4jTemplate.fetch(airPortFromDB.getMetars());
        neo4jTemplate.fetch(airPortFromDB.getTafs());
        neo4jTemplate.fetch(airPortFromDB.getAirSigmets());
        assertNotNull(airPortFromDB);
        assertAirportRelations(airPortFromDB);
    }

    private void addRelations() {
        Metar metar = createMetar();
        Taf taf = createTaf();
        AirSigmet airSigmet = createAirSigmet();
        AirPort airPort = findAirPortByName();
        airPort.addMetar(metar);
        airPort.addTaf(taf);
        airPort.addAirSigmet(airSigmet);
        airPortRepository.save(airPort);
    }

    private void saveAirPort() {
        AirPort airPort  = ModelBuilder.build(AirPort::new, a -> {
            a.setName(AIR_PORT_NAME);
            a.setLatitude(LATITUDE);

        });
        airPortRepository.saveOnly(airPort);
    }


    private void assertEmptyRelations(Class<?>... relationClasses) {
        Stream.of(relationClasses)
                .forEach(cls -> assertNull(neo4jTemplate.findAll(cls).singleOrNull()));
    }

    private AirPort findAirPortByName() {
        return neo4jTemplate.findByIndexedValue(AirPort.class, NAME_PROPERTY, AIR_PORT_NAME).single();
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
        airSigmet.setType(AirSigmetType.AIRMET);
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
        assertAirSigmet(airSigmets);
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

     private  void assertAirSigmet(Set<AirSigmet> set) {
       assertTrue(set.stream()
               .allMatch(forecast -> TIMESTAMP.equals(forecast.getTimestamp()) && AirSigmetType.AIRMET == forecast.getType()));
    }

    @Override
    protected AviationDelegate<AirPort> createAviationDelegate() {
        AirPort airPort = new AirPort();
        return AviationDelegate.build()
                .withAviationItem(airPort)
                .withNameSetter(airPort::setName)
                .withIcaoCodeSetter(airPort::setIcaoCode)
                .withIataCodeSetter(airPort::setIataCode);
    }

    @Override
    protected AviationGettersComposite createGettersCompositeFor(AirPort airPort) {
        return AviationGettersComposite.build()
                .withNameGetter(airPort::getName)
                .withIcaoCodeGetter(airPort::getIcaoCode)
                .withIataCodeGetter(airPort::getIataCode);
    }
}