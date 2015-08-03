package nz.ac.massey.cs.ig.core.game.instrumentation;

/**
 * Observer is used to observe instrumented bots.
 * 
 * @author Johannes Tandler
 *
 */
public interface Observer {

	/**
	 * Just notifies the observer about a unknown invocation. Use of this method is {@link Deprecated}
	 * because the {@link Observer} receives no information about what is going
	 * on.
	 */
	@Deprecated
	public void invoke();

	/**
	 * Notify {@link Observer} about a invocation.
	 * @param lineNumber number of current code line in observed bot
	 * @param opcode code of operation. See {@link OperationCodes} for more details
	 * @param owner ClassName of operation
	 * @param name name of operation
	 * @param desc description of operation
	 */
	public void invoke(int lineNumber, int opcode, String owner, String name, String desc);
	
	/**
	 * Sets the observed object
	 * @param x
	 */
	public void setObservable(Object observable);

}
