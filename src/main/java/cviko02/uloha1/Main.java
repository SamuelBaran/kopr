package cviko02.uloha1;

public class Main {

	public static void main(String[] args) {
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.setName("First");
		thread.start();
		Thread thread2 = new Thread(task);
		thread2.setName("Second");
		thread2.start();
		System.out.println("Main ends");
	}

}
