package cviko06.uloha4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SizeSummarizer {

	public static final String START_DIR = "C:\\Users\\PC\\Desktop\\4. semester\\SPS";
	public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		File rootDir = new File(START_DIR);
		
		System.out.println("Pocet vlakien = "+THREAD_COUNT);
//		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		ExecutorService executor = Executors.newCachedThreadPool();
		
		long start = System.nanoTime();
		File[] files = rootDir.listFiles();
		List<Future<DirSize>> futures =  new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				SizeTask task = new SizeTask(files[i], executor);
				Future<DirSize> future = executor.submit(task);
				futures.add(future);
			}
		}
		
		for(Future<DirSize> future: futures) {
			DirSize dirSize = future.get();
			System.out.println("�as: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
		}
		executor.shutdown();
	}
}
