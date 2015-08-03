package nz.ac.massey.cs.ig.core.game.instrumentation;

/**
 * Operation codes used in {@link Observer#invoke(int, String, String, String)}.
 * 
 * @author Johannes Tandler
 *
 */
public final class OperationCodes {

	public static final int INVOKE = 0;
	public static final int INSTANTIATION = 1;
	public static final int ASSIGN = 2;
	
	
	/**
	 * Private constructor so nobdy can instantiate it
	 */
	private OperationCodes(){};
	
}
