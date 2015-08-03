package nz.ac.massey.cs.ig.core.services.build.verifier.junit;

import java.util.concurrent.TimeoutException;

import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.defaults.SimpleDiagnostics;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

/**
 * A simple dynamic verifier based on a junit test runner.
 * 
 * @author jens dietrich
 * @param <B>
 *
 */
public class JUnitDynamicVerifier implements DynamicVerifier {

	private Class<?> testClass = null;
	
	private DynamicVerificationResult result;

	public JUnitDynamicVerifier(Class<?> testClass) {
		super();
		this.testClass = testClass;
		result = DynamicVerificationResult.FAILED;
	}

	@Override
	public DynamicVerificationResult verify(BotFactory botFactory, BuildProblemCollector dLog) {
		try {
			EmbeddedJUnitTestRunner runner = new EmbeddedJUnitTestRunner(
					botFactory, testClass);
			RunNotifier notifier = new RunNotifier();
			TestResultCollector collector = new TestResultCollector(runner,
					dLog);
			notifier.addListener(collector);
			runner.run(notifier);
		} catch (InitializationError err) {
			err.printStackTrace();
			SimpleDiagnostics issue = new SimpleDiagnostics(
					javax.tools.Diagnostic.Kind.ERROR,
					"Cannot initialise test runner for " + testClass.getName(),
					-1);
			dLog.report(issue);
			return result;
		}

		if (dLog.isError()) {
			return result;
		}
		return DynamicVerificationResult.OK;
	}

	class TestResultCollector extends RunListener {
		private BuildProblemCollector dLog = null;
		private EmbeddedJUnitTestRunner runner = null;

		private TestResultCollector(EmbeddedJUnitTestRunner runner,
				BuildProblemCollector dLog) {
			super();
			this.dLog = dLog;
			this.runner = runner;
		}

		@Override
		public void testFailure(Failure failure) throws Exception {
			super.testFailure(failure);
			StringBuilder s = new StringBuilder()
					.append("Tests failed for bot ")
					.append(runner.getBotUnderTest() == null ? "null" : runner
							.getBotUnderTest().getClass().getName())
					.append('\n').append("message: ")
					.append(failure.getMessage()).append('\n')
					.append("description: ").append(failure.getDescription())
					.append('\n').append("trace: ").append(failure.getTrace())
					.append('\n');

			SimpleDiagnostics issue = new SimpleDiagnostics(
					javax.tools.Diagnostic.Kind.ERROR, s.toString(), -1);
			dLog.report(issue);
			
			if(failure.getException() instanceof TimeoutException) {
				result = DynamicVerificationResult.TIMEOUT;
			}
		}

	}

}
