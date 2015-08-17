package nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation;

public interface Instrumentable {

	public void _initialize();
	
	public void _close();
	
	public Observer getObserver();
}
