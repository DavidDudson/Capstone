
package nz.ac.massey.cs.ig.core.services;


/**
 * Verify compiled code using byte code analysis, e.g. to prevent illegal API access. 
 * @author jens dietrich
 */
public interface StaticByteCodeVerifier {
    /**
     * Verify byte code, usually using static analysis.
     * E.g., use ASM to check API usage.
     * @param bin
     * @param dLog the diagnostic listener, used for detailed error reporting
     * @throws VerifierException 
     */
    void verify(byte[] bin,BuildProblemCollector dLog) throws VerifierException ;
    
}
