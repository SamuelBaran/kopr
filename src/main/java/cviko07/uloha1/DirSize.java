package cviko07.uloha1;

import java.io.File;

public class DirSize {

	private final File directory;
	private final long size;

	public DirSize(File directory, long size) {
		this.directory = directory;
		this.size = size;
	}

	public File getDirectory() {
		return directory;
	}

	public long getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return directory.getName() + " : " + size/1000000.0 + " MB";
	}
}
