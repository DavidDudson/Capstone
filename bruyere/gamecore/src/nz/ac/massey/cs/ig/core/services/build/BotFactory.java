package nz.ac.massey.cs.ig.core.services.build;

import java.lang.reflect.InvocationTargetException;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Factory for creating one specific bot
 * 
 * @author Johannes Tandler
 */
public interface BotFactory {

	/**
	 * Returns id of bot which is created by this factory
	 * 
	 * @return
	 */
	String getBotId();

	/**
	 * Creates a new usable instance of a bot
	 * 
	 * @return
	 */
	Bot<?, ?> createBot() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException, NoSuchMethodException;
	

	/**
	 * Returns true if this {@link BotFactory} supports caching
	 * 
	 * @return
	 */
	public boolean isCachingSupported();
}
