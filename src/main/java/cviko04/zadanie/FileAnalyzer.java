package cviko04.zadanie;

import java.io.File;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class FileAnalyzer implements Runnable {

	private final Queue<File> filesToAnalyze;
	private final Map<String, Integer> words;
	
	public FileAnalyzer(Queue<File> filesToAnalyze, Map<String, Integer> words) {
		this.filesToAnalyze = filesToAnalyze;
		this.words = words;
	}

	public void run() {
		File file = filesToAnalyze.poll();
		while(file != null) {
			try (Scanner scanner = new Scanner(file)){
				while(scanner.hasNext()) {
					String word = scanner.next();
					Integer count = words.get(word);
					if (count == null) {
						count = 1;
					}
					else {
						count++;
					}
					words.put(word, count);
				}
			} catch (Exception consumed) {}
			file = filesToAnalyze.poll();
		}
	}
}
