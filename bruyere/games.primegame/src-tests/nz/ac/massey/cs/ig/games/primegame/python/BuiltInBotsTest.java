package nz.ac.massey.cs.ig.games.primegame.python;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.core.services.defaults.ExtensibleServiceFactory;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;
import nz.ac.massey.cs.ig.games.primegame.PGGame;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BuiltInBotsTest {

	private Services service;

	private Configuration config;

	private BotData data;

	private Bot<?, ?> preBuiltBot;

	@Before
	public void setup() {
		config = Mockito.mock(Configuration.class);
		Mockito.when(config.getRootPath()).thenReturn("./");
		Mockito.when(config.getJDBCConnectionURL()).thenReturn(
				"jdbc:derby:target/database;create=true");

		data = createNewBot("TestBot");

		preBuiltBot = new PrebuiltBlackMamba(".");
		service = new ExtensibleServiceFactory().build(config);
	}

	private BotData createNewBot(String id) {
		UserData user = new UserData("_");
		BotData data = new BotData(id);
		data.setOwner(user);
		data.setLanguage("PYTHON");
		return data;
	}

	@Test
	public void testCautiousBot() throws Exception {
		data.setSrc(ResourceUtils.loadFromClassPath(this,
				"test-resources/CautiousBot.py"));
		Game<?, ?> game = playGame(data);

		assertTrue("Game should be finished", game.getState().isFinished());
		assertNull("There should be no error", game.getError());
	}

	@Test
	public void testGreedyBotWithReversedList() throws Exception {
		data.setSrc(ResourceUtils.loadFromClassPath(this,
				"test-resources/GreedyBot.py"));
		Game<?, ?> game = playGame(data);

		assertTrue("Game should be finished", game.getState().isFinished());
		assertNull("There should be no error", game.getError());
	}

	@Test
	public void testStateSeparation() throws Exception {
		BotData botdata1 = data;
		BotData botdata2 = createNewBot("Bot2");

		botdata1.setSrc(ResourceUtils.loadFromClassPath(this,
				"test-resources/overlappingBots/Bot1.py"));
		botdata2.setSrc(ResourceUtils.loadFromClassPath(this,
				"test-resources/overlappingBots/Bot2.py"));

		BuildService buildService = new BuildService(service);
		buildService.setDynamicVerificationEnabled(false);
		buildService.setSetInstrumentation(false);

		BuildResult result1 = buildService.buildBot(botdata1);
		BuildResult result2 = buildService.buildBot(botdata2);
		assertTrue("Build should succeed", result1.isSuccess());
		assertTrue("Build should succeed", result2.isSuccess());

		Bot<?, ?> bot1 = result1.getBotFactory().createBot();
		Bot<?, ?> bot2 = result2.getBotFactory().createBot();

		Game<?, ?> game = playGame(bot1, bot2);
		if (game.getError() != null) {
			fail("Bots overlapped each other");
		}
	}

	@Test
	public void testMultipleBotsAtSameTime() throws Exception {
		data.setSrc(ResourceUtils.loadFromClassPath(this,
				"test-resources/GreedyBot.py"));
		ExecutorService execService = Executors.newCachedThreadPool();

		BuildService buildService = new BuildService(service);
		buildService.setDynamicVerificationEnabled(false);
		buildService.setSetInstrumentation(false);

		BuildResult result1 = buildService.buildBot(data);
		BotFactory fac = result1.getBotFactory();
		BotFactory fac2 = new BotFactory() {

			@Override
			public boolean isCachingSupported() {
				return false;
			}

			@Override
			public String getBotId() {
				return null;
			}

			@Override
			public Bot<?, ?> createBot() throws InstantiationException,
					IllegalAccessException, IllegalArgumentException,
					InvocationTargetException, SecurityException,
					NoSuchMethodException {
				return new PrebuiltBlackMamba("_");
			}
		};
		
		List<Future<Game<?, ?>>> games = new ArrayList<Future<Game<?,?>>>();
		for (int i = 0; i < 100; i++) {
			BotAgainstBotPlay play = createGame(String.valueOf(i),
					fac.createBot(), fac2.createBot());
			games.add(execService.submit(play));			
		}
		
		for(int i=0;i<100;i++) {
			Game<?, ?> game = games.get(i).get(500, TimeUnit.MILLISECONDS);
			assertTrue("There should be no error", game.getError() == null);
		}
	}

	private Game<?, ?> playGame(BotData data) throws Exception {
		return playGame(data, preBuiltBot);
	}

	private Game<?, ?> playGame(BotData data, Bot<?, ?> otherBot)
			throws Exception {
		BuildService buildService = new BuildService(service);
		BuildResult result = buildService.buildBot(data);
		assertTrue("Build should succeed", result.isSuccess());

		Bot<?, ?> bot = null;
		try {
			bot = result.getBotFactory().createBot();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return playGame(bot, otherBot);
	}

	private Game<?, ?> playGame(Bot<?, ?> bot1, Bot<?, ?> bot2)
			throws Exception {
		Game<?, ?> game = createGame("_", bot1, bot2).call();
		return game;
	}

	private BotAgainstBotPlay createGame(String id, Bot<?, ?> bot1,
			Bot<?, ?> bot2) throws Exception {
		@SuppressWarnings("unchecked")
		Game<?, ?> game = new PGGame(id, (Bot<List<Integer>, Integer>) bot1,
				(Bot<List<Integer>, Integer>) bot2);
		BotAgainstBotPlay play = new BotAgainstBotPlay(game);
		return play;
	}
}
