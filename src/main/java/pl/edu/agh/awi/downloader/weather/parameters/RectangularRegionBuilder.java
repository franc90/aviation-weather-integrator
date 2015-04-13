package pl.edu.agh.awi.downloader.weather.parameters;

public class RectangularRegionBuilder {
    private double minimalLatitude;
    private double minimalLongitude;
    private double maximalLatitude;
    private double maximalLongitude;

    public RectangularRegionBuilder setMinimalLatitude(double minimalLatitude) {
        this.minimalLatitude = minimalLatitude;
        return this;
    }

    public RectangularRegionBuilder setMinimalLongitude(double minimalLongitude) {
        this.minimalLongitude = minimalLongitude;
        return this;
    }

    public RectangularRegionBuilder setMaximalLatitude(double maximalLatitude) {
        this.maximalLatitude = maximalLatitude;
        return this;
    }

    public RectangularRegionBuilder setMaximalLongitude(double maximalLongitude) {
        this.maximalLongitude = maximalLongitude;
        return this;
    }

    public RectangularRegion build() {
        return new RectangularRegion(minimalLatitude, minimalLongitude, maximalLatitude, maximalLongitude);
    }
}