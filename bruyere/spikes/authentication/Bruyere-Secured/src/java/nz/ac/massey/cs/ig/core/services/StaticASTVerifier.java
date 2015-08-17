package nz.ac.massey.cs.ig.core.services;


/**
 * Verify compiled code using AST analysis, e.g. to prevent illegal API access. 
 * @author Isaac Udy
 */
public interface StaticASTVerifier {
	/**
     * Verify source code, usually using static analysis of an AST generated from the source code.
     * E.g., build and traverse an AST to check API access
     * @param source
     * @param dLog the diagnostic listener, used for detailed error reporting
     * @throws VerifierException 
     */
    void verify(String source, BuildProblemCollector dLog) throws VerifierException;
    
}
