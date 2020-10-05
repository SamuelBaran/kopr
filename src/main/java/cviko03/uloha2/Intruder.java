package cviko03.uloha2;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Intruder implements Runnable {

	TrafficTracker trafficTracker;

	public Intruder(TrafficTracker traficTracker2) {
		this.trafficTracker = traficTracker2;
	}

	public void run() {
		while (true) {
			Map<String, Point> locations = trafficTracker.getLocations();
			Iterator<Entry<String, Point>> it = locations.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Point> point = it.next();
				if (Math.random()<0.5) {
					try {
						System.err.println("Intruder: Mazem auticko "+point.getKey());
						it.remove();
					} catch (Exception e) {
						System.err.println("Intruder: Nedovolili mi mazat auticka :(");
						return;
					}
					break;
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
