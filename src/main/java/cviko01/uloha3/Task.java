package cviko01.uloha3;

public class Task implements Runnable {

	private Counter counter = new Counter();
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		for(int i = 1; i <= 100000; i++) {
			System.out.println(name + ": " + counter.getNext());
		}
	}
}
