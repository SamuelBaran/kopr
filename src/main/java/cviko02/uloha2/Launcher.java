package cviko02.uloha2;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

	public static void main(String[] args) {
		List<Man> men = new ArrayList<Man>();
		for (int i = 1; i <= 10; i++) {
			men.add(new Man(i));
		}
			
		Woman woman = new Woman(men);
		Life life = new Life(men);
		
		Thread thread1 = new Thread(woman);
		Thread thread2 = new Thread(life);

		thread2.start();
		thread1.start();
	}

}