package helix;

import helix.enums.Cardinals.Cardinal;

/**
 * Location 2D
 * @author Kanzelmeyer
 * 
 */
public class Location2D {
    private double latDegrees;
    private double latMinutes;
    private double latSeconds;
    private Cardinal latCardinal;
    private double lonDegrees;
    private double lonMinutes;
    private double lonSeconds;
    private Cardinal lonCardinal;
    private String name;

    public Location2D(String name, double latDegrees, double latMinutes,
            double latSeconds, Cardinal latCardinal, double lonDegrees,
            double lonMinutes, double lonSeconds, Cardinal lonCardinal) {
        this.latDegrees = latDegrees;
        this.latMinutes = latMinutes;
        this.latSeconds = latSeconds;
        this.latCardinal = latCardinal;
        this.lonDegrees = lonDegrees;
        this.lonMinutes = lonMinutes;
        this.lonSeconds = lonSeconds;
        this.lonCardinal = lonCardinal;
        this.name = name;
    }

    public Location2D(String name) {
        this.name = name;
    }

    /**
     * 
     * @param degrees
     * @param minutes
     * @param seconds
     */
    public void setLatitude(double degrees, double minutes, double seconds) {
        this.latDegrees = degrees;
        this.latMinutes = minutes;
        this.latSeconds = seconds;
    }

    /**
     * 
     * @param degrees
     * @param minutes
     * @param seconds
     */
    public void setLongitude(double degrees, double minutes, double seconds) {
        this.lonDegrees = degrees;
        this.lonMinutes = minutes;
        this.lonSeconds = seconds;
    }

    /**
     * Returns the latitude of the object in decimal degrees
     * 
     * @return
     */
    public double getLatDD() {
        if (this.latCardinal == Cardinal.SOUTH) {
            return -(this.latDegrees + (this.latMinutes / 60) + (this.latSeconds / 3600));
        } else {
            return this.latDegrees + (this.latMinutes / 60)
                    + (this.latSeconds / 3600);
        }
    }

    /**
     * Returns the longitude of the object in decimal degrees
     * 
     * @return
     */
    public double getLonDD() {
        if (this.lonCardinal == Cardinal.WEST) {
            return -(this.lonDegrees + (this.lonMinutes / 60) + (this.lonSeconds / 3600));
        } else {
            return this.lonDegrees + (this.lonMinutes / 60)
                    + (this.lonSeconds / 3600);
        }
    }

    /**
     * 
     * @return name
     */
    public String name() {
        return this.name;
    }
}
