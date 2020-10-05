package cviko03.uloha2;

//NEMENNE BODY + THREADSAFE KOLEKCIA

public class Point {

	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point ip) {
		this.x = ip.getX();
		this.y = ip.getY();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
