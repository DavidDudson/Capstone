package nz.ac.massey.cs.ig.performance.test.python;

import java.util.ArrayList;
import java.util.List;

import org.python.core.PySystemState;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.games.primegame.PGGame;
import nz.ac.massey.cs.ig.games.primegame.PGGameFactory;
import nz.ac.massey.cs.ig.performance.test.Utils;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;

public class GameThroughPutTest {

	public static void main(String[] args) throws Exception {
		
		List<BotData> botMetas = new ArrayList<BotData>();
		Services service = Utils.initializeServices(false);
		botMetas.add(Utils.loadPythonBot(service, "python/AnxiousBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/GreedyBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/PrimeNumberBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/NoFactorsLeftBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/BestAdvantageBot.py"));

		int numberOfTournaments = 5;

		BuildService buildService = new BuildService(service);
		buildService.setSetInstrumentation(false);

		PGGameFactory gameFactory = new PGGameFactory();

		System.out.println("----- WITHOUT CACHE -----");
		long start = System.currentTimeMillis();
		for (int i = 0; i < numberOfTournaments; i++) {
			for (int b1 = 0; b1 < botMetas.size(); b1++) {
				for (int b2 = 0; b2 < botMetas.size(); b2++) {
					BotData botData1 = botMetas.get(b1);
					BotData botData2 = botMetas.get(b2);

					BotFactory botFac1 = buildService.buildBot(botData1)
							.getBotFactory();
					BotFactory botFac2 = buildService.buildBot(botData2)
							.getBotFactory();
					
					Bot<?, ?> bot1 = botFac1.createBot(); 
					Bot<?, ?> bot2 = botFac2.createBot();
					
					if(bot1 == null || bot2 == null) {
						throw new UnsupportedOperationException();
					}
					
					PGGame game = gameFactory.createGame(i + "-" + b1 + "-"
							+ b2, bot1, bot2);
					BotAgainstBotPlay play = new BotAgainstBotPlay(game);
					Game<?, ?> games = play.call();

//					if(games.getState().toString().length()>100) {
//						System.out.println("Hallo");
//					}
				}
			}
		}
		System.out.println("WITHOUT CACHE : "
				+ (System.currentTimeMillis() - start));

		service = Utils.initializeServices(true);
		buildService = new BuildService(service);
		buildService.setSetInstrumentation(false);
		System.out.println("----- WITH CACHE -----");
		start = System.currentTimeMillis();
		for (int i = 0; i < numberOfTournaments; i++) {
			for (int b1 = 0; b1 < botMetas.size(); b1++) {
				for (int b2 = 0; b2 < botMetas.size(); b2++) {
					BotData botData1 = botMetas.get(b1);
					BotData botData2 = botMetas.get(b2);

					BotFactory botFac1 = buildService.buildBot(botData1)
							.getBotFactory();
					BotFactory botFac2 = buildService.buildBot(botData2)
							.getBotFactory();

					Bot<?, ?> bot1 = botFac1.createBot(); 
					Bot<?, ?> bot2 = botFac2.createBot();
					
					if(bot1 == null || bot2 == null) {
						throw new UnsupportedOperationException();
					}
					
					PGGame game = gameFactory.createGame(i + "-" + b1 + "-"
							+ b2, bot1, bot2);
					BotAgainstBotPlay play = new BotAgainstBotPlay(game);
					Game<?, ?> games = play.call();
//					if(games.getState().toString().length()>100) {
//						System.out.println("Hallo");
//					}
				}
			}
		}
		System.out.println("WITH CACHE : "
				+ (System.currentTimeMillis() - start));
	}

}
