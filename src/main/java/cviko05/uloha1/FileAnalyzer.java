package cviko05.uloha1;

//work stealing

import java.io.File;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class FileAnalyzer implements Runnable {

	private final BlockingQueue<File> filesToAnalyze;
	private final ConcurrentMap<String, Integer> words;
	private CountDownLatch gate;
	
	public FileAnalyzer(BlockingQueue<File> filesToAnalyze, ConcurrentMap<String, Integer> words2, CountDownLatch gate) {
		this.filesToAnalyze = filesToAnalyze;
		this.words = words2;
		this.gate = gate;
	}

	public void run() {
		try {
			File file = filesToAnalyze.take();
			while(file != Searcher.POISON_PILL) {
				try (Scanner scanner = new Scanner(file)){
					while(scanner.hasNext()) {
						String word = scanner.next();
						
						// atomicke riesenie prace s mapou
						// konkurentna mapa
						// nizsie casy ako synchroized .. merge je efektivnejsie ako to co v synchronized
						words.merge(word, 1, (originalValue, newValue) -> originalValue + newValue);
						
						
						
					}
				} catch (Exception consumed) {}
				file = filesToAnalyze.take();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// stale ked skoncim tak zmensim velkost brany o 1
			gate.countDown();
		}
	}
}
