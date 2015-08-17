package nz.ac.massey.cs.ig.games.othello;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;

/**
 * Basic implementation of {@link GameSupport} for othello.
 * Nearly like PrimeGameSupport.
 * Look there for more information.
 * 
 * @author Johannes Tandler
 *
 */
public class OthelloGameSupport extends DefaultGameSupport {

	
	public OthelloGameSupport() {
        super("Othello");
    }

	@Override
	public Collection<Class<?>> getWhitelistedClasses() {
		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		clazzes.add(EasyOthelloBoard.class);
		clazzes.add(OthelloBot.class);
		clazzes.add(OthelloPosition.class);		
		return clazzes;
	}

	@Override
	public Class<?> getTestClass() {
		return null;
	}

	@Override
	public GameFactory getGameFactory() {
		return new GameFactory() {
			
			@Override
			public Game<?, ?> createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2) {
				return new OthelloGame(id, player1, player2);
			}
		};
	}

	@Override
	public Serializer getSerializer() {
		return new JSONOthelloSerializer();
	}

    @Override
    protected List<BotData> loadBuiltInBots() {
        String[] files = new String[] { "DumbBot.src",
                "SmartBot.src", "SmarterBot.src" };

        List<BotData> builtInBots = new ArrayList<>();
        for (String file : files) {
            String id = file.substring(0, file.indexOf("."));
            BotData metaData = new BotData(id);
            metaData.setName(id);
            metaData.setLanguage("JAVA");
            metaData.setCompilable(true);
            metaData.setTested(true);

            String src = ResourceUtils
                    .loadFromClassPath(this, "resources/builtinbots/" + file);
            metaData.setqName("nz.ac.massey.cs.ig.games.othello.bots." + id);

            metaData.setSrc(src);

            builtInBots.add(metaData);
        }
        return builtInBots;
    }
}
