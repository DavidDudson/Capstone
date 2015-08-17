
package nz.ac.massey.cs.ig.core.services;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Verify the instantiated bot, e.g. through running test cases. 
 * @author jens dietrich
 */
public interface DynamicVerifier<B extends Bot<?,?>> {
    
    /**
     * Verifies a bot instance, usually using dynamic analysis
     * such as unit testing.
     * @param bot
     * @param dLog the diagnostic listener, used for detailed error reporting
     * @throws VerifierException 
     */
    void verify(B bot,BuildProblemCollector dLog) throws VerifierException ;
    
}
