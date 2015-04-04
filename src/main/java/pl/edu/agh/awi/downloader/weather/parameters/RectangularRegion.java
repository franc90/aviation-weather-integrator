package pl.edu.agh.awi.downloader.weather.parameters;

public class RectangularRegion {

    private final int minimalLatitude;

    private final int minimalLongitude;

    private final int maximalLatitude;

    private final int maximalLongitude;

    public RectangularRegion(int minimalLatitude, int minimalLongitude, int maximalLatitude, int maximalLongitude) {
        this.minimalLatitude = minimalLatitude;
        this.minimalLongitude = minimalLongitude;
        this.maximalLatitude = maximalLatitude;
        this.maximalLongitude = maximalLongitude;
    }

    public int getMinimalLatitude() {
        return minimalLatitude;
    }

    public int getMinimalLongitude() {
        return minimalLongitude;
    }

    public int getMaximalLatitude() {
        return maximalLatitude;
    }

    public int getMaximalLongitude() {
        return maximalLongitude;
    }


}
