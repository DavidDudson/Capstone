package nz.ac.massey.cs.ig.core.services;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Factory to create loggers for users. It is also possible to use one global
 * logger.
 * 
 * @author jens dietrich & johannes tandler
 */
public interface LogService {

	Logger getLogger(String userId);

	/**
	 * destroy the {@link LogService}
	 */
	void destroy();

	/**
	 * returns all logged entries
	 * 
	 * @return
	 */
	List<LogEvent> getCachedEntries();
	
	/**
	 * Returns the actual log for a specific user
	 * @param userId
	 * @return
	 */
	String getCurrentLog(String userId);
	
	/**
	 * Sets the current log level.
	 * See {@link Level} for available options.
	 * 
	 * @param logLevel name of {@link Level} level.
	 */
	void setLogLevel(String logLevel);

}
