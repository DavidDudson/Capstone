package nz.ac.massey.cs.ig.performance.test.python;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.JavaObserver;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.games.primegame.PGGameFactory;
import nz.ac.massey.cs.ig.performance.test.Utils;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;

import org.python.core.PySystemState;

public class RunPerformanceTests {

	private static Services service;

	public static void main(String[] args) throws Exception {
		PySystemState.initialize();

		service = Utils.initializeServices(false);

		List<BotData> botMetas = new ArrayList<BotData>();
		botMetas.add(Utils.loadPythonBot(service, "python/BestAdvantageBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/AnxiousBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/GreedyBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/PrimeNumberBot.py"));
		botMetas.add(Utils.loadPythonBot(service, "python/NoFactorsLeftBot.py"));

		Map<BotData, BotFactory[]> factories = new HashMap<>();

		BuildService buildServiceWithoutInstrumentation = new BuildService(
				service);
		buildServiceWithoutInstrumentation.setSetInstrumentation(false);

		BuildService buildServiceWithInstrumentation = new BuildService(service);
		buildServiceWithInstrumentation.setSetInstrumentation(true);

		for (BotData data : botMetas) {
			// without instrumentation
			BuildResult resOff = buildServiceWithoutInstrumentation
					.buildBot(data);
			assert resOff.isSuccess();

			// with instrumentation
			BuildResult resOn = buildServiceWithInstrumentation.buildBot(data);
			assert resOn.isSuccess();

			// print results
			factories.put(data, new BotFactory[] { resOff.getBotFactory(),
					resOn.getBotFactory() });
		}

		int numberOfGames = 500;

		PGGameFactory gameFactory = new PGGameFactory();

		int[] instrumentationLimits = new int[] { 1, 10, 100 };
		for (int b = 0; b < 2; b++) {
			System.out.format("%-24s%8s%8s%8s%8s%n", "BotName", "OFF", "ON("
					+ instrumentationLimits[0] + ")", "ON("
					+ instrumentationLimits[1] + ")", "ON("
					+ instrumentationLimits[2] + ")");
			for (BotData data : botMetas) {
				BotFactory[] botFacs = factories.get(data);

				BotFactory factory = botFacs[0];
				long timeWithoutInstr = 0;
				for (int i = 0; i < numberOfGames; i++) {
					timeWithoutInstr += playGame(factory, factory,
							String.valueOf(i), gameFactory);
				}

				factory = botFacs[1];
				long timeWithInstr[] = new long[instrumentationLimits.length];
				for (int x = 0; x < instrumentationLimits.length; x++) {
					int currentLimit = instrumentationLimits[x];
					for (int i = 0; i < numberOfGames; i++) {
						timeWithInstr[x] += playGame(factory, factory, x + "-"+i, gameFactory, currentLimit);
					}
				}

				System.out.format("%-24s%8d%8d%8d%8d%n", data.getId(),
						timeWithoutInstr, timeWithInstr[0], timeWithInstr[1],
						timeWithInstr[2]);
			}
		}

	}

	private static long playGame(BotFactory factory, BotFactory factory2,
			String id, GameFactory gameFactory) throws Exception {
		return playGame(factory, factory2, id, gameFactory, -1);
	}

	private static long playGame(BotFactory factory, BotFactory factory2,
			String id, GameFactory gameFactory, int updateLimit)
			throws Exception {
		Bot<?, ?> bot1 = factory.createBot();
		if (updateLimit != -1 && bot1 instanceof InstrumentedBot) {
			((JavaObserver) ((InstrumentedBot) bot1).__getObserver())
					.setUpdateBorder(updateLimit);
		}
		Bot<?, ?> bot2 = factory2.createBot();
		if (updateLimit != -1 && bot2 instanceof InstrumentedBot) {
			((JavaObserver) ((InstrumentedBot) bot2).__getObserver())
					.setUpdateBorder(updateLimit);
		}

		BotAgainstBotPlay game = new BotAgainstBotPlay(gameFactory.createGame(
				id, bot1, bot2));
		long start = System.currentTimeMillis();
		GameState state2 = game.call().getState();

		if (state2 == GameState.ERROR) {
			throw new UnsupportedOperationException();
		}

		long end = System.currentTimeMillis();

		return end - start;
	}
}
