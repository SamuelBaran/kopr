package cviko01.uloha1;

public class Main {

	public static void main(String[] args) {
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.start();
		for(int i = 1; i <= 10; i++) {
			System.out.println("Main: " + i);
		}

	}

}
