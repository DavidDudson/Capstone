
package nz.ac.massey.cs.ig.core.services;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Compile sources, verify the code and instantiate the compiled class.
 * @author jens dietrich
 */
public interface Builder<B extends Bot<?,?>> {

    /**
     * Build a class from source code, and instantiate it
     * @param botId
     * @param src
     * @param astverifier TODO
     * @param sverifier
     * @param dverifier
     * @param dLog the diagnostic listener, used for detailed error reporting
     * @param astverifier
     * @return
     * @throws BuilderException 
     */
    public B build(String botId, String src,StaticASTVerifier astverifier,StaticByteCodeVerifier sverifier,DynamicVerifier<B> dverifier, BuildProblemCollector dLog) throws BuilderException ;
    
}
