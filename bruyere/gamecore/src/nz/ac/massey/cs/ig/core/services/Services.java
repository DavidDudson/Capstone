package nz.ac.massey.cs.ig.core.services;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import javax.persistence.EntityManager;

import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifierFactory;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;

/**
 * Factory used to instantiate the various service classes. If services classes
 * are stateless, they can be cached here. Implementing classes should provide
 * two constructors to facilitate instantiation via reflection: a public
 * constructor without parameters, and a public constructor with a
 * ServletContext as parameter to facilitate integration into web applications
 * (e.g., to use webapp loggers and resolve file names).
 * 
 * @author jens dietrich
 */
public interface Services {

	public static final String NAME = Services.class.getCanonicalName();
	
	public static final String ROOT_PATH = "ROOT_PATH";

	/**
	 * Returns collection of all {@link ProgrammingLanguageSupport}
	 * 
	 * @return
	 */
	Collection<ProgrammingLanguageSupport> getSupportedProgrammingLanguages();

	/**
	 * Returns the {@link ProgrammingLanguageSupport} which has the same
	 * identifier {@link ProgrammingLanguageSupport#getIdentifier()} as the
	 * parameter.
	 * 
	 * @param identifier
	 *            identifier of the wanted {@link ProgrammingLanguageSupport}
	 * @return {@link ProgrammingLanguageSupport} if found, otherwise null.
	 */
	ProgrammingLanguageSupport getProgrammingLanguageSupport(String identifier);

	/**
	 * Returns new {@link EntityManager}
	 * 
	 * @return
	 */
	EntityManager createEntityManager();

	/**
	 * Returns the support for the loaded game
	 * 
	 * @return
	 */
	GameSupport getGameSupport();

	/**
	 * {@link ExecutorService} for games
	 * 
	 * @return
	 */
	ExecutorService getExecutorServiceForGames();

	/**
	 * {@link UIDGenerator} which is used to generate new ids for games and bots
	 * 
	 * @return
	 */
	UIDGenerator getUIDGenerator();

	/**
	 * Factory for {@link DynamicVerifier}
	 * 
	 * @return
	 */
	DynamicVerifierFactory getDynamicVerifierFactory();

	/**
	 * {@link LogService} to provide user based logging
	 * 
	 * @return
	 */
	LogService getLogService();
	
	/**
	 * 
	 */
	BotCache getBotCache();

	/**
	 * Called when the Services instance is going to be destroyed
	 */
	void destroy();
	
	EventLogger getEventLogger();

}
