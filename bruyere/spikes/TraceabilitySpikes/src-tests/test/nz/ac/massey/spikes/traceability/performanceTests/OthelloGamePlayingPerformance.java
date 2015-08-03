package test.nz.ac.massey.spikes.traceability.performanceTests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.mancala.MancalaGame;
import nz.ac.massey.cs.ig.games.othello.OthelloGame;
import nz.ac.massey.cs.ig.games.othello.bots.SmartBot;
import nz.ac.massey.cs.ig.games.othello.bots.SmarterBot;
import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedMancalaBot1;
import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedMancalaBot2;
import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedOthelloBot1;
import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedOthelloBot2;
import nz.ac.massey.spikes.traceability.tracer.Tracer;

import org.junit.Ignore;
import org.junit.Test;

public class OthelloGamePlayingPerformance {
private int iteration =1000;
	
@Ignore
	public void testOthelloBots() throws Exception{
		OthelloGame game1 = new OthelloGame("othello1", new SmarterBot("1"), new SmartBot("2"));
		OthelloGame game2 = new OthelloGame("othello2",new InstrumentedOthelloBot1("bot1"), new InstrumentedOthelloBot2("bot2"));

		long startTime=System.currentTimeMillis();		
		for(int i=0;i<10;i++){
			System.out.println("iteration: "+(i+1));
			System.gc();
			System.out.println(" with instrumentation testing finished, "+playOthelloWithInstrumentation(game2));
			System.out.println(" without instrumentation testing finished, "+playOthelloWithoutInstrumentation(game1));
		}
		long endTime =System.currentTimeMillis();
		System.out.println("tested fininshed,total time(milisecond) is:"+(endTime-startTime));
	}
	
	@Test
	public void serialisationSize() throws Exception{
		OthelloGame game2 = new OthelloGame("othello2",new InstrumentedOthelloBot1("bot1"), new InstrumentedOthelloBot2("bot2"));
	
		while (!game2.getState().isFinished()){
			game2.move();
		}
		System.out.println("othello trace size for bot1(byte): "+compress(Tracer.manager.getJSON("othello2", "bot1")).length());
		System.out.println("othello trace size for bot2(byte): "+compress(Tracer.manager.getJSON("othello2", "bot2")).length());
	}
	
	
	public String compress(String str) throws IOException{
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		    GZIPOutputStream gzip = new GZIPOutputStream(out);
		    gzip.write(str.getBytes());
		    gzip.close();
		    return out.toString();
	}
	public String playOthelloWithInstrumentation(final OthelloGame game1) throws Exception{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
			Thread t=new Thread(new Runnable(){

				@Override
				public void run() {
					while (!game1.getState().isFinished()){
						try {
							game1.move();
						} catch (IllegalMoveException e) {
							e.printStackTrace();
						}
					}	
				}
			});
			
			long startTime=System.nanoTime();
			t.start();
			t.join();
			long endTime =System.nanoTime();
			totleTime=totleTime+(endTime-startTime);
		    Runtime runtime = Runtime.getRuntime();
		    totleMemory= totleMemory+(runtime.totalMemory() - runtime.freeMemory());
		    System.gc();
		}	
		return "average time(nanos) is:"+String.valueOf((totleTime/iteration))+" average used memory in bytes: "+String.valueOf(totleMemory/iteration);
		
	}
	public String playOthelloWithoutInstrumentation(final OthelloGame game2) throws Exception{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
			Thread t=new Thread(new Runnable(){

				@Override
				public void run() {
					while (!game2.getState().isFinished()){
						try {
							game2.move();
						} catch (IllegalMoveException e) {
							e.printStackTrace();
						}
					}
				}
			});
			long startTime=System.nanoTime();
			t.start();
			t.join();
			long endTime =System.nanoTime();
			totleTime=totleTime+(endTime-startTime);
			Runtime runtime = Runtime.getRuntime();
		    totleMemory= totleMemory+(runtime.totalMemory() - runtime.freeMemory());
		    System.gc();
		}	
		return "average time(nanos) is:"+String.valueOf((totleTime/iteration))+" average used memory in bytes: "+String.valueOf(totleMemory/iteration);
	}
}
