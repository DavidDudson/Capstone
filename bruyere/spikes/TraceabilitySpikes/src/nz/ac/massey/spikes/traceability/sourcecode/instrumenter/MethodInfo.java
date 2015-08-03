package nz.ac.massey.spikes.traceability.sourcecode.instrumenter;

import java.util.ArrayList;
import java.util.List;
/**
 * MethodInfo contains list of variable information
 * @author Li Sui
 *
 */
public class MethodInfo {
	
	private List<VariableInfo>variables =new ArrayList<VariableInfo>();
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
	public List<VariableInfo> getVariablesList() {
		return variables;
	}
	public void addVariable(final VariableInfo variable) {
		this.variables.add(variable);
	}
}
