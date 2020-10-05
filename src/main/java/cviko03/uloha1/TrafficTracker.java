package cviko03.uloha1;

// OCHRANA INSTANCNEJ PREMENNEJ + BEZPECNE SPRISTUPNENIE

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TrafficTracker {

//	kontrola nad mapou auticok .. posuny pripadne zistovanie klokacie
	
	
	//                   chranime stav loc .. lebo intruder maze
	private final Map<String, Point> locations;
	
	public TrafficTracker(Map<String, Point> locations) {
		// niekto iny vlastni referenciu na mapu takze tu tiez kopirujem
		this.locations = copyMap(locations);
	}

	public Map<String, Point> getLocations() {
		// ked vracam ... kopirujem
		return copyMap(locations);
	}
	
	// kopia mapy, aj bodov
	private synchronized Map<String, Point> copyMap(Map<String, Point> map){
		Map<String, Point> newMap = new HashMap<>();
		for (Entry<String, Point> entry: map.entrySet()) {
			newMap.put(entry.getKey(), new Point(entry.getValue()) );
		}
		return newMap;
	}

	public Point getLocation(String id) {
		Point loc = locations.get(id);
		return loc == null ? null : new Point(loc);
	}

	public synchronized void setLocation(String id, int x, int y) {
		Point loc = (Point) locations.get(id);
		if (loc == null)
			throw new IllegalArgumentException("No such ID: " + id);
		// tu je problem ... nenastavujem "naraz" riesenie: synchronized
		loc.setX(x);
		loc.setY(y);
	}
}
