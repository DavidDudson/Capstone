package nz.ac.massey.cs.ig.core.services.defaults;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nz.ac.massey.cs.ig.core.services.LogService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.google.common.io.Files;

/**
 * Default implementation of {@link LogService}.
 * 
 * Uses org.apache.logging.log4j for logging.
 * 
 * @author Johannes Tandler
 *
 */
public class DefaultLogService implements LogService {

	/**
	 * root path for logging
	 */
	private String rootPath;

	/**
	 * List of {@link Appender} which should be used
	 */
	private List<Appender> appenders;

	/**
	 * Map of created loggers (in order to close it properly at the end)
	 */
	private Map<String, Logger> loggers;

	/**
	 * {@link Level} which should be logged
	 */
	private Level level;

	/**
	 * Appender for caching
	 */
	private CustomCacheAppender cache;

	/**
	 * Configuration of root logger
	 */
	private Configuration config;

	/**
	 * current Logger context
	 */
	private LoggerContext ctx;

	/**
	 * Default constructor
	 * 
	 * @param rootPath
	 *            {@link #rootPath}
	 * @param level
	 *            {@link #level}
	 */
	public DefaultLogService(String rootPath, String level) {
		super();
		this.rootPath = rootPath;

		appenders = new ArrayList<Appender>();

		init(level);
	}

	/**
	 * Initialize {@link Appender}s
	 */
	private void init(String level) {
		// get path of main log
		// String path = Paths.get(rootPath).toString();

		loggers = new HashMap<String, Logger>();

		config = new DefaultConfiguration();
		ctx = new LoggerContext("SoGaCo");
		ctx.start(config);

		setLogLevel(level);

		// create cache
		cache = new CustomCacheAppender("cache");
		cache.start();
		// appenders.add(cache);
	}

	/**
	 * Creates a new {@link RollingFileAppender} with a specified target path.
	 * 
	 * @param path
	 * @return
	 */
	private Appender createNewRollingFileAppender(String path) {
		// create rolling file appender
		Appender fileAppender = RollingFileAppender.createAppender(
				path,
				"yyyy-MM-dd",
				"true",
				"rollingFileAppender",
				"true",
				null,
				null,
				SizeBasedTriggeringPolicy.createPolicy("1 MB"),
				DefaultRolloverStrategy.createStrategy("1", null, null, null,
						config),
				PatternLayout.newBuilder()
						.withPattern("%d{DEFAULT} [%c{1}] : %m \n").build(),
				null, "false", "false", null, config);
		appenders.add(fileAppender);
		fileAppender.start();

		return fileAppender;
	}

	@Override
	public Logger getLogger(String userId) {
		// if logger already exists, just return it
		if (loggers.containsKey(userId)) {
			return loggers.get(userId);
		}

		// create logger
		Logger logger = ctx.getLogger("/logs/user/" + userId);
		org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) logger;
		// remove all other loggers
		coreLogger.getAppenders().clear();

		// add all appenders
		coreLogger.addAppender(createNewRollingFileAppender(Paths.get(rootPath,
				userId + ".log").toString()));
		coreLogger.addAppender(cache);

		loggers.put(userId, logger);

		return logger;
	}

	@Override
	public void destroy() {
		// stop all appenders
		for (Appender app : appenders) {
			app.stop();
		}

		ctx.stop();
	}

	/**
	 * Small {@link Appender} to cach entries
	 * 
	 * @author Johannes Tandler
	 *
	 */
	private class CustomCacheAppender extends AbstractAppender {

		/**
		 * the real cache
		 */
		private List<LogEvent> cache;

		/**
		 * max size of cache
		 */
		private int cacheSize = 1000;

		/**
		 * number of elements which are removed when {@link #cacheSize} is
		 * reached by {@link #cache}
		 */
		private int removableStackSize = 100;

		/**
		 * 
		 */
		private static final long serialVersionUID = 392053779479040674L;

		protected CustomCacheAppender(String name) {
			super(name, null, null);
			cache = Collections.synchronizedList(new LinkedList<LogEvent>());
		}

		@SuppressWarnings("unused")
		protected CustomCacheAppender(String name, int cacheSize) {
			this(name);
			this.cacheSize = cacheSize;
		}

		@Override
		public void append(LogEvent event) {
			cache.add(0, event);

			// clear cache if too big
			if (cache.size() > cacheSize) {
				int newSize = cacheSize - removableStackSize;
				while (cache.size() > newSize) {
					cache.remove(cache.size() - 1);
				}
			}
		}

		public List<LogEvent> getCachedEntries() {
			return Collections.unmodifiableList(cache);
		}
	}

	@Override
	public List<LogEvent> getCachedEntries() {
		return cache.getCachedEntries();
	}

	@Override
	public String getCurrentLog(String userId) {
		try {
			File file = Paths.get(rootPath, userId + ".log").toFile();
			if (!file.exists())
				return "";

			List<String> lines = Files
					.readLines(file, Charset.forName("UTF-8"));
			StringBuffer buffer = new StringBuffer();
			for (String line : lines) {
				buffer.append(line + "\n");
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void setLogLevel(String logLevel) {
		// set right logging level
		this.level = Level.toLevel(logLevel);
		LoggerConfig loggerConfig = config
				.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(level);
		ctx.updateLoggers();
	}
}
