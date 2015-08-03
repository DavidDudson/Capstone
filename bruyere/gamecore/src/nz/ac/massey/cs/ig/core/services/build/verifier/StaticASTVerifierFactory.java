package nz.ac.massey.cs.ig.core.services.build.verifier;

import java.util.Collection;

public interface StaticASTVerifierFactory {

	public StaticASTVerifier build(Collection<Class<?>> additionalWhitelistedClasses);
	
}
