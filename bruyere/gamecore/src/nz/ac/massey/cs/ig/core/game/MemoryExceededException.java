package nz.ac.massey.cs.ig.core.game;

/**
 * Indicates that the user violated specific memory usage
 * @author Johannes Tandler
 */
public class MemoryExceededException  extends RuntimeException {

	private static final long serialVersionUID = 4395496966661977894L;

	public MemoryExceededException() {
		super();
    }

    public MemoryExceededException(String message) {
        super(message);
    }

    public MemoryExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoryExceededException(Throwable cause) {
        super(cause);
    }

    public MemoryExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
