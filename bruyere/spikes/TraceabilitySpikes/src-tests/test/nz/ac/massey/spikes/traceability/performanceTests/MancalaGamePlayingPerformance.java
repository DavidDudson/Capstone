package test.nz.ac.massey.spikes.traceability.performanceTests;

import org.junit.Ignore;
import org.junit.Test;

import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedMancalaBot1;
import test.nz.ac.massey.spikes.traceability.mockData.InstrumentedMancalaBot2;
import test.nz.ac.massey.spikes.traceability.mockData.MancalaBot1;
import test.nz.ac.massey.spikes.traceability.mockData.MancalaBot2;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.mancala.MancalaGame;
import nz.ac.massey.spikes.traceability.tracer.Tracer;


public class MancalaGamePlayingPerformance {
	private int iteration =1000;
	
	@Test
	public void testMancalaBots() throws Exception{
		MancalaGame game1 =new MancalaGame("mancala1",new MancalaBot1("mancala1"),new MancalaBot2("mancala2"));
		MancalaGame game2 =new MancalaGame("mancala2",new InstrumentedMancalaBot1("mancala1"),new InstrumentedMancalaBot2("mancala2"));

		long startTime=System.currentTimeMillis();		
		for(int i=0;i<10;i++){
			System.out.println("iteration: "+(i+1));
			System.gc();
			System.out.println(" with instrumentation testing finished, "+playMancalaWithInstrumentation(game2));
			System.out.println(" without instrumentation testing finished, "+playMancalaWithoutInstrumentation(game1));
		}
		long endTime =System.currentTimeMillis();
		System.out.println("tested fininshed,total time(milisecond) is:"+(endTime-startTime));
	}
	
	@Ignore
	public void serialisationSize() throws Exception{
		MancalaGame game2 =new MancalaGame("mancala2",new InstrumentedMancalaBot1("mancala1"),new InstrumentedMancalaBot2("mancala2"));
	
		while (!game2.getState().isFinished()){
			game2.move();
		}
		System.out.println("mancala trace size for bot1(byte): "+Tracer.manager.getJSON("mancala2", "mancala1").length());
		System.out.println("mancala trace size for bot2(byte): "+Tracer.manager.getJSON("mancala2", "mancala2").length());
	}
	
	public String playMancalaWithInstrumentation(final MancalaGame game1) throws Exception{
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
	public String playMancalaWithoutInstrumentation(final MancalaGame game2) throws Exception{
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
