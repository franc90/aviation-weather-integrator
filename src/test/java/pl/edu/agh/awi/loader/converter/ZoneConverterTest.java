package pl.edu.agh.awi.loader.converter;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.awi.persistence.model.Zone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class ZoneConverterTest {

    public static final String NAME = "europe";
    public static final double MINIMAL_LATITUDE = 13.;
    public static final double MAXIMAL_LATITUDE = 15.;
    public static final double MINIMAL_LONGITUDE = 10.;
    public static final double MAXIMAL_LONGITUDE = 11.;
    public static final String NAME_PL = "poland";
    public static final double MINIMAL_LATITUDE_PL = 14.;
    public static final double MAXIMAL_LATITUDE_PL = 15.;
    public static final double MINIMAL_LONGITUDE_PL = 10.;
    public static final double MAXIMAL_LONGITUDE_PL = 11.;

    public static final double DELTA = .1;

    private ZoneConverter converter;

    private pl.edu.agh.awi.downloader.flights.zone.data.Zone zone;

    @Before
    public void init() {
        converter = new ZoneConverter();

        zone = new pl.edu.agh.awi.downloader.flights.zone.data.Zone(NAME, MINIMAL_LATITUDE, MAXIMAL_LATITUDE, MINIMAL_LONGITUDE, MAXIMAL_LONGITUDE, Arrays.asList(
                new pl.edu.agh.awi.downloader.flights.zone.data.Zone(NAME_PL, MINIMAL_LATITUDE_PL, MAXIMAL_LATITUDE_PL, MINIMAL_LONGITUDE_PL, MAXIMAL_LONGITUDE_PL, Collections.emptyList())
        ));
    }

    @Test
    public void checkSingleConversion() {
        Zone zone = converter.apply(this.zone);

        checkZone(zone);
    }

    @Test
    public void checkListConversion() {
        List<Zone> zones = converter.convert(Arrays.asList(this.zone));

        assertNotNull(zones);
        assertEquals(zones.size(), 1);
        checkZone(zones.get(0));
    }

    private void checkZone(Zone zone) {
        assertNotNull(zone);

        assertEquals(zone.getName(), NAME);
        assertEquals(zone.getMaximalLatitude(), MAXIMAL_LATITUDE, DELTA);
        assertEquals(zone.getMinimalLatitude(), MINIMAL_LATITUDE, DELTA);
        assertEquals(zone.getMaximalLongitude(), MAXIMAL_LONGITUDE, DELTA);
        assertEquals(zone.getMinimalLongitude(), MINIMAL_LONGITUDE, DELTA);
        assertEquals(zone.getSubzones().size(), 1);

        Zone next = zone.getSubzones().iterator().next();
        assertEquals(next.getName(), NAME_PL);
        assertEquals(next.getMaximalLatitude(), MAXIMAL_LATITUDE_PL, DELTA);
        assertEquals(next.getMinimalLatitude(), MINIMAL_LATITUDE_PL, DELTA);
        assertEquals(next.getMaximalLongitude(), MAXIMAL_LONGITUDE_PL, DELTA);
        assertEquals(next.getMinimalLongitude(), MINIMAL_LONGITUDE_PL, DELTA);
        assertEquals(next.getSubzones().size(), 0);
    }

}