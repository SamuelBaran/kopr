package cviko04.zadanie;

import java.io.File;
import java.util.Queue;

public class Searcher implements Runnable {

	private File rootDir;
	private Queue<File> queue;

	public Searcher(File rootDir, Queue<File> queue) {
		this.rootDir = rootDir;
		this.queue = queue;
	}

	public void run() {
		search(rootDir.listFiles());	
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
