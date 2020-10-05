package cviko03.uloha1;

//OCHRANA INSTANCNEJ PREMENNEJ + BEZPECNE SPRISTUPNENIE

public class Car implements Runnable {

	private String name;
	private TrafficTracker trafficTracker;
	private static final int[][] movements = new int[][] {{1,1},{1,-1},{-1,1},{-1,-1}};
	
	public Car(String name, TrafficTracker traficTracker) {
		this.name = name;
		this.trafficTracker = traficTracker;
	}
	
	//	zistim kde som a nahodna sa pohnem po diagonale
	public void run() {
		while(true) {
			if (trafficTracker!= null) {
				
				// auticko je ok ... ono pracuje s traf treck len pomocou get a set 
				Point position = trafficTracker.getLocation(name);
				try {
					int movement = (int) (4*Math.random());
					int newx = position.getX()+Car.movements[movement][0];
					int newy = position.getY()+Car.movements[movement][1];
					trafficTracker.setLocation(name,newx,newy);
				} catch(Exception e) {
					System.err.println(name + ": Niekto mi zmazal auticko!");
					break;
				}
			}
		}
	}
}
