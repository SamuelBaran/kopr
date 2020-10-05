package cviko03.zadanie;

import java.util.Map;

public class TrafficTracker {

	private final Map<String, Point> locations;
	
	public TrafficTracker(Map<String, Point> locations) {
		this.locations = locations;
	}

	public Map<String, Point> getLocations() {
		return locations;
	}

	public Point getLocation(String id) {
		Point loc = locations.get(id);
		return loc == null ? null : new Point(loc);
	}

	public  void setLocation(String id, int x, int y) {
		Point loc = (Point) locations.get(id);
		if (loc == null)
			throw new IllegalArgumentException("No such ID: " + id);
		loc.setX(x);
		loc.setY(y);
	}
}
