package nz.ac.massey.cs.ig.core.services.defaults;

import java.util.Locale;
import javax.tools.Diagnostic;

/**
 * Simple (incomplete) diagnostics implementation.
 * Only message, kind and linenumber are supported.
 * @author jens dietrich
 */
public class SimpleDiagnostics implements Diagnostic<Object>{

	private Kind kind = null;
	private String message = null;
	private long lineNumber = -1;
	
	public SimpleDiagnostics(javax.tools.Diagnostic.Kind kind, String message,
			long lineNumber) {
		super();
		this.kind = kind;
		this.message = message;
		this.lineNumber = lineNumber;
	}

	
	@Override
	public javax.tools.Diagnostic.Kind getKind() {
		return kind;
	}

	@Override
	public Object getSource() {
		return null;
	}

	@Override
	public long getPosition() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getStartPosition() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getEndPosition() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLineNumber() {
		return lineNumber;
	}

	@Override
	public long getColumnNumber() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getMessage(Locale locale) {
		return message;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + (int) (lineNumber ^ (lineNumber >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleDiagnostics other = (SimpleDiagnostics) obj;
		if (kind != other.kind)
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SimpleDiagnostics [kind=" + kind + ", message=" + message
				+ ", lineNumber=" + lineNumber + "]";
	}

}
