package nz.ac.massey.cs.ig.core.services.storage;

import nz.ac.massey.cs.ig.core.services.build.BuilderException;

/**
 * Generic bot storage exception. 
 * This exception will usually wrap another class like IOException or SQLException.
 * @author jens dietrich
 */
public class StorageException extends BuilderException {

	private static final long serialVersionUID = -1555278790229091050L;

	public StorageException() {
		super();
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
