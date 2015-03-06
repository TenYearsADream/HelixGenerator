package helix.enums;

public class Directions {
	public enum Direction {
		COUNTERCLOCKWISE (false),
		CLOCKWISE (true);
		
		private boolean val;
		private Direction(boolean val) {
			this.val = val;
		}
		
		public boolean getVal() {
			return this.val;
		}
	}
}
