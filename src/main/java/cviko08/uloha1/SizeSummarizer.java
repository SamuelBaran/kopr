package cviko08.uloha1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SizeSummarizer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService executor = Executors.newSingleThreadExecutor();
		SizeSummarizerTask task = new SizeSummarizerTask();
		long start = System.nanoTime();
		Future<?> future = executor.submit(task);
	 	try {
	 		future.get(1, TimeUnit.SECONDS);
	 		System.out.println("Vsetko sme stihli prehladat");
	 		
	 	} catch (TimeoutException e) {
	 		// úloha ukonèená po timeoute
	 		System.err.println("Skoncili sme predcasne");
	 	} catch (ExecutionException e) {
	 		// úloha vyhodila výnimku, vyhodíme ju tiež
	 		System.err.println("Prehladavanie disku skoncilo chybou");
	 	} catch (InterruptedException e) {
	 		System.err.println("Niekto vypol cely ptogram");
			e.printStackTrace();
		} finally {
	 		// nastaví interrupt, ak úloha ešte beží
	 		future.cancel(true);
	 		executor.shutdown();
	 		// cancel neni blokovana akcia
	 		try {
				executor.awaitTermination(1, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		long duration  = System.nanoTime() - start;
	 		System.out.println("celkovy cas: "+ duration / 1000000.0 + " ms");
		}


	}

}
