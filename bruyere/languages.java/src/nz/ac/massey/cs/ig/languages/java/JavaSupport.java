package nz.ac.massey.cs.ig.languages.java;

import java.util.Collection;

import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.Builder;
import nz.ac.massey.cs.ig.core.services.build.BuilderFactory;
import nz.ac.massey.cs.ig.core.services.build.SourceCodeUtils;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifierFactory;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifierFactory;
import nz.ac.massey.cs.ig.languages.java.compiler.DefaultJavaBuilder;
import nz.ac.massey.cs.ig.languages.java.compiler.SourceUtils;
import nz.ac.massey.cs.ig.languages.java.verifier.DefaultByteCodeVerifier;

import org.apache.logging.log4j.Logger;

/**
 * Java language support
 * @author jens dietrich, johannes tandler
 */
public class JavaSupport implements ProgrammingLanguageSupport {

	public static final String IDENTIFIER = "JAVA";
	
	private BuilderFactory builderFactory;
	
	public JavaSupport() {
		builderFactory = new BuilderFactory() {
			
			@Override
			public Builder createBuilder(
					ProgrammingLanguageSupport programmingLanguageSupport, Logger logger) {
				DefaultJavaBuilder builder = new DefaultJavaBuilder(programmingLanguageSupport, logger);
				return builder;
			}
		};
	}
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public BuilderFactory getBuilderFactory() {
		return builderFactory;
	}

	@Override
	public StaticByteCodeVerifierFactory getByteCodeVerifierFactory() {
		return new StaticByteCodeVerifierFactory() {	
			@Override
			public StaticByteCodeVerifier build(
					Collection<Class<?>> additionalWhitelistedClasses) {
				return new DefaultByteCodeVerifier(additionalWhitelistedClasses);
			}
		};
	}

	@Override
	public String getLanguageInfo() {
		return System.getProperty("java.vm.version");
	}

	@Override
	public SourceCodeUtils getSourceCodeUtils() {
		return new SourceUtils();
	}
	
	@Override
	public StaticASTVerifierFactory getStaticASTVerifierFactory() {
		return null;
	}
}
