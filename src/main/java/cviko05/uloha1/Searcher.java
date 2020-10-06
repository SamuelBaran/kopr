package cviko05.uloha1;

//  work stealing

import java.io.File;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Searcher implements Runnable {
	public static final File POISON_PILL = new File("poison.pill");
	
	private BlockingQueue<File> queue;
	// zdielany rad ... vsetky vlakna vidia
//	private BlockingQueue<File> unprocessedDirs;
	
//  pocet adresarov an prehladanie
//	private AtomicInteger dirCounter; 
	private static AtomicInteger dirCounter = new AtomicInteger(1); // pre vsetkz searcher instancie
	private static Semaphore dirsInDequesSemaphore = new Semaphore(1);
	// 
	private BlockingDeque<File>[] deques;
	private int myId;

	public Searcher(BlockingQueue<File> queue, BlockingDeque<File>[] deques, int myId) {
		this.queue = queue;
		this.deques = deques;
		this.myId = myId;
		
// 		hodime si tam prvy korenovy dir
//		this.unprocessedDirs = new LinkedBlockingQueue<>();
//		unprocessedDirs.offer(rootDir);
	}

	public void run() {
		try {
			while(true) {
				dirsInDequesSemaphore.acquire(); // vzdy ked chcem brat tak ma bud pusti alebo nepusti a uspi
				
				// chcem blokovane alebo neblokovane?  beriem a ked tam nic neni ta idem kradnut ... neblokovane
				File dir = deques[myId].pollFirst(); // neblokovana
				
				int where = myId;
				
				// nechcem zacat brat skor kym tam nic nemam ... vie ma tu poslat semafor
				while(dir == null) {  // idem kradnut
					// hladam neprazdny rad z kt mozem kradnut
					// for nie je spravodlive ... furt od 1 veho
					// mozme okradat susedov napr. vpravo
					where = (where + 1) % deques.length;
					dir = deques[where].pollLast(); // kradnem z konca
				}
				
				
				if(dir == POISON_PILL) {
					break;
				}
				search(dir.listFiles());
				
				// ked spracujem adresar tak znizujem
				int count = dirCounter.decrementAndGet();
				// ked uz nie je co riesit generujem pilulky
				if(count == 0) {
					for (int i = 0; i < WordCounter.ANALYZERS_COUNT; i++) {
						queue.offer(POISON_PILL);
					}
					
					for (int i = 0; i < WordCounter.SEARCHERS_COUNT; i++) {
//						unprocessedDirs.offer(POISON_PILL);
						// mozem k sebe lebo ked ostatni skoncia tak si pridu ku mne kradnut
						deques[myId].offerFirst(POISON_PILL);
						// semafor pred / po ??? pred ... je tam nic ale semafor uz je na 1 preto az po
						dirsInDequesSemaphore.release();
						
					}
				}
				// kedy konci while ... ked viem ze uz nic nepride
				// bud niekto este prehliada alebo posledny nageneruje pilulky
			}
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void search(File[] dir) {
		// rekurzivna verzia je blbost lebo vsetky vlakna budu robit to iste
		// treba pracu rozdelit ... 
		for (int i = 0;  i < dir.length; i++) {
			if (dir[i].isDirectory()) {
				dirCounter.incrementAndGet();
//				unprocessedDirs.offer(dir[i]);
				deques[myId].offerFirst(dir[i]);  // k sebe na zaciatok
				dirsInDequesSemaphore.release();
			}
			else {
				if (dir[i].getName().endsWith(".java")) {
					queue.offer(dir[i]);
				}
			}
		}
	}
}
