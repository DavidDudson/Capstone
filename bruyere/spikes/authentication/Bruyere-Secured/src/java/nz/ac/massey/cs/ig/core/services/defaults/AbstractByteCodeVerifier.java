
package nz.ac.massey.cs.ig.core.services.defaults;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.VerifierException;
import nz.ac.massey.cs.ig.core.services.defaults.verifier.DependencyChecker;

import org.objectweb.asm.ClassReader;

/**
 * Abstract byte code verifier based on ASM.
 * @author jens dietrich
 */
public abstract class AbstractByteCodeVerifier implements StaticByteCodeVerifier {

    @Override
    public void verify(byte[] bin,BuildProblemCollector dLog) throws VerifierException {
        DependencyChecker collector = new DependencyChecker(this,dLog);
        InputStream in = new ByteArrayInputStream(bin);
        try {
            new ClassReader(in).accept(collector, 0);
        } catch (IOException x) {
            throw new VerifierException("Exception verifying class",x);
        }
        catch (SecurityException x) {
            throw new VerifierException("Illegal API access",x);
        }
    }

    /**
     * Check access to a type. Note that type names use / , not .
     * as in byte code.
     * If a certain class should not be accessed, a security exception should be thrown.
     * @param type
     * @param dLog
     * @param lineNo
     * @throws SecurityException 
     */
    public abstract void checkAccessToType (String type,BuildProblemCollector dLog,int lineNo) throws SecurityException;
}
