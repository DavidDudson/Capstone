package nz.ac.massey.cs.ig.core.services.build.verifier;

import java.util.Collection;

public interface StaticByteCodeVerifierFactory {

	public StaticByteCodeVerifier build(Collection<Class<?>> additionalWhitelistedClasses);
	
}
