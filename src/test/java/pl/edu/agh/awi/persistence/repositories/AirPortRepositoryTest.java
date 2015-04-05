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
import pl.edu.agh.awi.persistence.model.Taf;
import pl.edu.agh.awi.persistence.model.WeatherInfo;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
public class AirPortRepositoryTest {

    private static final String AIR_PORT_NAME = "airport1";
    private static final String TAF_INFO = "tafInfo";
    private static final String METAR_INFO = "metarInfo";

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
        AirPort airPort = new AirPort();
        airPort.setName(AIR_PORT_NAME);
        Taf taf = new Taf();
        taf.setTafInfo(TAF_INFO);
        Metar metar = new Metar();
        metar.setMetarInfo(METAR_INFO);
        WeatherInfo info = new WeatherInfo();
        info.addTaf(taf);
        info.addMetar(metar);
        airPort.addWeatherInfo(info);
        return airPort;
    }

    private void assertAirportRelations(AirPort airPort) {
        Set<WeatherInfo> weatherInfo = airPort.getWeatherInfo();
        assertFalse(weatherInfo.isEmpty());
        assertWeatherInfoRelations(weatherInfo.iterator().next());
    }

    private void assertWeatherInfoRelations(WeatherInfo weatherInfo) {
        Set<Taf> taf = weatherInfo.getTaf();
        Set<Metar> metar = weatherInfo.getMetar();
        assertFalse(taf.isEmpty());
        assertFalse(metar.isEmpty());
        assertTaf(taf.iterator().next());
        assertMetar(metar.iterator().next());
    }

    private void assertTaf(Taf taf) {
        assertEquals(TAF_INFO, taf.getTafInfo());
    }

    private void assertMetar(Metar metar) {
        assertEquals(METAR_INFO, metar.getMetarInfo());
    }
}