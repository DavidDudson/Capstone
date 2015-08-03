package nz.ac.massey.cs.ig.core.services;

/**
 * Generic verifier exception.
 * @author jens dietrich
 */

public class VerifierException extends BuilderException {

	private static final long serialVersionUID = -7528432228343920681L;

	public VerifierException() {
		super();
    }

    public VerifierException(String message) {
        super(message);
    }

    public VerifierException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifierException(Throwable cause) {
        super(cause);
    }

    public VerifierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
