package nz.ac.massey.cs.ig.core.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticListener;

/**
 * Collector for compiler and verifier warnings.
 * @author jens dietrich
 */
public class BuildProblemCollector implements DiagnosticListener<Object> {
	
	private List<Diagnostic<? extends Object>> issues = new ArrayList<>(); 
	private boolean error = false;
	
	@Override
	public synchronized void report(Diagnostic<? extends Object> issue) {
		issues.add(issue);
		Kind k = issue.getKind();
		error = error || k==Kind.ERROR;
	}

	public List<Diagnostic<? extends Object>> getIssues() {
		return Collections.unmodifiableList(issues);
	}

	public boolean isError() {
		return error;
	}

}
