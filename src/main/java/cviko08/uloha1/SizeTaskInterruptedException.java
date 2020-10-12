package cviko08.uloha1;

public class SizeTaskInterruptedException extends RuntimeException{
	
	private DirSize dirSize;

	public SizeTaskInterruptedException(DirSize dirSize) {
		this.dirSize = dirSize;
	}
	
	public SizeTaskInterruptedException(DirSize dirSize, Throwable cause) {
		super(cause);
		this.dirSize = dirSize;
	}
	
	public DirSize getDirSize() {
		return dirSize;
	}
}
