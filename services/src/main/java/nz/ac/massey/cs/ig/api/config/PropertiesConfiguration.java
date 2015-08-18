package nz.ac.massey.cs.ig.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import nz.ac.massey.cs.ig.api.DefaultEventLogger;
import nz.ac.massey.cs.ig.api.config.shiro.CustomShiroWebEnviroment;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.logging.log4j.Level;

/**
 * Default Configuration of SoGaCo-Web via {@link Properties}. The default file
 * location is : ${catalina.home}/.SoGaCo/${contextPath}/.config
 * 
 * Right now you can define two different Options:
 * <ol>
 * <li>{@link PropertiesConfiguration#DEBUG_MODE_PROPERTY} defines if the
 * platform should start in debug mode. Right now every authentication is
 * disabled if you activate this mode. See {@link CustomShiroWebEnviroment} for
 * more information.<br/>
 * 
 * <li>2. {@link PropertiesConfiguration#ADMINS_PROPERTY} defines default admins
 * (their id's which are authorized to access the admin interface. If you want
 * to define multiple admins, seperate them by ",".
 * </ol>
 * 
 * A Properties file could look like this:
 * 
 * <pre>
 * isDebugMode = true;
 * defaultAdmin = user1, user2
 * logLevel = INFO
 * jdbcConnectionURL=jdbc:derby:database;create=true
 * users=user1:pass1, user2:pass2
 * </pre>
 * 
 * @author Johannes Tandler
 *
 */
public class PropertiesConfiguration implements Configuration {

	/**
	 * Name of default admins property
	 */
	public static final String ADMINS_PROPERTY = "defaultAdmins";

	/**
	 * Name of debug mode property
	 */
	public static final String DEBUG_MODE_PROPERTY = "isDebugMode";

	/**
	 * Name of log level property
	 */
	public static final String LOG_LEVEL_PROPERTY = "logLevel";

	/**
	 * Name of jdbc connection url
	 */
	public static final String JDBC_CONNECTION_URL_PROPERTY = "jdbcConnectionURL";

	/**
	 * Name of {@link EventLogger} base url
	 */
	public static final String EVENT_LOGGER_BASE_URL_PROPERTY = "eventLoggingURL";

	/**
	 * Implementation class name of {@link EventLogger}
	 */
	public static final String EVENT_LOGGER_CLASS_NAME_PROPERTY = "eventLog";

	/**
	 * Option to define predefined users
	 */
	public static final String PREDEFINED_USERS = "users";

	/**
	 * state of debug mode
	 */
	private boolean isDebugMode;

	/**
	 * list of default admins
	 */
	private List<String> defaultAdmins;

	/**
	 * predefined users
	 */
	private Map<String, String> predefinedUsers;

	/**
	 * path of configuration file
	 */
	private String path;

	/**
	 * base url of event logger
	 */
	private String baseEventLoggerURL;

	private String eventLoggerClassName;

	/**
	 * configured log level
	 */
	private String logLevel;

	/**
	 * database connection url
	 */
	private String databaseConnectionURL;

	/**
	 * Default constructor
	 * 
	 * @param rootPath
	 *            root path for configuration file
	 */
	public PropertiesConfiguration(String rootPath) {
		defaultAdmins = new ArrayList<String>();
		isDebugMode = false;
		path = rootPath;
		predefinedUsers = new HashMap<String, String>();

		init();
	}

	private String getFilePath() {
		return this.path + "/." + Configuration.NAME;
	}

	private void init() {
		Path path = Paths.get(getFilePath());

		Properties properties = new Properties();

		// check if configuration file already exists
		boolean exists = Files.exists(path);
		if (!exists) {
			createDefaultProperties(path);
		}

		// load properties file
		try {
			properties.load(Files.newInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// parse properties file
		this.isDebugMode = Boolean.parseBoolean(properties.getProperty(
				PropertiesConfiguration.DEBUG_MODE_PROPERTY,
				String.valueOf(isDebugMode)));

		// parse default admins
		String defaultAdmins = properties.getProperty(
				PropertiesConfiguration.ADMINS_PROPERTY, "");
		this.defaultAdmins.addAll(parseAdminString(defaultAdmins));

		String predefinedUsers = properties
				.getProperty(PropertiesConfiguration.PREDEFINED_USERS);
		if (predefinedUsers != null) {
			this.predefinedUsers = parsePredefinedUsers(predefinedUsers);
		}

		// parse log level
		this.logLevel = properties.getProperty(
				PropertiesConfiguration.LOG_LEVEL_PROPERTY, Level.ALL.name());

		// parse event logging base url
		this.baseEventLoggerURL = properties.getProperty(
				PropertiesConfiguration.EVENT_LOGGER_BASE_URL_PROPERTY,
				"http://localhost:8080/logging");
		this.eventLoggerClassName = properties.getProperty(
				PropertiesConfiguration.EVENT_LOGGER_CLASS_NAME_PROPERTY, DefaultEventLogger.class.getName());

		// parse database connection url
		this.databaseConnectionURL = properties
				.getProperty(PropertiesConfiguration.JDBC_CONNECTION_URL_PROPERTY);
	}

	private void createDefaultProperties(Path path) {
		try {
			// get default config
			URL defaultConfigURL = getClass().getClassLoader().getResource(
					"resources/default.config");

			// copy default config to target path
			org.apache.commons.configuration.PropertiesConfiguration config = new org.apache.commons.configuration.PropertiesConfiguration();
			PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(
					config);
			layout.load(new InputStreamReader(defaultConfigURL.openStream()));

			config.setProperty(
					PropertiesConfiguration.JDBC_CONNECTION_URL_PROPERTY,
					"jdbc:derby:" + this.path + "/database;create=true");
			layout.save(Files
					.newBufferedWriter(path, StandardOpenOption.CREATE));
		} catch (IOException | ConfigurationException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Parses the admin string of property file to collection of string.
	 * Basically they are just splitted by ","
	 * 
	 * @param string
	 *            value of admin property
	 * @return list of default admins
	 */
	private Collection<String> parseAdminString(String string) {
		Set<String> admins = new HashSet<String>();

		if (string.contains(",")) {
			for (String admin : string.split(",")) {
				String adminName = admin.trim();
				admins.add(adminName);
			}
		} else if (string.trim().length() > 0) {
			admins.add(string.trim());
		}
		return admins;
	}

	private Map<String, String> parsePredefinedUsers(String string) {
		Map<String, String> predefinedUsers = new HashMap<String, String>();

		if (string == null || string.trim().length() == 0) {
			return predefinedUsers;
		}

		String[] elements;
		if (string.contains(",")) {
			elements = string.split(",");
		} else {
			elements = new String[] { string.trim() };
		}

		for (String element : elements) {
			if (!element.contains(":")) {
				continue;
			}

			String[] subElements = element.trim().split(":");
			String name = subElements[0].trim();
			String password = subElements[1].trim();

			if (name.length() == 0 || password.length() == 0) {
				continue;
			}
			predefinedUsers.put(name, password);
		}

		return predefinedUsers;
	}

	/**
	 * Serializes the collection of admins to a string by separeted by comma.
	 * 
	 * @param admins
	 * @return
	 */
	private String serializeAdmins(Collection<String> admins) {
		StringBuffer buffer = new StringBuffer();
		for (String admin : admins) {
			if (buffer.length() > 0) {
				buffer.append(", ");
			}
			buffer.append(admin);
		}
		return buffer.toString();
	}

	@Override
	public boolean isDebugMode() {
		return isDebugMode;
	}

	@Override
	public List<String> getAdminUserIds() {
		return defaultAdmins;
	}

	/**
	 * Adds an default admin
	 * 
	 * @param adminId
	 */
	public void addAdmin(String adminId) {
		this.defaultAdmins.add(adminId);
	}

	@Override
	public void save() {
		Path path = Paths.get(getFilePath());

		org.apache.commons.configuration.PropertiesConfiguration config = new org.apache.commons.configuration.PropertiesConfiguration();
		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(
				config);
		try {
			InputStream is = Files.newInputStream(path);
			layout.load(new InputStreamReader(is));
			is.close();
		} catch (ConfigurationException | IOException e1) {
			e1.printStackTrace();
		}

		// set all properties
		config.setProperty(PropertiesConfiguration.DEBUG_MODE_PROPERTY,
				String.valueOf(isDebugMode));
		config.setProperty(PropertiesConfiguration.LOG_LEVEL_PROPERTY,
				this.getLogLevel());
		config.setProperty(
				PropertiesConfiguration.JDBC_CONNECTION_URL_PROPERTY,
				this.getJDBCConnectionURL());
		config.setProperty(PropertiesConfiguration.ADMINS_PROPERTY,
				serializeAdmins(defaultAdmins));
		config.setProperty(PropertiesConfiguration.EVENT_LOGGER_CLASS_NAME_PROPERTY, getEventLoggerClass());
		config.setProperty(PropertiesConfiguration.EVENT_LOGGER_BASE_URL_PROPERTY, this.baseEventLoggerURL);

		// save configuration file
		try {
			layout.save(Files
					.newBufferedWriter(path, StandardOpenOption.CREATE));
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getLogLevel() {
		return logLevel;
	}

	@Override
	public void setLogLevel(String newLogLevel) {
		this.logLevel = newLogLevel;
	}

	@Override
	public String getJDBCConnectionURL() {
		return databaseConnectionURL;
	}

	@Override
	public Map<String, String> getPredefinedUsers() {
		return predefinedUsers;
	}

	@Override
	public String getEventLoggerBaseURL() {
		return baseEventLoggerURL;
	}

	@Override
	public String getEventLoggerClass() {
		return eventLoggerClassName;
	}

	@Override
	public String getRootPath() {
		return path;
	}

}
