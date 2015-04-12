package pl.edu.agh.awi.loader.file_loader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RunwaysLoaderTest {

    @Test
    public void testLoad() {
        RunwaysLoader loader = new RunwaysLoader();

        assertEquals((long) loader.getValue("EPKK"), 1);
        assertEquals((long) loader.getValue("KJFK"), 4);
        assertEquals((long) loader.getValue("KFJK"), 0);
    }

}