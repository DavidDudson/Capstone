package nz.ac.massey.cs.ig.core.services.build;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.build.BuildResult.BuildStatus;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.VerifierException;

/**
 * Interface to manage the lifecycle of bots. In particular, this includes
 * compiling or interpreting source code, verifying the code and instantiating
 * the compiled class. The purpose of the release method is to clean up caches
 * builders may use internally.
 * 
 * @author jens dietrich
 */
public interface Builder {

	/**
	 * Build a class from source code, and instantiate it
	 * 
	 * @param <B>
	 * @param botId
	 * @param src
	 * @param astverifier
	 *            TODO
	 * @param sverifier
	 * @param dverifier
	 * @param dLog
	 *            the diagnostic listener, used for detailed error reporting
	 * @param astverifier
	 * @param logger
	 *            a (user-specific) logger
	 * @return
	 * @throws BuilderException
	 */
	public BotFactory build(BotData data)
			throws BuilderException, VerifierException;

	/**
	 * Changes instrumentation state
	 * @param enabled true if instrumentation should be enabled or false otherwise
	 */
	public void setInstrumenationSupport(boolean enabled);
	
	/**
	 * True if instrumentation is supported
	 * @return
	 */
	public boolean isInstrumentationSupported();
	
	/**
	 * Sets the used byte code verifier
	 * @param verifier
	 */
	public void setByteCodeVerifier(StaticByteCodeVerifier verifier);
	
	/**
	 * sets the dynamic verifier
	 * @param verifier
	 */
	public void setDynamicVerifier(DynamicVerifier verifier);
	
	/**
	 * sets the static ast verifier
	 * @param astVerifier
	 */
	public void setAstVerifier(StaticASTVerifier astVerifier);
	
	/**
	 * Returns issues of last build process
	 * @return
	 */
	public BuildProblemCollector getBuildProblemCollector();
	
	public BuildStatus getCurrentStatus();
}
