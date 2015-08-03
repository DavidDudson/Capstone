package nz.ac.massey.cs.ig.core.services;

import nz.ac.massey.cs.ig.core.services.build.BuilderFactory;
import nz.ac.massey.cs.ig.core.services.build.SourceCodeUtils;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifierFactory;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifierFactory;

/**
 * Basic support interface for programming languages
 * 
 * @author Johannes Tandler
 *
 */
public interface ProgrammingLanguageSupport {

	/**
	 * Identifier of a programming language
	 * 
	 * @return
	 */
	public String getIdentifier();

	/**
	 * Returns the Builder for a programming language
	 * 
	 * @return created builder
	 */
	public BuilderFactory getBuilderFactory();

	/**
	 * Returns information about this language
	 * 
	 * @return
	 */
	public String getLanguageInfo();

	/**
	 * Returns SoureCode utils for some source code tasks like class name
	 * extraction.
	 * 
	 * @return
	 */
	public SourceCodeUtils getSourceCodeUtils();
	
	/**
	 * returns Factory for {@link StaticByteCodeVerifier}
	 * @return
	 */
	public StaticByteCodeVerifierFactory getByteCodeVerifierFactory();
	
	/**
	 * returns Factory for {@link StaticASTVerifierFactory}
	 * @return
	 */
	public StaticASTVerifierFactory getStaticASTVerifierFactory();
}
