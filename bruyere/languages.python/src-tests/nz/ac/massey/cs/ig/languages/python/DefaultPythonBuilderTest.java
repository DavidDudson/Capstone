package nz.ac.massey.cs.ig.languages.python;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.UIDGenerator;
import nz.ac.massey.cs.ig.core.services.build.Builder;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultUIDGenerator;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for the Default Python Builder
 * 
 * @author Isaac Udy
 */
// TODO: Should these test be pulled into another class? Most likely yes...
public class DefaultPythonBuilderTest {

	public static final String USER = "dummyuser";
	protected Builder builder = null;

	// mocks/defaults
	protected UIDGenerator uidGenerator = new DefaultUIDGenerator();
	protected StaticASTVerifier astverifier = new DefaultPythonASTVerifier(
			Arrays.asList(MockMove.class, MockGame.class, MockBot.class));

	@Before
	public void setUp() {
		ProgrammingLanguageSupport sup = Mockito
				.mock(ProgrammingLanguageSupport.class);
		this.builder = new DefaultPythonBuilder(sup, Mockito.mock(Logger.class));
		this.builder.setAstVerifier(astverifier);
	}

	@After
	public void tearDown() {
		this.builder = null;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBasicBuild() throws Exception {
		this.builder.setInstrumenationSupport(false);
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def nextMove(game):\n"
				+ "    return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInstrumentedBuild() throws Exception {
		builder.setInstrumenationSupport(true);

		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def nextMove(game):\n"
				+ "    return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	public void testMissingParameters() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def nextMove():\n" + "	return MockMove()";

		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	public void testTooManyParameters() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def nextMove(a,b,c,d):\n" + "	return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	public void testBadIndentation() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "	def nextMove(game):\n" + "		return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	public void testUseOpen() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def nextMove(game):\n"
				+ "    file = open('file.txt', 'r')\n"
				+ "    return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		this.builder.setAstVerifier(astverifier);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMultipleMethods() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def otherMethod(a, b):\n"
				+ "	return a + b"
				+ "\n"
				+ "def nextMove(game):\n"
				+ "	otherMethod(5,4)\n"
				+ "	return MockMove()";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	public void testNoNextMove() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def otherMethod(a, b):\n" + "	return a + b";

		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = BuilderException.class)
	// The Python interpreter will still 'find' nextMove in this case, but
	// nextMove will not be callable as a function (as it is a variable), so the
	// builder needs to realize this is not a function
	public void testNextMoveIsVariable() throws Exception {
		String src = "from nz.ac.massey.cs.ig.languages.python import MockMove\n"
				+ "def otherMethod(a, b):\n"
				+ "	return a + b\n"
				+ "nextMove = 5";
		BotData data = new BotData("test");
		data.setSrc(src);

		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
		MockMove move = bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}
}
