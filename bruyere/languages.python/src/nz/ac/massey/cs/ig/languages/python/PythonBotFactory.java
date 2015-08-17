package nz.ac.massey.cs.ig.languages.python;

import java.lang.reflect.InvocationTargetException;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.instrumentation.JavaObserver;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.languages.python.bots.DefaultPythonBot;
import nz.ac.massey.cs.ig.languages.python.bots.InstrumentedPythonBot;

/**
 * Basic factory for instantiation of new bots
 * @author Johannes Tandler
 *
 */
public class PythonBotFactory implements BotFactory {

	/**
	 * Fieldname of current game in python script
	 */
	public static final String MOVE_FIELD = "__game";

	/**
	 * id of built bot
	 */
	private String botId;

	/**
	 * {@link PythonBuildArtefact} which is used to build bots
	 */
	private PythonBuildArtefact artefact;

	/**
	 * Default constructor
	 * @param botId
	 * @param buildArtefact
	 */
	public PythonBotFactory(String botId, PythonBuildArtefact buildArtefact) {
		this.botId = botId;
		this.artefact = buildArtefact;
	}

	@Override
	public String getBotId() {
		return botId;
	}

	@Override
	public Bot<?, ?> createBot() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		if(!artefact.isInstrumented()) {
			return new DefaultPythonBot(artefact, botId);
		} else {
			InstrumentedPythonBot bot = new InstrumentedPythonBot(artefact, botId);
			bot.__initialize(new JavaObserver(bot));
			return bot;
		}
	}

	@Override
	public boolean isCachingSupported() {
		return false;
	}
	
	

}
