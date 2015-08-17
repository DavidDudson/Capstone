package nz.ac.massey.cs.ig.performance.test.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.massey.cs.ig.core.game.Bot;
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

public class RunPerformanceTests {

	private static Services service;

	public static void main(String[] args) throws Exception {

		service = Utils.initializeServices(false);

		List<BotData> botMetas = new ArrayList<BotData>();
		botMetas.add(Utils.loadJavaBot(service, "java/AnxiousBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/GreedyBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/PrimeNumberBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/NoFactorsLeftBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/BestAdvantageBot.java"));

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
					Bot<?, ?> bot1 = factory.createBot();
					Bot<?, ?> bot2 = factory.createBot();

					BotAgainstBotPlay game = new BotAgainstBotPlay(
							gameFactory.createGame(String.valueOf(i), bot1,
									bot2));
					long start = System.currentTimeMillis();
					game.call();
					long end = System.currentTimeMillis();

					timeWithoutInstr += end - start;
				}
				// timeWithoutInstr = timeWithoutInstr / numberOfGames;

				factory = botFacs[1];
				long timeWithInstr[] = new long[instrumentationLimits.length];
				for (int x = 0; x < instrumentationLimits.length; x++) {
					int currentLimit = instrumentationLimits[x];
					for (int i = 0; i < numberOfGames; i++) {
						Bot<?, ?> bot1 = factory.createBot();
						((JavaObserver) ((InstrumentedBot) bot1)
								.__getObserver()).setUpdateBorder(currentLimit);
						Bot<?, ?> bot2 = factory.createBot();
						((JavaObserver) ((InstrumentedBot) bot2)
								.__getObserver()).setUpdateBorder(currentLimit);

						BotAgainstBotPlay game = new BotAgainstBotPlay(
								gameFactory.createGame(String.valueOf(i), bot1,
										bot2));
						long start = System.currentTimeMillis();
						game.call();
						long end = System.currentTimeMillis();

						timeWithInstr[x] += end - start;
					}
				}

				System.out.format("%-24s%8d%8d%8d%8d%n", data.getId(),
						timeWithoutInstr, timeWithInstr[0], timeWithInstr[1],
						timeWithInstr[2]);
			}
		}

	}
}
