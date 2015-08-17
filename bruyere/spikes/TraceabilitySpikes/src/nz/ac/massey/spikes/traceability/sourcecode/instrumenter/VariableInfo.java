package nz.ac.massey.spikes.traceability.sourcecode.instrumenter;

/**
 * a variable information
 * @author Li Sui
 *
 */
public class VariableInfo {
	private String name;
	private int lineNumber;
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(final int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
