package nz.ac.massey.cs.ig.core.services.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import nz.ac.massey.cs.ig.core.services.BotCache;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.LogService;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.UIDGenerator;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifierFactory;
import nz.ac.massey.cs.ig.core.services.build.verifier.junit.JUnitDynamicVerifier;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultBotCache;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultUIDGenerator;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;

/**
 * This services instance is initialized by the {@link ExtensibleServiceFactory}
 * with all {@link ProgrammingLanguageSupport} and one {@link GameSupport} which
 * are available in the current classpath.
 * 
 * @author Johannes Tandler
 *
 */
public class ExtensibleServices implements Services {

	/**
	 * map of currently loaded {@link ProgrammingLanguageSupport}
	 */
	private Map<String, ProgrammingLanguageSupport> supportedLanguages;

	/**
	 * used {@link GameSupport}
	 */
	private GameSupport support;

	/**
	 * used {@link EntityManagerFactory}
	 */
	private EntityManagerFactory entityManagerFactory;

	/**
	 * used {@link UIDGenerator}
	 */
	private UIDGenerator generator;

	private LogService logService;
	
	private BotCache botCache;

	private ExecutorService threadPool;
	private static int THREAD_COUNT = 0;
	private static synchronized int getThreadCounter () {
		return THREAD_COUNT;
	}
	
	private EventLogger eventLogger;

	public ExtensibleServices(EntityManagerFactory factory) {
		supportedLanguages = new HashMap<String, ProgrammingLanguageSupport>();
		this.entityManagerFactory = factory;
		
		// Jens: added naming to make threads easier to identify in monitoring tools
		// TODO: add mbean 
		threadPool = Executors.newCachedThreadPool(
			new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r,"sogaco.games-" + getThreadCounter());
				}
			}
		);
		
		botCache = new DefaultBotCache();
	}

	@Override
	public Collection<ProgrammingLanguageSupport> getSupportedProgrammingLanguages() {
		return supportedLanguages.values();
	}

	@Override
	public ProgrammingLanguageSupport getProgrammingLanguageSupport(String identifier) {
		return supportedLanguages.get(identifier);
	}

	@Override
	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	@Override
	public GameSupport getGameSupport() {
		return support;
	}

	@Override
	public ExecutorService getExecutorServiceForGames() {
		return threadPool;
	}

	@Override
	public UIDGenerator getUIDGenerator() {
		return generator;
	}

	public void addProgrammingLanguageSupport(ProgrammingLanguageSupport next) {
		supportedLanguages.put(next.getIdentifier(), next);
	}

	public void setGameSupport(GameSupport next) {
		this.support = next;
	}

	@Override
	public DynamicVerifierFactory getDynamicVerifierFactory() {
		return new DynamicVerifierFactory() {

			@Override
			public DynamicVerifier build(Class<?> testClass) {
				return new JUnitDynamicVerifier(testClass);
			}
		};
	}

	@Override
	public LogService getLogService() {
		return logService;
	}
	
	

	public void setUIDGenerator(DefaultUIDGenerator defaultUIDGenerator) {
		this.generator = defaultUIDGenerator;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * Destroy {@link EntityManagerFactory} and {@link LogService}
	 */
	@Override
	public void destroy() {
		entityManagerFactory.close();
		logService.destroy();
	}

	@Override
	public BotCache getBotCache() {
		return botCache;
	}

	@Override
	public EventLogger getEventLogger() {
		return eventLogger;
	}

	public void setEventLogger(EventLogger eventLogger) {
		this.eventLogger = eventLogger;
	}

}
