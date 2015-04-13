package pl.edu.agh.awi.scheduled.helpers;

import org.junit.Test;
import pl.edu.agh.awi.scheduled.helper.airsigmet.AreaHelper;

import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AreaHelperTest {

    @Test
    public void testSinglePoint() {
        Path2D path = new Path2D.Double(1, 4);
        path.moveTo(0., 0.);
        path.lineTo(0., 2.);
        path.lineTo(2., 2.);
        path.lineTo(2., 0.);

        assertTrue(AreaHelper.contains(path, 1.999, 1.9999));
        assertFalse(AreaHelper.contains(path, 2., 2.));
        assertFalse(AreaHelper.contains(path, 2.1, 2.));
        assertTrue(AreaHelper.contains(path, .00001, .001));
        assertFalse(AreaHelper.contains(path, -.01, .01));
    }

    @Test
    public void testList() {
        Path2D path = new Path2D.Double(1, 4);
        path.moveTo(0., 0.);
        path.lineTo(0., 2.);
        path.lineTo(2., 2.);
        path.lineTo(2., 0.);

        Path2D path2 = new Path2D.Double(1, 4);
        path2.moveTo(4.5, 5.);
        path2.lineTo(4.5, 5.2);
        path2.lineTo(3.1, 5.2);
        path2.lineTo(3.1, 5.);

        List<Path2D> paths = Arrays.asList(path, path2);
        assertTrue(AreaHelper.contains(paths, 1.9, 1.8));
        assertTrue(AreaHelper.contains(paths, 4.45, 5.12));
        assertFalse(AreaHelper.contains(paths, 4.55, 5.12));
        assertFalse(AreaHelper.contains(paths, -4.55, 5.12));
    }

}