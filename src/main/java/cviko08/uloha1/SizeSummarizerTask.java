package cviko08.uloha1;

// ukoncenie po nejakom case

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SizeSummarizerTask implements Runnable{

	public static final String START_DIR = "C:/windows";
	public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

//	public static void main(String[] args) throws InterruptedException, ExecutionException {
	@Override
	public void run() {
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
		int i = 0;
		try {
			for(;i < futures.size(); i++) {
				Future<DirSize> future = futures.get(i);
				try {
					// toto je blokovana operacie ... jedine miesto kde sledujem ci to je prerusene
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
		} catch (InterruptedException e) {
			System.err.println("SizeSummarizer prerusil ulohu SizeSummarizerTask");
			List<Runnable> notStratedTasks = executor.shutdownNow();
			
			// dobehnu vsetky vlakna
			try {
				executor.awaitTermination(1,  TimeUnit.DAYS);
			} catch (InterruptedException ee) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// mozem doiterovat zvysne ulohy a pozistovat vynimky
			// moze sa stat ze som daco nevypisal len preto ze nieco predtym trvalo dlhsie
			for(;i < futures.size(); i++) {
				Future<DirSize> future = futures.get(i);
				try {
					if(future.isDone()) {
						// ked cakame get z ulohy kt nebola ani zacata
						DirSize dirSize = future.get();
						System.out.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " + dirSize);
					} else {
						System.out.println("uloha nezacata");
					}
				} catch (InterruptedException ignored) {
					// nehrozi ... nemoze nastat
				} catch (ExecutionException e1) {
					// 2 moznosti
					
					if(e1.getCause() instanceof DirectoryForbiddenException) {
						DirectoryForbiddenException dfe = (DirectoryForbiddenException) e.getCause();
						System.err.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " +
											"Adresar " + dfe.getDir().getAbsolutePath() + " nas nepusti");
					}
					if(e1.getCause() instanceof SizeTaskInterruptedException) {
						SizeTaskInterruptedException stie = (SizeTaskInterruptedException) e1.getCause();
						System.err.println("Èas: "+ (System.nanoTime()-start)/1000000 +" ms   " +
								"Ciastocny Adresar " + stie.getDirSize());
								
					}
				}
			}
			// nastavime na prerusene aby b
			Thread.interrupted();
			
		}
		executor.shutdown();
		
	}
}
