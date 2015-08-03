package test.nz.ac.massey.cs.ig.languages.java.compiler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;
import nz.ac.massey.cs.ig.languages.java.compiler.DefaultJavaBuilder;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link DefaultJavaBuilder}
 * 
 * @author jens dietrich
 * @author Johannes Tandler
 */
public class JavaBuilderTests {

	// mocks/defaults
	private ProgrammingLanguageSupport testProgrammingLanguageSupport;

	private DefaultJavaBuilder builder;

	private BotData data;

	@Before
	public void setup() {
		testProgrammingLanguageSupport = Mockito
				.mock(ProgrammingLanguageSupport.class);

		Logger logger = Mockito.mock(Logger.class);

		builder = new DefaultJavaBuilder(testProgrammingLanguageSupport, logger);
		builder.setInstrumenationSupport(true);

		data = new BotData(UUID.randomUUID().toString());
		data.setLanguage("JAVA");

	}

	@Test
	public void test1() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;  \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private String id = null;                              \n"
				+ "   public MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		@SuppressWarnings("unchecked")
		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);

		MockMove move = (MockMove) bot.nextMove(new MockGame());
		assertNotNull(move);
		assertTrue(move instanceof MockMove);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBlackMambacompiles() throws Exception {
		String src = ResourceUtils.loadFromClassPath(this,
				"resources/test/BlackMamba.java");
		data.setSrc(src);
		Bot<List<Integer>, Integer> bot = (Bot<List<Integer>, Integer>) builder
				.build(data).createBot();
		assertNotNull(bot);

		int i = bot.nextMove(Arrays.asList(1, 2, 3));
		assertTrue("move by next mamba should be valid", i >= 1 && i <= 3);
	}

	// class does not have a constructor to pass id
	@Test(expected = NoSuchMethodException.class)
	public void testMissingConstructor1() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;  \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return \"bot42\";}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		@SuppressWarnings("unchecked")
		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
	}

	// class does not have a PUBLIC constructor to pass id
	@Test(expected = NoSuchMethodException.class)
	public void testMissingConstructor2() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;  \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private String id = null;                              \n"
				+ "   private MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		@SuppressWarnings("unchecked")
		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
	}

	@Test
	public void testInnerClassesAreWorking() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;  \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   public String id = null;                              \n"
				+ "   public MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) { "
				+ "			new Test().getMove();      \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "   public class Test {\n"
				+ "       public String getMove() {return MyBot.this.id;}"
				+ "   }\n"
				+ "}                                                         \n";
		data.setSrc(src);
		@SuppressWarnings("unchecked")
		Bot<MockGame, MockMove> bot = (Bot<MockGame, MockMove>) builder.build(
				data).createBot();
		assertNotNull(bot);
	}
}
