package pl.edu.agh.awi.scheduler.helper.airsigmet;

import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Area;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Point;

import java.awt.geom.Path2D;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PathHelper {

    public static List<Path2D> ofAreas(List<Area> area) {
        if (CollectionUtils.isEmpty(area)) {
            return Collections.emptyList();
        }

        List<Path2D> points = area
                .stream()
                .filter(e -> e.getNumPoints().intValue() > 0)
                .map(e -> ofArea(e))
                .collect(Collectors.toList());

        return points;
    }

    public static Path2D ofArea(Area area) {
        List<pl.edu.agh.awi.downloader.weather.airsigmet.generated.Point> points = area.getPoint();

        Path2D path2D = new Path2D.Double(1, points.size());
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (i == 0) {
                path2D.moveTo(point.getLatitude(), point.getLongitude());
            } else {
                path2D.lineTo(point.getLatitude(), point.getLongitude());
            }
        }

        return path2D;
    }

}
