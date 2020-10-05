package cviko01.uloha2;

public class Task implements Runnable {

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		for(int i = 1; i <= 10; i++) {
			System.out.println(name + ": " + i);
		}
	}
}
