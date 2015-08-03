package nz.ac.massey.cs.ig.core.services;

/**
 * Generic builder exception.
 * @author jens dietrich
 */
public class BuilderException extends Exception {

	private static final long serialVersionUID = -5978819475397089737L;

	public BuilderException() {
		super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }

    public BuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
