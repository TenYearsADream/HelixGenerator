package tests;

import helix.Helix;
import helix.enums.Directions.Direction;
import helix.enums.Locations.Location;
import helix.enums.WaypointType;

/**
 * Main method to generate the helix pattern
 * 
 * @author Kanzelmeyer
 *
 */
public class HelixTest {

    public static void main(String[] args) {
        Location roi = Location.HSV_LOWE_MILL_WATER_TOWER; // Establish the center location of the helix
        Helix helix = new Helix(roi.location(), 1, 10, 50, 15, 180, false); // Build the helix with custom params
        helix.waypointType(WaypointType.WAYPOINT); // Set the waypoint type
        helix.circleAtTop(true); // make the drone do a planar circle at the top of the helix
        helix.descend(true); // make the drone do a descending helix
        helix.setDirection(Direction.CLOCKWISE); // set the direction to clockwise or counter-clockwise
        helix.generateHelicalWaypoints(); // must call  this method to generate the waypoints
        helix.printKML();
//        helix.printMissionCommands(); // use to generate the APM Planner style text output
    }
}
