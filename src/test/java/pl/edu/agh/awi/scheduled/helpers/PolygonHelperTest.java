package pl.edu.agh.awi.scheduled.helpers;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Area;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Point;
import pl.edu.agh.awi.scheduled.helper.airsigmet.PathHelper;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PolygonHelperTest {

    public static final int[] X = {1, 2, 3};

    public static final int[] Y = {1, 2, 3};
    public static final String FIELD_NAME = "point";
    public static final double DELTA = .1;

    @Test
    public void singleAreaConversion() {
        Area area = buildArea(2);

        Path2D path2D = PathHelper.ofArea(area);

        assertNotNull(path2D);
        Point2D currentPoint = path2D.getCurrentPoint();
        assertEquals(currentPoint.getX(), X[1], DELTA);
        assertEquals(currentPoint.getY(), Y[1], DELTA);
    }

    @Test
    public void listConversion() {
        final int LEN = 34;
        Area area = buildArea(LEN);

        List<Path2D> paths = PathHelper.ofAreas(Arrays.asList(area));

        assertNotNull(paths);
        assertEquals(paths.size(), 1);

        Path2D path = paths.get(0);

        Point2D currentPoint = path.getCurrentPoint();
        assertEquals(currentPoint.getX(), X[(LEN - 1) % X.length], DELTA);
        assertEquals(currentPoint.getY(), Y[(LEN - 1) % X.length], DELTA);
    }

    private Area buildArea(int points) {
        Area area = new Area();
        area.setNumPoints(BigInteger.valueOf(points));
        setPoints(area, points);

        return area;
    }

    private void setPoints(Area area, int pointsCnt) {
        List<Point> points = new ArrayList<>(pointsCnt);

        for (int i = 0; i < pointsCnt; i++) {
            Point p = new Point();
            p.setLatitude(X[i % X.length]);
            p.setLongitude(Y[i % X.length]);
            points.add(p);
        }

        try {
            Class<? extends Area> clazz = area.getClass();
            Field field = clazz.getDeclaredField(FIELD_NAME);
            field.setAccessible(true);
            field.set(area, points);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}