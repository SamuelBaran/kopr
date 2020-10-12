package cviko08.uloha1;

// ukoncovanie vynimkami 

import java.io.File;
import java.util.concurrent.Callable;

public class SizeTask implements Callable<DirSize> {

	private File dir;
	
	public SizeTask(File dir) {
		super();
		this.dir = dir;
	}

	@Override
	public DirSize call() throws Exception {
		return new DirSize(dir,analyzeDir(dir));
	}
	
	
	private static long analyzeDir(File dir) throws DirectoryForbiddenException, SizeTaskInterruptedException {
		long sumSize = 0;
		File[] files = dir.listFiles();
		
		if (files == null)
			throw new DirectoryForbiddenException(dir);
		
		if (Thread.currentThread().isInterrupted()) {
			// vypis ciastocne prehladanych podstromov
			// tento nezistim ale o uroven vyssie mozno je
			// vyhodim vynimku .. pamatam kde so a aku mam velkost 'docasnu'
			throw new SizeTaskInterruptedException(new DirSize(dir, 0));
		}
		
		try {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile())
					sumSize += files[i].length();
				if (files[i].isDirectory())
					sumSize += analyzeDir(files[i]);
			}
		} catch (SizeTaskInterruptedException e) {
			throw new SizeTaskInterruptedException(new DirSize(dir, sumSize + e.getDirSize().getSize()), e);
		}
		return sumSize;
	}


}

