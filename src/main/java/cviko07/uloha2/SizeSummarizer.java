package cviko07.uloha2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SizeSummarizer {

	public static final String START_DIR = "C:/windows";
	public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		File rootDir = new File(START_DIR);
		
		System.out.println("Pocet vlakien = "+THREAD_COUNT);
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		
		long start = System.nanoTime();
		File[] files = rootDir.listFiles();
		List<Future<DirSize>> futures =  new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				SizeTask task = new SizeTask(files[i]);
				Future<DirSize> future = executor.submit(task);
				futures.add(future);
			}
		}
		
		for(Future<DirSize> future: futures) {
			try {
				DirSize dirSize = future.get();
				System.out.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
			} catch (ExecutionException e) {
				if(e.getCause() instanceof DirectoryForbiddenException ) {
					DirectoryForbiddenException dfe = (DirectoryForbiddenException) e.getCause();
					System.err.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " +
										"Adresar " + dfe.getDir().getAbsolutePath() + " nas nepusti");
				}
			}
		}
		executor.shutdown();
	}
}
