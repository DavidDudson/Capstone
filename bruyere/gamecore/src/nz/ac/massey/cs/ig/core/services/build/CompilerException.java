	package nz.ac.massey.cs.ig.core.services.build;

/**
 * Generic compiler exception.
 * @author jens dietrich
 */
public class CompilerException extends BuilderException {

	private static final long serialVersionUID = -1486097641028058223L;

	public CompilerException() {
		super();
    }

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerException(Throwable cause) {
        super(cause);
    }

    public CompilerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
