package helix.enums;

public enum WaypointType {
    WAYPOINT(16, 8),
    ROI(201, 0),
    SPLINE(82, 4);
    private int value;
    private int circleStops;

    private WaypointType(int value, int circleStops) {
        this.value = value;
        this.circleStops = circleStops;
    }

    public int getValue() {
        return this.value;
    }
    
    public int getCircleStops() {
    	return this.circleStops;
    }
}