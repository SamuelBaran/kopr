package cviko03.uloha3;

//THREADSAFE BODY + THREADSAFE KOLEKCIA

public class Point {

	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point ip) {
		this.x = ip.getX();
		this.y = ip.getY();
	}

	// verzia 1
	public int getX() {
		return x;
	}
	
	// verzia 1
	public int getY() {
		return y;
	}
	
	// verzia 2
	public synchronized int[] getXY() {
		return new int[] {x, y};
	}
	
	// verzia 3
	public synchronized Point getCopy() {
		return new Point(x, y);
	}
	
	public synchronized void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
