package nz.ac.massey.cs.ig.core.services.defaults;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.ServiceFactory;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;
import nz.ac.massey.cs.ig.core.services.storage.StorageException;

/**
 * This {@link ServiceFactory} creates an {@link ExtensibleServices} which is
 * initialized with all {@link ProgrammingLanguageSupport} and the first
 * {@link GameSupport} which are available in the current classpath.
 *
 * @author Johannes Tandler
 */
public class ExtensibleServiceFactory implements ServiceFactory {

	/**
	 * path of db
	 */
	private String rootPath;

	public Services build(Configuration config) {
		rootPath = config.getRootPath();

		EntityManagerFactory eMFactory = createEntityManagerFactory(config);

		ExtensibleServices extServices = new ExtensibleServices(eMFactory);

		extServices.setUIDGenerator(new DefaultUIDGenerator());
		if (config.getEventLoggerClass() != null) {
			EventLogger eventLogger = null;
			try {
				@SuppressWarnings("unchecked")
				Constructor<EventLogger> cons = (Constructor<EventLogger>) Class
						.forName(config.getEventLoggerClass()).getConstructor(
								String.class);
				eventLogger = cons.newInstance(config.getEventLoggerBaseURL());
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			extServices.setEventLogger(eventLogger);
		}

		// load programming languages support
		Collection<ProgrammingLanguageSupport> pls = buildProgrammingLanguageSupports();
		for (ProgrammingLanguageSupport sup : pls) {
			extServices.addProgrammingLanguageSupport(sup);
		}

		// load game support
		GameSupport gameSupport = buildGameSupport();
		extServices.setGameSupport(gameSupport);

		deployBuiltInBotsIfNecessary(extServices);

		Path logPath = Paths.get(rootPath, "logs");
		// create log directory if not already exists
		if (!Files.exists(logPath)) {
			try {
				Files.createDirectories(logPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// set log service
		extServices.setLogService(new DefaultLogService(logPath.toString(),
				config.getLogLevel()));

		return extServices;
	}

	/**
	 * creates and configures the {@link EntityManagerFactory} for JPA.
	 *
	 * @return
	 */
	private EntityManagerFactory createEntityManagerFactory(Configuration config) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver",
				"org.apache.derby.jdbc.EmbeddedDriver");

		// add "database" to rootpath
		properties.put("javax.persistence.jdbc.url",
				config.getJDBCConnectionURL());

		properties.put("eclipselink.ddl-generation", "create-tables");
		properties.put("eclipselink.ddl-generation.output-mode", "database");

		return Persistence.createEntityManagerFactory("persistenceUnit",
				properties);
	}

	/**
	 * builds the related {@link GameSupport}
	 *
	 * @return
	 */
	private GameSupport buildGameSupport() {
		ServiceLoader<GameSupport> loader2 = ServiceLoader
				.load(GameSupport.class);
		Iterator<GameSupport> gameSupports = loader2.iterator();
		GameSupport game = gameSupports.next();

		if (gameSupports.hasNext()) {
			throw new UnsupportedOperationException(
					"Multiple games in classpath. This is not supported!");
		}

		return game;
	}

	/**
	 * builds all {@link ProgrammingLanguageSupport}
	 *
	 * @return
	 */
	private Collection<ProgrammingLanguageSupport> buildProgrammingLanguageSupports() {
		List<ProgrammingLanguageSupport> pls = new ArrayList<ProgrammingLanguageSupport>();

		ServiceLoader<ProgrammingLanguageSupport> loader = ServiceLoader
				.load(ProgrammingLanguageSupport.class);
		Iterator<ProgrammingLanguageSupport> languages = loader.iterator();
		while (languages.hasNext()) {
			ProgrammingLanguageSupport sup = languages.next();
			pls.add(sup);
		}

		return pls;
	}

	/**
	 * Deploys all built in bots if there are no other built in bots
	 *
	 * @param services
	 */
	private void deployBuiltInBotsIfNecessary(Services services) {
		EntityTransaction t = null;
		EntityManager manager = services.createEntityManager();
		try {
			UserData user = manager.find(UserData.class,
					Configuration.BUILTIN_BOTS_PSEUDO_USER);

			t = manager.getTransaction();
			t.begin();
			if (user == null) {
				user = new UserData(Configuration.BUILTIN_BOTS_PSEUDO_USER);
				user.setName(Configuration.BUILTIN_BOTS_PSEUDO_USER);
				manager.persist(user);
			}

			deployBuiltInBots(services, user, manager);
			manager.merge(user);
			t.commit();

			manager.close();
		} catch (StorageException e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			manager.close();
		}
	}

	/**
	 * Deploys all built in bots by using the {@link Storage}
	 *
	 * @param services
	 * @throws StorageException
	 */
	private void deployBuiltInBots(Services services, UserData user,
			EntityManager manager) throws StorageException {

		GameSupport gameSupport = services.getGameSupport();

		for (BotData bot : gameSupport.getBuiltInBots()) {
			BotData dbBot = manager.find(BotData.class, bot.getId());
			if (dbBot == null) {
				bot.setOwner(user);
				user.getBots().add(bot);
				bot.setShared(true);
				manager.persist(bot);
			} else {
				dbBot.setSrc(bot.getSrc());
				manager.merge(dbBot);
			}
		}
	}
}
