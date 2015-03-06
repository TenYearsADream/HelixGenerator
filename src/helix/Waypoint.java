package helix;

import java.text.DecimalFormat;
/**
 *
 * @author Kevin Kanzelmeyer
 */
public class Waypoint {
    
    private double latitude;
    private double longitude;
    private double altitude;
    private int type;
    private DecimalFormat latLon = new DecimalFormat("##.######");

    public Waypoint(int type, double lat, double lon, double alt) {
        this.type = type;
        this.latitude = lat;
        this.longitude = lon;
        this.altitude = alt;
    }
    
    public Waypoint() {
    	
    }
    
    public void setWaypoint(int type, double lat, double lon, double alt) {
        this.type = type;
        this.latitude = lat;
        this.longitude = lon;
        this.altitude = alt;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    /**
     * Generates text output in the console in a format readable by APM Planner
     * @return
     */
    public String toMissionCommand() {
        return("0\t3\t" + this.type + "\t0\t0\t0\t0\t" + latLon.format(this.latitude) + "\t" + latLon.format(this.longitude) + "\t" + latLon.format(this.altitude) + "\t1");
    }
    
    /**
     * Generates text output which can be copy/pasted into the coordinates element of a 
     * Google Earth kml file
     * @return
     */
    public String toKML() {
        return(latLon.format(this.longitude) + "," + latLon.format(this.latitude) + "," + latLon.format(this.altitude));
    }
    
    public enum WaypointType {
        WAYPOINT(16),
        ROI(201),
        SPLINE(82);
        private int value;

        private WaypointType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
}
}

