package cviko01.uloha3;

public class Counter {
	private int count;
	
	public int getNext() {
		synchronized(this) {
			return ++count;
		}
	}
}
