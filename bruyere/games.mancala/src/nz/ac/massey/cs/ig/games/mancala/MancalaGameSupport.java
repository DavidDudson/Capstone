package nz.ac.massey.cs.ig.games.mancala;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;

/**
 * Support for Mancala.
 * @see http://en.wikipedia.org/wiki/Mancala
 * @author jens dietrich
 */
public class MancalaGameSupport extends DefaultGameSupport {
	
	public MancalaGameSupport() {		
		super("Mancala");
	}
	
	@Override
	public Collection<Class<?>> getWhitelistedClasses() {
		ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();
		clazzes.add(MancalaBot.class);
		clazzes.add(Mancala.class);
		return clazzes;
	}

	@Override
	public Class<?> getTestClass() {
		return JavaMancalaBotTests.class;
	}

	@Override
	public GameFactory getGameFactory() {
		return new MancalaGameFactory();
	}

	public Serializer getSerializer() {
		return new MancalaSerializer();
	}

	@Override
	protected List<BotData> loadBuiltInBots() {
		String[] files = new String[] { "DefaultBuiltInBot.java.src","BetterBot.java.src"};
		
		List<BotData> builtInBots = new ArrayList<BotData>();
		for (String file : files) {
			String id = file.substring(0, file.indexOf("."));
			
			BotData botData = new BotData(id);
			botData.setName(id);
			botData.setLanguage("JAVA");
			botData.setCompilable(true);
			botData.setTested(true);
			botData.setqName("builtinbots." + id);
			
			String src = ResourceUtils.loadFromClassPath(this, "resources/builtinbots/" + file);
			
			botData.setSrc(src);
			
			builtInBots.add(botData);
		}
		return builtInBots;
	}
}
