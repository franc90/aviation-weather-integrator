package pl.edu.agh.awi.loader.file_loader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirportCityLoaderTest {

    @Test
    public void testLoad() {
        AirportCityLoader loader = new AirportCityLoader();

        assertEquals(loader.getValue("EPKK"), "Kraków");
        assertEquals(loader.getValue("KJFK"), "New York");
        assertEquals(loader.getValue("KFJK"), "");
    }

}