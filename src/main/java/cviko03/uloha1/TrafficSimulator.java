package cviko03.uloha1;

//OCHRANA INSTANCNEJ PREMENNEJ + BEZPECNE SPRISTUPNENIE

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class TrafficSimulator {

	public static void main(String[] args) {
		Map<String,Point> cars = new HashMap<String, Point>();
		for(int i = 0;  i < 10; i++) {
//			mapa auticok nazov -> suradnice (parne)
			cars.put("car"+i, new Point(((int)(20*Math.random())*2),((int)(20*Math.random()))*2));
		}
		
		// sleduje auticka .. odovzdame
		TrafficTracker trafficTracker = new TrafficTracker(cars);
		
		// 10 auticok sa zacne hmyrit po ploche
		for(int i = 0;  i < 10; i++) {
			// uloha auticko
			Thread thread = new Thread(new Car("car"+i, trafficTracker));
			thread.start();
		}	
		
		// mazem
		Thread intruderThread = new Thread(new Intruder(trafficTracker));
		intruderThread.start();
		
		
		while (true) {
			Map<String,Point> locations = trafficTracker.getLocations();
			try {
				for (String carName : locations.keySet()) {
					Point point = locations.get(carName); 
					int x = 0;
					int y = 0;
					
					// nekonzistentne body:
					// pocas citania nechcem menit ... aby mi niekto nesetoval
					// ten isty zamok ako v trafficTracker
					//synchronized (trafficTracker) { // nakoniec nepotrebujem lebo locations.get nam vracia kopu
						x = point.getX();
						y = point.getY();
					//}
					System.out.print(carName + ":["+x+","+y+"],");
					
					// x a y maju mat rovnaku paritu
					if (Math.abs(x) % 2 != Math.abs(y) % 2) {
						System.err.println("Nekonzistentny bod: "+carName + ":["+x+","+y+"]");
					}
				}
			} catch (ConcurrentModificationException e1) {
				System.err.println("main: niekto mi pod rukami zmazal auto");
			}
			System.out.println();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
