package cviko06.uloha1;

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
		
		// vytvorenie executora
		System.out.println("Pocet vlakien = "+THREAD_COUNT);
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		
		long start = System.nanoTime();
		File[] files = rootDir.listFiles();
		List<Future<DirSize>> futures =  new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				SizeTask task = new SizeTask(files[i]);
				Future<DirSize> future = executor.submit(task);
//				future.get() ak bz som to dl tu tak to zaspi a nebude mozne pridat dalsi task
				futures.add(future);
				
//				DirSize dirSize = new DirSize(files[i],analyzeDir(files[i]));
//				System.out.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
			}
		}
		
		// cakanie na ukoncenie
		for(Future<DirSize> future: futures) {
			DirSize dirSize = future.get();
			System.out.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
		}
		// treba ukoncit
		executor.shutdown();
	}
}
