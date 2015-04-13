package pl.edu.agh.awi.downloader.weather.parameters;

public class RectangularRegion {

    private final double minimalLatitude;

    private final double minimalLongitude;

    private final double maximalLatitude;

    private final double maximalLongitude;

    public RectangularRegion(double minimalLatitude, double minimalLongitude, double maximalLatitude, double maximalLongitude) {
        this.minimalLatitude = minimalLatitude;
        this.minimalLongitude = minimalLongitude;
        this.maximalLatitude = maximalLatitude;
        this.maximalLongitude = maximalLongitude;
    }

    public double getMinimalLatitude() {
        return minimalLatitude;
    }

    public double getMinimalLongitude() {
        return minimalLongitude;
    }

    public double getMaximalLatitude() {
        return maximalLatitude;
    }

    public double getMaximalLongitude() {
        return maximalLongitude;
    }


}
