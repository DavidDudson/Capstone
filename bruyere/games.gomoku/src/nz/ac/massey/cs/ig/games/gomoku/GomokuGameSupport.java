package nz.ac.massey.cs.ig.games.gomoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;


public class GomokuGameSupport extends DefaultGameSupport {

	public GomokuGameSupport() {
		super("Gomoku");
	}

	@Override
	public Collection<Class<?>> getWhitelistedClasses() {
		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		clazzes.add(EasyGomokuBoard.class);
		clazzes.add(GomokuBot.class);
		clazzes.add(GomokuPosition.class);		
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
				return new GomokuGame(id, player1, player2);
			}
		};
	}

	@Override
	public Serializer getSerializer() {
		return new GomokuSerializer();
	}

	@Override
	protected List<BotData> loadBuiltInBots() {
		 String[] files = new String[] { "DumbBot.src","RandomBot.src"};//TODO:need to create more bot

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
	            metaData.setqName("nz.ac.massey.cs.ig.games.gomoku.bots." + id);

	            metaData.setSrc(src);

	            builtInBots.add(metaData);
	        }
	        return builtInBots;
	}

}
