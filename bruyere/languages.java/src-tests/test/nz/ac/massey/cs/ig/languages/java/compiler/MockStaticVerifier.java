
package test.nz.ac.massey.cs.ig.languages.java.compiler;

import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.VerifierException;

/**
 * Mocks a verifier - does not do anything.
 * @author jens dietrich
 */
public class MockStaticVerifier implements StaticByteCodeVerifier {

    @Override
    public void verify(byte[] bin,BuildProblemCollector dLog) throws VerifierException {
        // do nothing
    }
}
