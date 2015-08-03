package test.nz.ac.massey.cs.ig.languages.java.instrumentation;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.Observer;
import nz.ac.massey.cs.ig.core.game.instrumentation.OperationCodes;
import nz.ac.massey.cs.ig.languages.java.instrumentation.AddInstrumentationAdapter;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class AddInstrumentationAdapterTest {

	@Test
	public void asmifyInstrumentedBot() {
		String botName = "test/InstrumentedExampleBot";
		final byte[] originalClassBytes = CompileUtils.compileAndGetByteCode(
				botName, botName.replace("/", ".") +  "$Test");

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		TraceClassVisitor tcv = new TraceClassVisitor(cw, new ASMifier(),
				new PrintWriter(System.out));
		ClassReader cr = new ClassReader(originalClassBytes);
		cr.accept(tcv, 0);
	}

	@Test
	@Ignore
	public void lambdaTest() {
		String botName = "test/LambdaBot";
		final byte[] originalClassBytes = CompileUtils.compileAndGetByteCode(
				botName, botName.replace("/", "."));

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		TraceClassVisitor tcv = new TraceClassVisitor(cw, new Textifier(),
				new PrintWriter(System.out));
		ClassReader cr = new ClassReader(originalClassBytes);
		cr.accept(tcv, 0);
	}

	@Test
	public void testExampleBotInstrumentation() throws Exception {
		String botName = "test/ExampleBot";
		String clazzName = botName.replace("/", ".");
		final byte[] originalClassBytes = CompileUtils.compileAndGetByteCode(
				botName, clazzName);

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor instrumentor = new AddInstrumentationAdapter(cw, clazzName);
		ClassReader cr = new ClassReader(originalClassBytes);
		cr.accept(instrumentor, 0);

		final byte[] instrumentedByteCode = cw.toByteArray();

		Class<?> botClass = CompileUtils.getClassloader(clazzName,
				instrumentedByteCode);
		@SuppressWarnings("unchecked")
		Bot<Object, Object> bot = (Bot<Object, Object>) botClass
				.getConstructors()[0].newInstance("testBot");
		InstrumentedBot iBot = (InstrumentedBot) bot;

		Observer observer = Mockito.mock(Observer.class);
		iBot.__initialize(observer);

		Mockito.verify(observer, Mockito.times(1)).setObservable(bot);
		assertEquals(
				"Observer of bot should be the same as used to initialize bot",
				observer, iBot.__getObserver());
		

		List<Object[]> expectedInvocationParameters = new ArrayList<Object[]>();
		expectedInvocationParameters.add(new Object[] {16, OperationCodes.INSTANTIATION, "java/lang/Integer", null , null});
		expectedInvocationParameters.add(new Object[] {16, OperationCodes.INVOKE, "java/util/List", "size", "()I"});
		expectedInvocationParameters.add(new Object[] {16, OperationCodes.INVOKE, "java/lang/Integer", "<init>" , "(I)V"});
		expectedInvocationParameters.add(new Object[] {18, OperationCodes.INVOKE, "java/lang/Integer", "intValue" , "()I"});
		expectedInvocationParameters.add(new Object[] {18, OperationCodes.INVOKE, "java/util/List", "get", "(I)Ljava/lang/Object;"});

		List<Integer> game = new ArrayList<Integer>();
		game.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

		ArgumentCaptor<Integer> lineNumberCaptor = ArgumentCaptor
				.forClass(Integer.class);
		ArgumentCaptor<Integer> opcodeCaptor = ArgumentCaptor
				.forClass(Integer.class);
		ArgumentCaptor<String> ownerCaptor = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<String> nameCaptor = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<String> descCaptor = ArgumentCaptor
				.forClass(String.class);

		Object x = bot.nextMove(game);
		Mockito.verify(observer, Mockito.times(expectedInvocationParameters.size())).invoke(lineNumberCaptor.capture(),
				opcodeCaptor.capture(), ownerCaptor.capture(),
				nameCaptor.capture(), descCaptor.capture());
		assertEquals("Logic of bot shouldn't change due instrumentation", game.get(game.size()-1), x);
		
		
		for(int i=0;i<expectedInvocationParameters.size();i++) {
			int lineNumber = lineNumberCaptor.getAllValues().get(i);
			int opcode = opcodeCaptor.getAllValues().get(i);
			String owner = ownerCaptor.getAllValues().get(i);
			String name = nameCaptor.getAllValues().get(i);
			String desc = descCaptor.getAllValues().get(i);
			
			Object[] expectedParamter = expectedInvocationParameters.get(i);
			
			assertEquals("Line number should match expected one", expectedParamter[0], lineNumber);
			assertEquals("Operation code should match expected one", expectedParamter[1], opcode);
			assertEquals("Owner of call should match expected one", expectedParamter[2], owner);
			assertEquals("Name of call should match expected one", expectedParamter[3], name);
			assertEquals("Description of call should match expected one", expectedParamter[4], desc);
		}
	}
	
	@Test
	public void testBotsWithAsserts() throws Exception {
		String botName = "test/BotWithAssert";
		String clazzName = botName.replace("/", ".");
		final byte[] originalClassBytes = CompileUtils.compileAndGetByteCode(
				botName, clazzName);

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		TraceClassVisitor tcv = new TraceClassVisitor(cw, new Textifier(),
				new PrintWriter(System.out));
		ClassVisitor instrumentor = new AddInstrumentationAdapter(tcv, clazzName);
		ClassReader cr = new ClassReader(originalClassBytes);
		cr.accept(instrumentor, 0);

		final byte[] instrumentedByteCode = cw.toByteArray();

		Class<?> botClass = CompileUtils.getClassloader(clazzName,
				instrumentedByteCode);
		@SuppressWarnings("unchecked")
		Bot<Object, Object> bot = (Bot<Object, Object>) botClass
				.getConstructors()[0].newInstance("testBot");
		if(bot instanceof InstrumentedBot) {
			((InstrumentedBot) bot).__initialize(Mockito.mock(Observer.class));
		}
		assertEquals("Bot should return right move", 2, bot.nextMove(Arrays.asList(1, 2)));
	}

}
