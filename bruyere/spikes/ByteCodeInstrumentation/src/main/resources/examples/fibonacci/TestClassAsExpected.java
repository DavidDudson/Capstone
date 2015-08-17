import java.lang.management.*;

import nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Observer;
import nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Instrumentable;

public class TestClassAsExpected implements Instrumentable {
	
	ThreadLocal<nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Observer> observerLocal = new ThreadLocal<Observer>();
	
	public TestClassAsExpected() {
	}
	
	public void run() {
		observerLocal.get().invoke(0, "TestClassAsExpected", "fibonacci", "(L)L");
		System.out.print(fibonacci(45));
	}
	
	private int fibonacci(int n) {
		if(n <= 0) {
			_invokeObserver(1, "java/lang/UnspportedOperationException", "<init>", "()V");
			throw new UnsupportedOperationException();
		}
		if(n <= 2) {
			_invokeObserver(0,"TestClassAsExpected", "RETURN", null);
			return 1;
		}
		_invokeObserver(0, "TestClassAsExpected", "fibonacci", "(L)L");
		_invokeObserver(0, "TestClassAsExpected", "fibonacci", "(L)L");
		return fibonacci(n-1) + fibonacci(n-2);
	}
	
	public void _initialize() {
		if(observerLocal == null) {
			observerLocal = new ThreadLocal<Observer>();
		}
		if(observerLocal.get() == null) {
			observerLocal.set(new Observer(this));
		}
	}
	
	public void _close() {
		if(observerLocal != null) {
			observerLocal.remove();
		}
	}
	
	private void _invokeObserver(int opcode, String owner, String name, String desc) {		
		observerLocal.get().invoke(opcode, owner, name, desc);
	}
	
	public nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Observer getObserver() {
		return observerLocal.get();
	}
}
