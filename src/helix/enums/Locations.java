package helix.enums;

import helix.Location2D;
import helix.enums.Cardinals.Cardinal;

/**
 * Locations.java
 * @author Kanzelmeyer
 * 
 * This enum contains locations of places you want to fly helix patterns.
 */
public class Locations {
    public enum Location {
        HSV_LOWE_MILL_WATER_TOWER   (new Location2D("Huntsville Lowe Mill Water Tower",
                                    34, 42, 55.31, Cardinal.NORTH,
                                    86, 35, 46.80, Cardinal.WEST)),
        HSV_DALLAS_WATER_TOWER      (new Location2D("Huntsville Dallas/Lincoln Mill Water Tower",
                                    34, 44, 44.36, Cardinal.NORTH,
                                    86, 34, 51.99, Cardinal.WEST)),
        HSV_LOWE_MILL_SMOKESTACK    (new Location2D("Huntsville Lowe Mill Smokestack",
                                    34, 42, 53.60, Cardinal.NORTH,
                                    86, 35, 51.43, Cardinal.WEST)),
        HSV_SATURN_V                (new Location2D("Huntsville Saturn V",
                                    34, 42, 39.97, Cardinal.NORTH,
                                    86, 39, 21.29, Cardinal.WEST));

        private Location2D location;

        private Location(Location2D location) {
            this.location = location;
        }

        public double latitude() {
            return location.getLatDD();
        }

        public double longitude() {
            return location.getLonDD();
        }

        public Location2D location() {
            return this.location;
        }
    }
}
