package pl.edu.agh.awi.downloader.weather.parameters;

public class RectangularRegionBuilder {
    private int minimalLatitude;
    private int minimalLongitude;
    private int maximalLatitude;
    private int maximalLongitude;

    public RectangularRegionBuilder setMinimalLatitude(int minimalLatitude) {
        this.minimalLatitude = minimalLatitude;
        return this;
    }

    public RectangularRegionBuilder setMinimalLongitude(int minimalLongitude) {
        this.minimalLongitude = minimalLongitude;
        return this;
    }

    public RectangularRegionBuilder setMaximalLatitude(int maximalLatitude) {
        this.maximalLatitude = maximalLatitude;
        return this;
    }

    public RectangularRegionBuilder setMaximalLongitude(int maximalLongitude) {
        this.maximalLongitude = maximalLongitude;
        return this;
    }

    public RectangularRegion createRectangularRegion() {
        return new RectangularRegion(minimalLatitude, minimalLongitude, maximalLatitude, maximalLongitude);
    }
}