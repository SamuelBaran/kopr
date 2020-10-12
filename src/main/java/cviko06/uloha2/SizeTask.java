package cviko06.uloha2;

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

