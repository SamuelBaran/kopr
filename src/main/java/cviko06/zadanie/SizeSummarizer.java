package cviko06.zadanie;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class SizeSummarizer {

	public static final String START_DIR = "C:/workspace/";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		File rootDir = new File(START_DIR);

		long start = System.nanoTime();
		File[] files = rootDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				DirSize dirSize = new DirSize(files[i],analyzeDir(files[i]));
				System.out.println("Čas: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
			}
		}

	}

	private static long analyzeDir(File dir) {
		long sumSize = 0;
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile())
				sumSize += files[i].length();
			if (files[i].isDirectory())
				sumSize += analyzeDir(files[i]);
		}
		return sumSize;
	}

}
