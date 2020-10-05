package cviko03.zadanie;

public class Car implements Runnable {

	private String name;
	private TrafficTracker trafficTracker;
	private static final int[][] movements = new int[][] {{1,1},{1,-1},{-1,1},{-1,-1}};
	
	public Car(String name, TrafficTracker traficTracker) {
		this.name = name;
		this.trafficTracker = traficTracker;
	}
	
	public void run() {
		while(true) {
			if (trafficTracker!= null) {
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
