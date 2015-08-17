package nz.ac.massey.cs.ig.languages.python;

import org.python.core.PyObject;


public class PythonBuildArtefact {
	
	private PyObject src;
	
	private PyObject execScript;
	
	private boolean isInstrumented;

	public PythonBuildArtefact(PyObject src, PyObject execScript) {
		this.src = src;
		this.execScript = execScript;
	}
	
	public PyObject getSrc() {
		return src;
	}

	public void setSrc(PyObject src) {
		this.src = src;
	}

	public PyObject getExecScript() {
		return execScript;
	}

	public void setExecScript(PyObject execScript) {
		this.execScript = execScript;
	}

	public boolean isInstrumented() {
		return isInstrumented;
	}

	public void setInstrumented(boolean isInstrumented) {
		this.isInstrumented = isInstrumented;
	}

	
}
