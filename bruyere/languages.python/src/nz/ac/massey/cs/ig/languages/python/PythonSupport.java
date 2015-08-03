package nz.ac.massey.cs.ig.languages.python;

import java.util.Collection;

import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.Builder;
import nz.ac.massey.cs.ig.core.services.build.BuilderFactory;
import nz.ac.massey.cs.ig.core.services.build.SourceCodeUtils;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifierFactory;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifierFactory;

import org.apache.logging.log4j.Logger;

public class PythonSupport implements ProgrammingLanguageSupport {

	private BuilderFactory builderFactory = new BuilderFactory() {
		
		@Override
		public Builder createBuilder(
				ProgrammingLanguageSupport programmingLanguageSupport, Logger logger) {
			Builder builder = new DefaultPythonBuilder(programmingLanguageSupport, logger);
			return builder;
		}
	};
	
	@Override
	public String getIdentifier() {
		return "PYTHON";
	}

	@Override
	public BuilderFactory getBuilderFactory() {
		return builderFactory;
	}


	@Override
	public String getLanguageInfo() {
		return "Python 2.7";
	}

	@Override
	public SourceCodeUtils getSourceCodeUtils() {
		return new SourceCodeUtils() {
			
			@Override
			public String extractFullClassName(String botName, String src) {
				return botName;
			}
			
			@Override
			public String extractClassName(String botName, String src) {
				return botName;
			}
		};
	}
	
	@Override
	public StaticByteCodeVerifierFactory getByteCodeVerifierFactory() {
		return null;
	}
	
	@Override
	public StaticASTVerifierFactory getStaticASTVerifierFactory() {
		return new StaticASTVerifierFactory() {
			
			@Override
			public StaticASTVerifier build(
					Collection<Class<?>> additionalWhitelistedClasses) {
				return new DefaultPythonASTVerifier(additionalWhitelistedClasses);
			}
		};
	}

}
