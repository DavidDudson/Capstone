/**
 * 
 */
package test.nz.ac.massey.spikes.traceability.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import test.nz.ac.massey.spikes.traceability.instrumenter.compiler.Compiler;
import test.nz.ac.massey.spikes.traceability.instrumenter.compiler.MethodInvoker;
import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.Instrumenter;
import nz.ac.massey.spikes.traceability.tracer.Tracer;

import org.junit.After;
import org.junit.Test;


/**
 * Tests for both instumenter and tracer
 * @author Li Sui
 *
 */
public class IntegrationTests {
	private final String packageName = "nz.ac.massey.spikes.testSourceCode.TestBot1";
	private String gameId="game";
	@Test
	public void testOneMove() throws Exception {
		String botId="bot1";
		StringBuilder source =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+ "import java.util.List;\n"
				+	"public class TestBot1{\n"
				+	"private String botid;\n"
				+	"public TestBot1(String botId) {\n"
				+		"this.botid=botId;\n"
				+	"}\n"
				+	"public Integer nextMove(List<Integer> game) {\n"
				+		"int size = game.size();\n"
				+		"return game.get(size-1);\n"
				+	"}\n"
				+	"}"
					);
		//instrument source
		String output =(new Instrumenter().instrument(source.toString(), packageName, botId,gameId));
		//compile code after instrumentaion
		Class<?> claz =new Compiler().compile(packageName, output);
		//call nextMove once
		new MethodInvoker().invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
		
		//only on iteration
		assertEquals(1,Tracer.manager.getManager().get(gameId).get(botId).size());
		// two snapshots in this move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).size());
		//two variables registered in the first snapshots
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).size());
		//line number for game
		assertEquals(8,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getLineNumber());
		//line number for size
		assertEquals(9,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getLineNumber());
		//the game value
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getValue("game"));
		//the size value
		assertEquals("4",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getValue("size"));
	}
	
	@Test
	public void testTwoMoves() throws Exception{
		String botId="bot2";
		StringBuilder source =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+ "import java.util.List;\n\n"
				+	"public class TestBot1{\n"
				+	"private String botid;\n"
				+	"public TestBot1(String botId) {\n"
				+		"this.botid=botId;\n"
				+	"}\n"
				+	"public Integer nextMove(List<Integer> game) {\n"
				+		"int size = game.size();\n"
				+		"return game.get(size-1);\n"
				+	"}\n"
				+	"}"
					);
		//instrument source
		String output =(new Instrumenter().instrument(source.toString(), packageName, botId,gameId));
		//compile code after instrumentaion
		Class<?> claz =new Compiler().compile(packageName, output);
		//call nextmove
		MethodInvoker invoker =new MethodInvoker();
		//first move
		invoker.invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
		//second move
		invoker.callAgain(getListOfInt(1,2));
		
		//two moves
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).size());
		//two snapshots in the first move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).size());
		//two variables registered in the first snapshots first move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).size());
		//line number for game.first move
		assertEquals(9,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getLineNumber());
		//line number for size first move
		assertEquals(10,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getLineNumber());
		//the game value first move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getValue("game"));
		//the size value first move
		assertEquals("4",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getValue("size"));
		
		//two snapshots in the second move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(1).size());
		//two variables registered in the first snapshots second move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(1).get(0).size());
		//line number for game second move
		assertEquals(9,Tracer.manager.getManager().get(gameId).get(botId).get(1).get(0).getLineNumber());
		//line number for size second move
		assertEquals(10,Tracer.manager.getManager().get(gameId).get(botId).get(1).get(1).getLineNumber());
		//the game value second move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2]}",Tracer.manager.getManager().get(gameId).get(botId).get(1).get(0).getValue("game"));
		//the size value second move
		assertEquals("2",Tracer.manager.getManager().get(gameId).get(botId).get(1).get(1).getValue("size"));
	}
	
	@Test
	public void testMultipleBots() throws Exception{
		Thread t1=new Thread(new Runnable(){
			@Override
			public void run() {
				String botId="bot3";
				StringBuilder source =new StringBuilder(
						"package nz.ac.massey.spikes.testSourceCode;\n"
						+ "import java.util.List;\n\n"
						+	"public class TestBot1{\n"
						+	"private String botid;\n"
						+	"public TestBot1(String botId) {\n"
						+		"this.botid=botId;\n"
						+	"}\n"
						+	"public Integer nextMove(List<Integer> game) {\n"
						+		"int size = game.size();\n"
						+		"return game.get(size-1);\n"
						+	"}\n"
						+	"}"
							);
				//instrument source
				String output;
				try {
					output = (new Instrumenter().instrument(source.toString(), packageName, botId,gameId));
					//compile code after instrumentaion
					Class<?> claz =new Compiler().compile(packageName, output);
					//call nextMove once
					new MethodInvoker().invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}	
		});
		
		Thread t2=new Thread(new Runnable(){
			@Override
			public void run() {
				String botId="bot4";
				StringBuilder source =new StringBuilder(
						"package nz.ac.massey.spikes.testSourceCode;\n"
						+ "import java.util.List;\n\n"
						+	"public class TestBot1{\n"
						+	"private String botid;\n"
						+	"public TestBot1(String botId) {\n"
						+		"this.botid=botId;\n"
						+	"}\n"
						+	"public Integer nextMove(List<Integer> game) {\n"
						+		"int size = game.size();\n"
						+		"return game.get(size-1);\n"
						+	"}\n"
						+	"}"
							);
				//instrument source
				String output;
				try {
					output = (new Instrumenter().instrument(source.toString(), packageName, botId,gameId));
					//compile code after instrumentaion
					Class<?> claz =new Compiler().compile(packageName, output);
					//call nextMove once
					MethodInvoker invoker =new MethodInvoker();
					//first move
					invoker.invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
					//second move
					invoker.callAgain(getListOfInt(1,2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		//there are 2 bots
		assertEquals(2,Tracer.manager.getManager().get(gameId).size());
		/**
		 * results from bot3
		 */
		//only on iteration
		assertEquals(1,Tracer.manager.getManager().get(gameId).get("bot3").size());
		// two snapshots in this move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot3").get(0).size());
		//two variables registered in the first snapshots
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot3").get(0).get(0).size());
		//line number for game
		assertEquals(9,Tracer.manager.getManager().get(gameId).get("bot3").get(0).get(0).getLineNumber());
		//line number for size
		assertEquals(10,Tracer.manager.getManager().get(gameId).get("bot3").get(0).get(1).getLineNumber());
		//the game value
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(gameId).get("bot3").get(0).get(0).getValue("game"));
		//the size value
		assertEquals("4",Tracer.manager.getManager().get(gameId).get("bot3").get(0).get(1).getValue("size"));
		
		/**
		 * results from bot4
		 */
		//two moves
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot4").size());
		//two snapshots in the first move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot4").get(0).size());
		//two variables registered in the first snapshots first move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot4").get(0).get(0).size());
		//line number for game.first move
		assertEquals(9,Tracer.manager.getManager().get(gameId).get("bot4").get(0).get(0).getLineNumber());
		//line number for size first move
		assertEquals(10,Tracer.manager.getManager().get(gameId).get("bot4").get(0).get(1).getLineNumber());
		//the game value first move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(gameId).get("bot4").get(0).get(0).getValue("game"));
		//the size value first move
		assertEquals("4",Tracer.manager.getManager().get(gameId).get("bot4").get(0).get(1).getValue("size"));
		
		//two snapshots in the second move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot4").get(1).size());
		//two variables registered in the first snapshots second move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get("bot4").get(1).get(0).size());
		//line number for game second move
		assertEquals(9,Tracer.manager.getManager().get(gameId).get("bot4").get(1).get(0).getLineNumber());
		//line number for size second move
		assertEquals(10,Tracer.manager.getManager().get(gameId).get("bot4").get(1).get(1).getLineNumber());
		//the game value second move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2]}",Tracer.manager.getManager().get(gameId).get("bot4").get(1).get(0).getValue("game"));
		//the size value second move
		assertEquals("2",Tracer.manager.getManager().get(gameId).get("bot4").get(1).get(1).getValue("size"));
		
	}
	@Test
	public void testMultipleGames() throws Exception{
		final String game1 ="game1";
		final String game2 ="game2";
		Thread t1=new Thread(new Runnable(){
			@Override
			public void run() {
				String botId="bot3";
				StringBuilder source =new StringBuilder(
						"package nz.ac.massey.spikes.testSourceCode;\n"
						+ "import java.util.List;\n\n"
						+	"public class TestBot1{\n"
						+	"private String botid;\n"
						+	"public TestBot1(String botId) {\n"
						+		"this.botid=botId;\n"
						+	"}\n"
						+	"public Integer nextMove(List<Integer> game) {\n"
						+		"int size = game.size();\n"
						+		"return game.get(size-1);\n"
						+	"}\n"
						+	"}"
							);
				//instrument source
				String output;
				try {
					output = (new Instrumenter().instrument(source.toString(), packageName, botId,game1));
					//compile code after instrumentaion
					Class<?> claz =new Compiler().compile(packageName, output);
					//call nextMove once
					new MethodInvoker().invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}	
		});
		
		Thread t2=new Thread(new Runnable(){
			@Override
			public void run() {
				String botId="bot4";
				StringBuilder source =new StringBuilder(
						"package nz.ac.massey.spikes.testSourceCode;\n"
						+ "import java.util.List;\n\n"
						+	"public class TestBot1{\n"
						+	"private String botid;\n"
						+	"public TestBot1(String botId) {\n"
						+		"this.botid=botId;\n"
						+	"}\n"
						+	"public Integer nextMove(List<Integer> game) {\n"
						+		"int size = game.size();\n"
						+		"return game.get(size-1);\n"
						+	"}\n"
						+	"}"
							);
				//instrument source
				String output;
				try {
					output = (new Instrumenter().instrument(source.toString(), packageName, botId,game2));
					//compile code after instrumentaion
					Class<?> claz =new Compiler().compile(packageName, output);
					//call nextMove once
					MethodInvoker invoker =new MethodInvoker();
					//first move
					invoker.invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
					//second move
					invoker.callAgain(getListOfInt(1,2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		//two games
		assertEquals(2,Tracer.manager.getManager().size());
		
		//there is 1 bot in game1
		assertEquals(1,Tracer.manager.getManager().get(game1).size());
		/**
		 * results from bot3 game1
		 */
		//only on iteration
		assertEquals(1,Tracer.manager.getManager().get(game1).get("bot3").size());
		// two snapshots in this move
		assertEquals(2,Tracer.manager.getManager().get(game1).get("bot3").get(0).size());
		//two variables registered in the first snapshots
		assertEquals(2,Tracer.manager.getManager().get(game1).get("bot3").get(0).get(0).size());
		//line number for game
		assertEquals(9,Tracer.manager.getManager().get(game1).get("bot3").get(0).get(0).getLineNumber());
		//line number for size
		assertEquals(10,Tracer.manager.getManager().get(game1).get("bot3").get(0).get(1).getLineNumber());
		//the game value
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(game1).get("bot3").get(0).get(0).getValue("game"));
		//the size value
		assertEquals("4",Tracer.manager.getManager().get(game1).get("bot3").get(0).get(1).getValue("size"));
		
		/**
		 * results from bot4 game 2
		 */
		//two moves
		assertEquals(2,Tracer.manager.getManager().get(game2).get("bot4").size());
		//two snapshots in the first move
		assertEquals(2,Tracer.manager.getManager().get(game2).get("bot4").get(0).size());
		//two variables registered in the first snapshots first move
		assertEquals(2,Tracer.manager.getManager().get(game2).get("bot4").get(0).get(0).size());
		//line number for game.first move
		assertEquals(9,Tracer.manager.getManager().get(game2).get("bot4").get(0).get(0).getLineNumber());
		//line number for size first move
		assertEquals(10,Tracer.manager.getManager().get(game2).get("bot4").get(0).get(1).getLineNumber());
		//the game value first move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(game2).get("bot4").get(0).get(0).getValue("game"));
		//the size value first move
		assertEquals("4",Tracer.manager.getManager().get(game2).get("bot4").get(0).get(1).getValue("size"));
		
		//two snapshots in the second move
		assertEquals(2,Tracer.manager.getManager().get(game2).get("bot4").get(1).size());
		//two variables registered in the first snapshots second move
		assertEquals(2,Tracer.manager.getManager().get(game2).get("bot4").get(1).get(0).size());
		//line number for game second move
		assertEquals(9,Tracer.manager.getManager().get(game2).get("bot4").get(1).get(0).getLineNumber());
		//line number for size second move
		assertEquals(10,Tracer.manager.getManager().get(game2).get("bot4").get(1).get(1).getLineNumber());
		//the game value second move
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2]}",Tracer.manager.getManager().get(game2).get("bot4").get(1).get(0).getValue("game"));
		//the size value second move
		assertEquals("2",Tracer.manager.getManager().get(game2).get("bot4").get(1).get(1).getValue("size"));
	}
	@Test
	public void testMultipleLines() throws Exception{
		String botId="bot5";
		StringBuilder source =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+ "import java.util.List;\n\n"
				+	"public class TestBot1{\n"
				+	"private String botid;\n"
				+	"public TestBot1(String botId) {\n"
				+		"this.botid=botId;\n"
				+	"}\n"
				+	"public Integer nextMove(List<Integer> game) {\n"
				+		"int size = \n\n"
				+ 		"game.size();\n"
				+		"return game.get(size-1);\n"
				+	"}\n"
				+	"}"
					);
		//instrument source
		String output =(new Instrumenter().instrument(source.toString(), packageName, botId,gameId));
		//compile code after instrumentaion
		Class<?> claz =new Compiler().compile(packageName, output);
		//call nextMove once
		new MethodInvoker().invoke(claz, "nextMove", List.class, getListOfInt(1,2,3,4),botId);
		//only on iteration
		assertEquals(1,Tracer.manager.getManager().get(gameId).get(botId).size());
		// two snapshots in this move
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).size());
		//two variables registered in the first snapshots
		assertEquals(2,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).size());
		//line number for game
		assertEquals(9,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getLineNumber());
		//line number for size
		assertEquals(12,Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getLineNumber());
		//the game value
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2,3,4]}",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(0).getValue("game"));
		//the size value
		assertEquals("4",Tracer.manager.getManager().get(gameId).get(botId).get(0).get(1).getValue("size"));
	}
	
	@After
	public void cleanUp(){
		Tracer.manager.getManager().clear();
	}
	private List<Integer> getListOfInt(int... numbers) {
		List<Integer> list = new ArrayList<>(numbers.length);
		for (int i:numbers) list.add(i);
		return list;
	}

}
