package pl.edu.agh.awi.downloader.flights.flight.data;

import java.util.Date;

public class Flight {

    private final String flightId;

    private final String registration;

    private final double latitude;

    private final double longitude;

    private final int track;

    private final int altitude;

    private final int speed;

    private final String squawk;

    private final String radar;

    private final String aircraft;

    private final String registration2;

    private final Date timestamp;

    private final String departureIata;

    private final String arrivalIata;

    private final String flightDesignator;

    private final int value1;

    private final int verticalSpeed;

    private final String flightTracker;

    private final int value2;

    private Flight(String flightId, String registration, double latitude, double longitude, int track, int altitude, int speed, String squawk, String radar, String aircraft, String registration2, Date timestamp, String departureIata, String arrivalIata, String flightDesignator, int value1, int verticalSpeed, String flightTracker, int value2) {
        this.flightId = flightId;
        this.registration = registration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.track = track;
        this.altitude = altitude;
        this.speed = speed;
        this.squawk = squawk;
        this.radar = radar;
        this.aircraft = aircraft;
        this.registration2 = registration2;
        this.timestamp = timestamp;
        this.departureIata = departureIata;
        this.arrivalIata = arrivalIata;
        this.flightDesignator = flightDesignator;
        this.value1 = value1;
        this.verticalSpeed = verticalSpeed;
        this.flightTracker = flightTracker;
        this.value2 = value2;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getRegistration() {
        return registration;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * angle - degrees
     */
    public int getTrack() {
        return track;
    }

    /**
     * unit - feet
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * unit - knots
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * returns "0000" when no value
     */
    public String getSquawk() {
        return squawk;
    }

    public String getRadar() {
        return radar;
    }

    public String getAircraft() {
        return aircraft;
    }

    public String getRegistration2() {
        return registration2;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDepartureIata() {
        return departureIata;
    }

    public String getArrivalIata() {
        return arrivalIata;
    }

    /**
     * fltDesignator = airlineIata + fltNo
     */
    public String getFlightDesignator() {
        return flightDesignator;
    }

    public int getValue1() {
        return value1;
    }

    /**
     * unit - fpm
     */
    public int getVerticalSpeed() {
        return verticalSpeed;
    }

    /**
     * airlineICAO + fltNo or registration if does not have ICAO aircraft
     */
    public String getFlightTracker() {
        return flightTracker;
    }

    public int getValue2() {
        return value2;
    }

    public static class FlightBuilder {

        private String flightId;
        private String registration;
        private double latitude;
        private double longitude;
        private int track;
        private int altitude;
        private int speed;
        private String squawk;
        private String radar;
        private String aircraft;
        private String registration2;
        private Date timestamp;
        private String departureIata;
        private String arrivalIata;
        private String flightDesignator;
        private int verticalSpeed;
        private String flightTracker;

        public FlightBuilder setFlightId(String flightId) {
            this.flightId = flightId;
            return this;
        }

        public FlightBuilder setRegistration(String registration) {
            this.registration = registration;
            return this;
        }

        public FlightBuilder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public FlightBuilder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public FlightBuilder setTrack(int track) {
            this.track = track;
            return this;
        }

        public FlightBuilder setAltitude(int altitude) {
            this.altitude = altitude;
            return this;
        }

        public FlightBuilder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public FlightBuilder setSquawk(String squawk) {
            this.squawk = squawk;
            return this;
        }

        public FlightBuilder setRadar(String radar) {
            this.radar = radar;
            return this;
        }

        public FlightBuilder setAircraft(String aircraft) {
            this.aircraft = aircraft;
            return this;
        }

        public FlightBuilder setRegistration2(String registration2) {
            this.registration2 = registration2;
            return this;
        }

        public FlightBuilder setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FlightBuilder setDepartureIata(String departureIata) {
            this.departureIata = departureIata;
            return this;
        }

        public FlightBuilder setArrivalIata(String arrivalIata) {
            this.arrivalIata = arrivalIata;
            return this;
        }

        public FlightBuilder setFlightDesignator(String flightDesignator) {
            this.flightDesignator = flightDesignator;
            return this;
        }


        public FlightBuilder setVerticalSpeed(int verticalSpeed) {
            this.verticalSpeed = verticalSpeed;
            return this;
        }

        public FlightBuilder setFlightTracker(String flightTracker) {
            this.flightTracker = flightTracker;
            return this;
        }


        public Flight createFlight() {
            return new Flight(flightId, registration, latitude, longitude, track, altitude, speed, squawk, radar, aircraft, registration2, timestamp, departureIata, arrivalIata, flightDesignator, 0, verticalSpeed, flightTracker, 0);
        }
    }
}
