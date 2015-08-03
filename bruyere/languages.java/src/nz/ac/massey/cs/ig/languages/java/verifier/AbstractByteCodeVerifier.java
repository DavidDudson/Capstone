
package nz.ac.massey.cs.ig.languages.java.verifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.VerifierException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

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
            throw new VerifierException("Illegal API access due " + x.toString(),x);
        }
    }

    /**
     * Check access to a type. Note that type names use / , not .
     * as in byte code.
     * If a certain class should not be accessed, a security exception should be thrown.
     * @param type
     * @param thisClass
     * @param dLog
     * @param lineNo
     * @throws SecurityException 
     */
    public abstract void checkAccessToType (String type, String thisClass, BuildProblemCollector dLog,int lineNo) throws SecurityException;

	public void checkFieldAccessModifier(int access, String name) throws SecurityException {
		if((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_SYNTHETIC) == 0) {
			throw new SecurityException("Field \"" + name + "\" must not be static");
		}
	}

	public void checkMethodAccessModifier(int access, String name) {
		if(name.equals("<clinit>")) {
			return;
		}
		
		if((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_SYNTHETIC) == 0) {
			throw new SecurityException("Method \"" + name + "\" must not be static");
		}
	}

	public void checkClassNestingLevel(String name, int nestingLevel) throws SecurityException {}
}
