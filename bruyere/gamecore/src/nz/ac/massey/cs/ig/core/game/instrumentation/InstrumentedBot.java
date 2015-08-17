package nz.ac.massey.cs.ig.core.game.instrumentation;

/**
 * Interface which is implemented by instrumentated bots. They have to be
 * initialized and closed but every method invocation and memory usage should be
 * observed.
 * 
 * @author Johannes Tandler
 *
 */
public interface InstrumentedBot {

	/**
	 * Initializes local {@link Observer}.
	 */
	public void __initialize(Observer observer);

	/**
	 * Returns the current observer
	 * @return
	 */
	public Observer __getObserver();

}
