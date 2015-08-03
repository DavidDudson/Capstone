
package nz.ac.massey.cs.ig.core.services.build.verifier;

import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;

/**
 * Verify the instantiated bot, e.g. through running test cases. 
 * @author jens dietrich
 */
public interface DynamicVerifier {
    
    /**
     * Verifies a bot instance, usually using dynamic analysis
     * such as unit testing.
     * @param bot
     * @param dLog the diagnostic listener, used for detailed error reporting
     * @throws VerifierException 
     */
	DynamicVerificationResult verify(BotFactory bot, BuildProblemCollector dLog);
    
    
    public enum DynamicVerificationResult {
    	OK,
    	FAILED,
    	TIMEOUT
    }
}
