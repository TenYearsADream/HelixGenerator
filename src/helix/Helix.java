package helix;

import helix.enums.Directions.Direction;
import helix.enums.WaypointType;

import java.util.ArrayList;

/**
 *
 * @author Kanzelmeyer
 */
public class Helix {

    // User Properties
    private double originLat;           // in decimal degrees
    private double originLon;           // in decimal degrees
    private int rotations = 1;          // number of rotations for the helix (1-x, .1 precision)
    private double initialAltitude;     // Beginning altitude of the helix
    private double finalAltitude;       // Ending altitude of the helix
    private int helixRadius = 25;       // Radius of the desired helix
    private double startPosition = 0;   // Desired starting point (use compass degrees, i.e. 90 for East, 180 for South, etc)
    private Direction direction = Direction.CLOCKWISE;
    private ArrayList<Waypoint> waypointArray = new ArrayList<Waypoint>();
    private Waypoint ROI = new Waypoint();
    private boolean upAndDown = false;  // defaults to going up once only, not returning back down
    private boolean circleAtTop = false;
    private WaypointType type = WaypointType.SPLINE;

    // --------------------------------------------------------------------------------------
    /**
     * Constructor builds the Helix
     *
     * @param center Location2D with lat and lon in decimal degrees
     * @param rotations Number of rotations, one decimal point precision
     * @param initialAltitude Starting altitude
     * @param finalAltitude Ending altitude
     * @param radius Radius of the helix
     * @param startPosition The starting angle of the helix (default 0, North). Use compass angles (90 for East, 180 for South, etc)
     * @param upAndDown boolean, determines if the helix goes up and returns back down
     */
    public Helix(Location2D center, int rotations, double initialAltitude, double finalAltitude, int radius, int startPosition, boolean upAndDown) {
        this.originLat = Math.toRadians(center.getLatDD());
        this.originLon = Math.toRadians(center.getLonDD());
        this.rotations = rotations;
        this.initialAltitude = initialAltitude;
        this.finalAltitude = finalAltitude;
        this.helixRadius = radius;
        this.startPosition = Math.toRadians(startPosition);
        this.upAndDown = upAndDown;
    }

    public void waypointType(WaypointType type) {
        this.type = type;
    }

    /**
     * Simple constructor. Uses default values of 1 rotation, 25 meter radius, clockwise, and up only
     * @param center
     * @param initialAltitude
     * @param finalAltitude
     */
    public Helix(Location2D center, double initialAltitude, double finalAltitude) {
        this.originLat = Math.toRadians(center.getLatDD());
        this.originLon = Math.toRadians(center.getLonDD());
        this.initialAltitude = initialAltitude;
        this.finalAltitude = finalAltitude;
    }

    /**
     * Set the direction of rotation around the helix
     * @param direction (see Directions enum
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Makes a circle at the peak altitude of the helix
     * @param val
     */
    public void circleAtTop(boolean val) {
        this.circleAtTop = val;
    }

    /**
     * 
     * @param val
     */
    public void descend(boolean val) {
        this.upAndDown = val;
    }

    /**
     * This method generates waypoints from the object properties
     */
    public void generateHelicalWaypoints() {
        int circleStops = this.type.getCircleStops();
        int numWaypoints = circleStops * this.rotations;
        double altitude = initialAltitude;
        double steps = (finalAltitude - initialAltitude) / (rotations * circleStops);
        // Initialize the ROI waypoint
        this.ROI.setWaypoint(WaypointType.ROI.getValue(), 
                Math.toDegrees(this.originLat), 
                Math.toDegrees(this.originLon), 
                altitude);
        this.waypointArray.add(this.ROI);
        double brng, latNew, lonNew;

        for (int x = 0; x <= numWaypoints; x++) {
            brng = bearing(x, circleStops);
            latNew = latNew(brng, this.originLat, this.helixRadius);
            lonNew = lonNew(brng, this.originLat, this.originLon, latNew, this.helixRadius);
            // store the waypoint in a list
            Waypoint waypoint = new Waypoint(WaypointType.SPLINE.getValue(), 
                    Math.toDegrees(latNew), 
                    Math.toDegrees(lonNew), 
                    altitude);
            this.waypointArray.add(waypoint);
            // increment the altitude
            altitude += steps;
        } // for loop

        if(this.circleAtTop) {
            // make a circle at the top of the helix
            altitude -= steps;
            for (int x = 1; x <= circleStops; x++) {
                brng = bearing(x, circleStops);
                latNew = latNew(brng, this.originLat, this.helixRadius);
                lonNew = lonNew(brng, this.originLat, this.originLon, latNew, this.helixRadius);
                Waypoint waypoint = new Waypoint(WaypointType.SPLINE.getValue(), 
                        Math.toDegrees(latNew), 
                        Math.toDegrees(lonNew), 
                        altitude);
                this.waypointArray.add(waypoint);
            }
        }

        if (this.upAndDown) {
            // helix back down
            for (int x = 1; x <= numWaypoints; x++) {
                brng = bearing(x, circleStops);
                latNew = latNew(brng, this.originLat, this.helixRadius);
                lonNew = lonNew(brng, this.originLat, this.originLon, latNew, this.helixRadius);
                altitude -= steps;
                Waypoint waypoint = new Waypoint(WaypointType.SPLINE.getValue(), 
                        Math.toDegrees(latNew), 
                        Math.toDegrees(lonNew), 
                        altitude);
                this.waypointArray.add(waypoint);
            }
        }
    }

    /**
     * Prints Mission Commands readable in Mission Planner
     */
    public void printMissionCommands() {
        for (int index = 0; index < this.waypointArray.size(); index++) {
            System.out.print(2 + index + "\t");
            System.out.println(this.waypointArray.get(index).toMissionCommand());
        }
    }

    /**
     * Prints KML readable data
     */
    public void printKML() {
        for (int index = 0; index < this.waypointArray.size(); index++) {
            System.out.println(this.waypointArray.get(index).toKML());
        }
    }

    /**
     * Calculates a bearing around a circle
     * @param x - integer multiplier from an iterator
     * @param stops - the number of divisions per revolution
     * @return 
     */
    public double bearing(int x, int stops) {
        if (this.direction == Direction.COUNTERCLOCKWISE) {
            return (-(x * 2 * Math.PI) / stops) + this.startPosition;
        } else return ((x * 2 * Math.PI) / stops) + this.startPosition;
    }

    /**
     * Calculates a latitude from a given input latitude, bearing, and radius
     * @param brng
     * @return
     */
    public double latNew(double bearing, double originLat, int radius) {
        double R = 6378100;
        return Math.asin(Math.sin(originLat) * Math.cos(radius / R)
                + Math.cos(originLat) * Math.sin(radius / R) * Math.cos(bearing));
    }

    /**
     * Calculates a longitude from a given origin latitude, origin longitude, 
     * destination latitude, and radius
     * @param brng
     * @param originLat
     * @param originLon
     * @param latNew
     * @param radius
     * @return
     */
    public double lonNew(double bearing, double originLat, double originLon, double destinationLat, int radius) {
        double R = 6378100;
        return this.originLon + Math.atan2(Math.sin(bearing) * Math.sin(this.helixRadius / R) * Math.cos(this.originLat),
                Math.cos(this.helixRadius / R) - Math.sin(this.originLat) * Math.sin(destinationLat));
    }
}
