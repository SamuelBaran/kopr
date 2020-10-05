package cviko03.uloha3;

// THREADSAFE BODY + THREADSAFE KOLEKCIA (mapa)

import java.util.Collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TrafficTracker {

	// thread safe mapa ... mozem ju davat
	// mozem iterovat a zaroven menit
	private final ConcurrentMap<String, Point> locations;
	
	public TrafficTracker(Map<String, Point> locations) {
		// kopia
		this.locations = new ConcurrentHashMap<>(locations);
	}

	public Map<String, Point> getLocations() {
		// koli intrtuderu
		return Collections.unmodifiableMap(locations);
	}

	public Point getLocation(String id) {
		Point loc = locations.get(id);
		return loc == null ? null : new Point(loc);
	}

	public void setLocation(String id, int x, int y) {
		Point loc = (Point) locations.get(id);
		if (loc == null)
			throw new IllegalArgumentException("No such ID: " + id);
		loc.setPosition(x, y);
	}
}
