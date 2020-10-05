package cviko02.uloha1;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
	private AtomicInteger count = new AtomicInteger();
	
	public int getNext() {
		return count.incrementAndGet();
	}
}
