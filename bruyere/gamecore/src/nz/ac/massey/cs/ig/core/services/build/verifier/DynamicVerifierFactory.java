package nz.ac.massey.cs.ig.core.services.build.verifier;


public interface DynamicVerifierFactory {

	public DynamicVerifier build(Class<?> testClass);
	
}
