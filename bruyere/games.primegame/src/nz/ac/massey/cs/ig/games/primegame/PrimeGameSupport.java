package nz.ac.massey.cs.ig.games.primegame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;

public class PrimeGameSupport extends DefaultGameSupport{

	public PrimeGameSupport() {
        super("PrimeGame");
	}

	@Override
	public Collection<Class<?>> getWhitelistedClasses() {
		ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();
		clazzes.add(PGGame.class);
		clazzes.add(PGBot.class);

		return clazzes;
	}

	@Override
	public Class<?> getTestClass() {
		return JavaPrimeGameBotTests.class;
	}

	@Override
	public GameFactory getGameFactory() {
		return new PGGameFactory();
	}


	public Serializer getSerializer() {
		return new PGSerializer();
	}


    @Override
    protected List<BotData> loadBuiltInBots() {
        String[] files = new String[] { "SmarterBot.src",
                "GreedyBuiltinBot.src", "CautiousBuiltinBot.src", "BlackMamba.src" };
        List<BotData> builtInBots = new ArrayList<>();
        for (String file : files) {
            String id = file.substring(0, file.indexOf("."));

            BotData botData = new BotData(id);
            botData.setName(id);
            botData.setLanguage("JAVA");
            botData.setCompilable(true);
            botData.setTested(true);
            botData.setqName("builtinbots." + id);

            String src = ResourceUtils
                    .loadFromClassPath(this, "resources/builtinbots/" + file);

            botData.setSrc(src);

            builtInBots.add(botData);
        }
        return builtInBots;
    }
}
