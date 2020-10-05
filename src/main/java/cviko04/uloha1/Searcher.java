package cviko04.uloha1;

//BLOKOVANY RAD

import java.io.File;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Searcher implements Runnable {
	//poison pill na zastavenie
	public static final File POISON_PILL = new File("poison.pill");
	
	private File rootDir;
	private BlockingQueue<File> queue;

	public Searcher(File rootDir, BlockingQueue<File> queue) {
		this.rootDir = rootDir;
		this.queue = queue;
	}

	public void run() {
		search(rootDir.listFiles());
		
		// posielam ked vsetko skonci
		queue.offer(POISON_PILL);
	}
	
	private void search(File[] dir) {
		for (int i = 0;  i < dir.length; i++) {
			if (dir[i].isDirectory()) {
				search(dir[i].listFiles());
			}
			else {
				if (dir[i].getName().endsWith(".java")) {
					queue.offer(dir[i]);
				}
			}
		}
	}
}
