package pl.edu.agh.awi.downloader.flights.zone.data.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.zone.data.converter.converter.ZoneResponseConverter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ZoneResponseConverterTest {

    public static final double DELTA = .01;

    @Test
    public void zoneConversionTest() throws URISyntaxException, IOException {
        URL resource = getClass().getResource("/example/json/zones/zones_response.json");

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> parsed = mapper.readValue(new File(resource.toURI()), HashMap.class);

        ZoneResponse convert = ZoneResponseConverter.convert(parsed);

        assertEquals(convert.getVersion(), 4);
        List<Zone> zones = convert.getZones();

        assertNotNull(zones);
        assertEquals(zones.size(), 9);

        Zone zone = getZone(zones, "europe");
        assertNotNull(zone);
        assertEquals(zone.getMinimalLatitude(), 33.57, DELTA);
        assertEquals(zone.getMaximalLatitude(), 72.57, DELTA);
        assertEquals(zone.getMinimalLongitude(), -16.96, DELTA);
        assertEquals(zone.getMaximalLongitude(), 53.05, DELTA);
        assertEquals(zone.getSubzones().size(), 8);

        zone = getZone(zone.getSubzones(), "poland");
        assertNotNull(zone);
        assertEquals(zone.getMinimalLatitude(), 48.22, DELTA);
        assertEquals(zone.getMaximalLatitude(), 56.86, DELTA);
        assertEquals(zone.getMinimalLongitude(), 11.06, DELTA);
        assertEquals(zone.getMaximalLongitude(), 28.26, DELTA);
        assertEquals(zone.getSubzones().size(), 0);
    }

    private Zone getZone(List<Zone> zones, String zoneName) {
        for (Zone zone : zones) {
            if (zoneName.equals(zone.getName())) {
                return zone;
            }
        }

        return null;
    }

}