package test.nz.ac.massey.spikes.traceability.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nz.ac.massey.spikes.traceability.tracer.Snapshot;
import nz.ac.massey.spikes.traceability.tracer.Tracer;

import org.junit.After;
import org.junit.Test;
/**
 * Tests for tracer
 * @author Li Sui
 *
 */
public class TestTracer {
	
	private String botId="MY BOT";
	private String gameId="game";
	@Test
	public void testIntOneStep(){
		Tracer.increaseIterationCounter(gameId,botId);//move snapshot closed
		Tracer.addTrace(gameId,botId, 1, "number", 6);
		
		List<List<Snapshot>> traces = Tracer.manager.getManager().get(gameId).get(botId);
		
		// only one iteration (invocation of move()) 
		assertEquals(1,traces.size());  
		
		//get first iteration
		List<Snapshot> snapshots = traces.get(0);
		
		// only one snapshot
		assertEquals(1,snapshots.size());
		
		// get line 1
		Snapshot snapshot = snapshots.get(0);
		assertEquals(1,snapshot.getLineNumber()); 
		assertEquals(1,snapshot.size());
		assertEquals("6",snapshot.getValue("number"));
		
	}
	
	@Test
	public void testListOneStep(){
		
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		Tracer.increaseIterationCounter(gameId,botId);//move snapshot closed
		Tracer.addTrace(gameId,botId, 1, "list",list);
		
		List<List<Snapshot>> traces = Tracer.manager.getManager().get(gameId).get(botId);
		
		// only one iteration (invocation of move()) 
		assertEquals(1,traces.size());  
		
		List<Snapshot> snapshots = traces.get(0);
		
		// only one snapshot
		assertEquals(1,snapshots.size());
		
		Snapshot snapshot = snapshots.get(0);
		assertEquals(1,snapshot.getLineNumber()); 
		assertEquals(1,snapshot.size());
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[\"a\",\"b\"]}",snapshot.getValue("list"));
		
		
	}
	
	@Test
	public void testMapOneStep(){
		
		Map<String,String> map = new LinkedHashMap<>();
		map.put("a","1");
		map.put("b","2");
		Tracer.increaseIterationCounter(gameId,botId);//move snapshot closed
		Tracer.addTrace(gameId,botId, 1, "map", map);
		
		List<List<Snapshot>> traces = Tracer.manager.getManager().get(gameId).get(botId);
		// only one iteration (invocation of move()) 
		assertEquals(1,traces.size());  
		
		List<Snapshot> snapshots = traces.get(0);
		
		// only one snapshot
		assertEquals(1,snapshots.size());
		
		Snapshot snapshot = snapshots.get(0);
		// line number is 1
		assertEquals(1,snapshot.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot.size());
		//get value of this variable 
		assertEquals("{\"class\":\"java.util.LinkedHashMap\",\"elements\":{\"a\":\"1\",\"b\":\"2\"}}",snapshot.getValue("map"));
		
	}
	
	
	@Test
	public void testIntSteps(){
		//first move
		Tracer.increaseIterationCounter(gameId,botId);
		Tracer.addTrace(gameId,botId, 1, "number", 6);
		Tracer.addTrace(gameId,botId, 2, "name", "jack");
		//second move
		Tracer.increaseIterationCounter(gameId,botId);
		Tracer.addTrace(gameId,botId, 1, "number", 7);
		Tracer.addTrace(gameId,botId, 2, "name", "tom");

		
		List<List<Snapshot>> traces = Tracer.manager.getManager().get(gameId).get(botId);

		// two iterations (invocation of move()) 
		assertEquals(2,traces.size());  
		//get first move
		List<Snapshot> snapshots1 = traces.get(0);
		assertEquals(2,snapshots1.size());	
		//get first snapshot
		Snapshot snapshot1 = snapshots1.get(0);
		// line number is 1
		assertEquals(1,snapshot1.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot1.size());
		assertEquals("6",snapshot1.getValue("number"));
		
		//get second move
		List<Snapshot> snapshots2 = traces.get(1);
		assertEquals(2,snapshots2.size());
		//get second snapshot
		Snapshot snapshot2 = snapshots2.get(1);
		// line number is 2
		assertEquals(2,snapshot2.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot2.size());
		assertEquals("\"tom\"",snapshot2.getValue("name"));
	}
	@Test
	public void testOneSnapshotWithMultipleVariables(){
		//first move
		Tracer.increaseIterationCounter(gameId,botId);
		Tracer.addTrace(gameId,botId, 1, "number", 6);
		Tracer.addTrace(gameId,botId, 1, "name", "jack");
		
		List<List<Snapshot>> traces = Tracer.manager.getManager().get(gameId).get(botId);

		// two iterations (invocation of move()) 
		assertEquals(1,traces.size());  
		//get first move
		List<Snapshot> snapshots = traces.get(0);
		//only on snapshot
		assertEquals(1,snapshots.size());	
		Snapshot snapshot = snapshots.get(0);
		// line number is 1
		assertEquals(1,snapshot.getLineNumber());
		// two variables been registered
		assertEquals(2,snapshot.size());
		assertEquals("6",snapshot.getValue("number"));
		assertEquals("\"jack\"",snapshot.getValue("name"));
	}
	
	@Test
	public void testMultipleBots() throws InterruptedException{
	
		Thread t1=new Thread(new Runnable(){
			@Override
			public void run() {
				Tracer.increaseIterationCounter(gameId,"bot1");
				Tracer.addTrace(gameId,"bot1", 1, "number", 6);
				Tracer.addTrace(gameId,"bot1", 2, "name", "jack");
			}	
		});
		
		Thread t2=new Thread(new Runnable(){
			@Override
			public void run() {
				Tracer.increaseIterationCounter(gameId,"bot2");
				Tracer.addTrace(gameId,"bot2", 1, "number", 7);
				Tracer.addTrace(gameId,"bot2", 2, "name", "tom");
			}	
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		List<List<Snapshot>> bot1Traces = Tracer.manager.getManager().get(gameId).get("bot1");

		// one iterations (invocation of move()) 
		assertEquals(1,bot1Traces.size());  
		//get first move
		List<Snapshot> snapshots1 = bot1Traces.get(0);
		assertEquals(2,snapshots1.size());	
		//get first snapshot
		Snapshot snapshot1 = snapshots1.get(0);
		// line number is 1
		assertEquals(1,snapshot1.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot1.size());
		assertEquals("6",snapshot1.getValue("number"));
		
		List<List<Snapshot>> bot2Traces = Tracer.manager.getManager().get(gameId).get("bot2");
		// one iterations (invocation of move()) 
		assertEquals(1,bot2Traces.size());  
		//get first move
		List<Snapshot> snapshots2 = bot2Traces.get(0);
		assertEquals(2,snapshots2.size());	
		//get first snapshot
		Snapshot snapshot2 = snapshots2.get(0);
		// line number is 1
		assertEquals(1,snapshot2.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot2.size());
		assertEquals("7",snapshot2.getValue("number"));
	}
	
	@Test
	public void testMultipleGames() throws InterruptedException{
		String game1 ="game1";
		String game2 ="game2";
		Thread t1=new Thread(new Runnable(){
			@Override
			public void run() {
				Tracer.increaseIterationCounter(game1,"bot1");
				Tracer.addTrace(game1,"bot1", 1, "number", 6);
				Tracer.addTrace(game1,"bot1", 2, "name", "jack");
			}	
		});
		
		Thread t2=new Thread(new Runnable(){
			@Override
			public void run() {
				Tracer.increaseIterationCounter(game1,"bot2");
				Tracer.addTrace(game1,"bot2", 1, "number", 7);
				Tracer.addTrace(game1,"bot2", 2, "name", "tom");
			}	
		});
		Thread t3=new Thread(new Runnable(){
			@Override
			public void run() {
				Tracer.increaseIterationCounter(game2,"bot2");
				Tracer.addTrace(game2,"bot2", 1, "number", 8);
				Tracer.addTrace(game2,"bot2", 2, "name", "lily");
			}	
		});
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
		
		//two games
		assertEquals(2,Tracer.manager.getManager().size());

		List<List<Snapshot>> bot1Traces = Tracer.manager.getManager().get(game1).get("bot1");
		// one iterations (invocation of move()) 
		assertEquals(1,bot1Traces.size());  
		//get first move
		List<Snapshot> snapshots1 = bot1Traces.get(0);
		assertEquals(2,snapshots1.size());	
		//get first snapshot
		Snapshot snapshot1 = snapshots1.get(0);
		// line number is 1
		assertEquals(1,snapshot1.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot1.size());
		assertEquals("6",snapshot1.getValue("number"));
		List<List<Snapshot>> bot2Traces = Tracer.manager.getManager().get(game1).get("bot2");
		// one iterations (invocation of move()) 
		assertEquals(1,bot2Traces.size());  
		//get first move
		List<Snapshot> snapshots2 = bot2Traces.get(0);
		assertEquals(2,snapshots2.size());	
		//get first snapshot
		Snapshot snapshot2 = snapshots2.get(0);
		// line number is 1
		assertEquals(1,snapshot2.getLineNumber());
		// only one variable been registered
		assertEquals(1,snapshot2.size());
		assertEquals("7",snapshot2.getValue("number"));
		
		List<List<Snapshot>> game2Bot2Traces = Tracer.manager.getManager().get(game2).get("bot2");
		// one iterations (invocation of move()) 
		assertEquals(1,game2Bot2Traces.size());  
		//get first move
		List<Snapshot> game2Snapshots2 = game2Bot2Traces.get(0);
		assertEquals(2,game2Snapshots2.size());	
		//get first snapshot
		Snapshot game2Snapshot2 = game2Snapshots2.get(0);
		// line number is 1
		assertEquals(1,game2Snapshot2.getLineNumber());
		// only one variable been registered
		assertEquals(1,game2Snapshot2.size());
		assertEquals("8",game2Snapshot2.getValue("number"));
	}
	@After
	public void cleanUp(){
		Tracer.manager.getManager().clear();
	}
	
}
