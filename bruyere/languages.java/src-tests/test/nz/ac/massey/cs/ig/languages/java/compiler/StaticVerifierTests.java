package test.nz.ac.massey.cs.ig.languages.java.compiler;


import java.util.UUID;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.VerifierException;
import nz.ac.massey.cs.ig.languages.java.compiler.DefaultJavaBuilder;

import org.apache.logging.log4j.Logger;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Abstract reusable tests for static verifiers. To reuse these tests,
 * subclass this class and instantiate sverifier.
 * @author jens dietrich
 */
public abstract class StaticVerifierTests {

	public static final String USER = "dummyuser";
	protected StaticByteCodeVerifier sverifier = null;

	// mocks
	protected ProgrammingLanguageSupport testProgrammingLanguageSupport;

	protected DefaultJavaBuilder builder;
	
	private BotData data;

	@Before
	public void setup() {
		testProgrammingLanguageSupport = Mockito
				.mock(ProgrammingLanguageSupport.class);
		
		Logger logger = Mockito.mock(Logger.class);
		
		builder = new DefaultJavaBuilder(testProgrammingLanguageSupport, logger);
		
		data = new BotData(UUID.randomUUID().toString());
		data.setLanguage("JAVA");
		
	}

	@Test
	public void testPermitted1() throws Exception {
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
		builder.build(data).createBot();
	}
	
	private static void ifJava8() {
		boolean isJava8 = false;
		try {
			Class.forName("java.lang.invoke.LambdaMetafactory");
			isJava8 = true;
		}
		catch (Exception x) {}
		Assume.assumeTrue("WARNING: cannot test Java 8 support, the current platform is " + System.getProperty("java.version"),isJava8);
	}
	
	@Test
	public void testPermittedLambda1() throws Exception {
		
		ifJava8();
		
		String src = 
				  "package foo;\n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;\n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;\n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "import java.util.*;\n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {\n"
				+ "   private String id = null;\n"
				+ "   public MyBot(String id) {\n"
				+ "      super();\n"
				+ "      this.id=id;\n"
				+ "   }\n"
				+ "   @Override public MockMove nextMove(MockGame g) {\n"
				+ "			Comparator<String> comparator = (i,j) -> i.compareTo(j);\n"
				+ "			comparator.compare(\"a\",\"b\");\n"
				+ "         return new MockMove();\n"
				+ "   }\n"
				+ "   @Override public String getId() {return id;}\n"
				+ "}\n";

		data.setSrc(src);
		builder.build(data).createBot();
	}
	
	@Test(expected = VerifierException.class)
	public void testProhibitedLambda1() throws Exception {
		
		ifJava8();
		
		String src = 
				  "package foo;\n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;\n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;\n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "import java.util.*;\n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {\n"
				+ "   private String id = null;\n"
				+ "   public MyBot(String id) {\n"
				+ "      super();\n"
				+ "      this.id=id;\n"
				+ "   }\n"
				+ "   @Override public MockMove nextMove(MockGame g) {\n"
				+ "			java.util.function.Consumer consumer = (s) -> System.out.println(s);\n"
				+ "			consumer.accept(\"foo\");\n"
				+ "         return new MockMove();\n"
				+ "   }\n"
				+ "   @Override public String getId() {return id;}\n"
				+ "}\n";
		data.setSrc(src);
		builder.build(data).createBot();
	}
	

	@Test(expected = VerifierException.class)
	public void testProhibited_System_out_println() throws Exception {
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
				+ "         System.out.println(\"Hello World\");             \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}

	@Test(expected = VerifierException.class)
	public void testProhibited_System_currentTimeMillis()
			throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;           \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private String id = null;                              \n"
				+ "   public MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         long i = System.currentTimeMillis();             \n"
				+ "         if (i%2==0) return null;                         \n"
				+ "         else return new MockMove();                      \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}

	@Test(expected = VerifierException.class)
	public void testProhibited_Thread() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;           \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private String id = null;                              \n"
				+ "   public MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         Thread t = new Thread();                         \n"
				+ "         t.start();                                       \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}

	@Test(expected = VerifierException.class)
	public void testProhibited_io_File() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;           \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private String id = null;                              \n"
				+ "   public MyBot(String id) {                              \n"
				+ "      super();                                            \n"
				+ "      this.id=id;                                         \n"
				+ "   }                                                      \n"
				+ "   @Override public MockMove nextMove(MockGame g) {       \n"
				+ "         java.io.File f = new java.io.File(\"tmp\");      \n"
				+ "         return new MockMove();                           \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}
	
	@Test(expected = VerifierException.class)
	public void checkStaticFieldIsProhibited() throws Exception {
		String src = "package foo;                                              \n"
				+ "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
				+ "import nz.ac.massey.cs.ig.languages.java.compiler.*;  \n"
				+ "import test.nz.ac.massey.cs.ig.languages.java.compiler.*; \n"
				+ "public class MyBot implements Bot<MockGame,MockMove> {    \n"
				+ "   private static String yolo = null;                     \n"
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
		builder.build(data).createBot();
	}
	
	@Test(expected = VerifierException.class)
	public void checkStaticMethodIsProhibited() throws Exception {
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
				+ "   public static String hello() {       \n"
				+ "         return \"NO\";                           		 \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}
	
	@Test(expected = VerifierException.class)
	public void checkInnerInnerClassesAreProhibited() throws Exception {
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
				+ "   private class hello {       \n"
				+ "         private class nope {}                     		 \n"
				+ "   }                                                      \n"
				+ "   @Override public String getId() {return id;}    \n"
				+ "}                                                         \n";
		data.setSrc(src);
		builder.build(data).createBot();
	}
}
