package pl.edu.agh.awi.scheduled.helper.airsigmet;

import java.awt.geom.Path2D;
import java.util.List;

public class AreaHelper {

    public static boolean contains(List<Path2D> paths, double x, double y) {
        for (Path2D path : paths) {
            if (contains(path, x, y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Path2D path, double x, double y) {
        return path.contains(x, y);
    }
}
