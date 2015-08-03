package nz.ac.massey.cs.ig.core.services;

import java.util.List;
import java.util.Map;

import nz.ac.massey.cs.ig.core.services.event.EventLogger;

import org.apache.logging.log4j.Level;

/**
 * Basic interface to configure the SoGaCo-Web Platform. Default implementation
 * is available at {@link PropertiesConfiguration}.
 * 
 * @author Johannes Tandler
 *
 */
public interface Configuration {

	public static final String NAME = "config";
	public static final String BUILTIN_BOTS_PSEUDO_USER = "builtinbots";

	/**
	 * Returns true if platform is started in debug mode
	 * 
	 * @return
	 */
	boolean isDebugMode();

	/**
	 * returns id of default admins
	 * 
	 * @return
	 */
	List<String> getAdminUserIds();

	/**
	 * saves the current {@link Configuration}
	 */
	void save();

	/**
	 * Returns the log level
	 * 
	 * Supported are all values of {@link Level}
	 * 
	 * @return
	 */
	String getLogLevel();

	/**
	 * Changes the default log level
	 * 
	 * WARNNING: This method is not responsible for propagating this value to
	 * any Logger.
	 * 
	 * @param newLogLevel
	 */
	void setLogLevel(String newLogLevel);

	/**
	 * JDBC URL String which is used to connect to jpa database
	 * 
	 * @return
	 */
	String getJDBCConnectionURL();

	/**
	 * Returns a map of predefined users. Key contains username and value
	 * password.
	 * 
	 * @return
	 */
	Map<String, String> getPredefinedUsers();

	/**
	 * base url for {@link EventLogger}
	 * 
	 * @return
	 */
	String getEventLoggerBaseURL();

	/**
	 * Class name of {@link EventLogger} implementation. If null no
	 * {@link EventLogger} should be used.
	 * 
	 * @return
	 */
	String getEventLoggerClass();

	String getRootPath();
}
