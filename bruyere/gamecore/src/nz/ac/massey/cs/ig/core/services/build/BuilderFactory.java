package nz.ac.massey.cs.ig.core.services.build;

import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;

import org.apache.logging.log4j.Logger;

public interface BuilderFactory {

	public Builder createBuilder(ProgrammingLanguageSupport programmingLanguageSupport, Logger logger);

}
