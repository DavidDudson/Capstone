package nz.ac.massey.cs.ig.core.services;

import java.util.Collection;

import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;

/**
 * Support for Games
 * 
 * @author Johannes Tandler
 *
 */
public interface GameSupport {

	/**
	 * List of classes which are allowed to use in bots
	 * 
	 * @return
	 */
	public Collection<Class<?>> getWhitelistedClasses();

	/**
	 * The test class
	 * 
	 * @return
	 */
	public Class<?> getTestClass();

	/**
	 * Returns true if a given programming language is supported
	 * 
	 * @param languageId
	 *            identifier of a programming language
	 * @return
	 */
	public boolean isLanguageSupported(String languageId);

	/**
	 * Creates {@link GameFactory} for this game
	 * @return
	 */
	public GameFactory getGameFactory();

	/**
	 * Time in milliseconds for the calculation of one move
	 * @return
	 */
	public long getMoveTimeout();

	/**
	 * {@link TemplateFactory} for bot templates of supported programming languages
	 * @return
	 */
	public TemplateFactory getTemplateFactory();

	/**
	 * Used to serialize game instances.
	 * @return
	 */
	public Serializer getSerializer();
	
	/**
	 * Collection of built in bots
	 * @return
	 */
	public Collection<BotData> getBuiltInBots();
	
	/**
	 * Name of this game
	 * @return
	 */
	public String getName();
	
	/**
	 * Game version
	 * @return
	 */
	public int[] getVersion();
}
