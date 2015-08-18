package nz.ac.massey.cs.ig.api;

/**
 * Exception indicating that the current user name is not known to the application.
 * @author jens dietrich
 */
public class UnknownUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownUserException() {
		super();
	}

	public UnknownUserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnknownUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownUserException(String message) {
		super(message);
	}

	public UnknownUserException(Throwable cause) {
		super(cause);
	}

}
