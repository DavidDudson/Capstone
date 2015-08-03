package nz.ac.massey.cs.ig.core.game;

/**
 * Indicates that the move was illegal.
 * @author jens dietrich
 */
public class IllegalMoveException  extends Exception {

	private static final long serialVersionUID = 4395496966661977894L;

	public IllegalMoveException() {
		super();
    }

    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMoveException(Throwable cause) {
        super(cause);
    }

    public IllegalMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
