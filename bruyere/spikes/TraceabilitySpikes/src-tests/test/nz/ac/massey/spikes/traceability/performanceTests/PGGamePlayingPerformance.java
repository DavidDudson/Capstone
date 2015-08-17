package test.nz.ac.massey.spikes.traceability.performanceTests;

import nz.ac.massey.cs.ig.games.primegame.PGGame;
import nz.ac.massey.cs.ig.games.primegame.bots.CautiousBuiltinBot;
import nz.ac.massey.cs.ig.games.primegame.bots.GreedyBuiltinBot;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;
import test.nz.ac.massey.spikes.traceability.mockData.InsturmentedPGBot1;
import test.nz.ac.massey.spikes.traceability.mockData.InsturmentedPGBot2;
import nz.ac.massey.spikes.traceability.tracer.Tracer;

import org.junit.Ignore;
import org.junit.Test;

public class PGGamePlayingPerformance {
	private int iteration =1000;
	@Test
	public void testPrimeGameBots() throws Exception {
		PGGame game1 = new PGGame("game1",new InsturmentedPGBot2(),new InsturmentedPGBot1());
		PGGame game2 = new PGGame("game2",new GreedyBuiltinBot(),new CautiousBuiltinBot());
		game1.setBoardSize(10);
		game2.setBoardSize(10);
		BotAgainstBotPlay play1;
		BotAgainstBotPlay play2;
		play1 = new BotAgainstBotPlay(game1);
		play2 = new BotAgainstBotPlay(game2);

		long startTime=System.currentTimeMillis();		
		for(int i=0;i<10;i++){
			System.out.println("iteration: "+(i+1));
			System.gc();
			System.out.println(" without instrumentation testing finished, "+playPGWithoutInstrumentation(play1));
			System.out.println(" with instrumentation testing finished, "+playPGWithInstrumentation(play2));
		}
		long endTime =System.currentTimeMillis();
		System.out.println("tested fininshed,total time(milisecond) is:"+(endTime-startTime));
	}
	
	@Ignore
	public void serialisationSize() throws Exception{
		PGGame game1 = new PGGame("pggame",new InsturmentedPGBot2(),new InsturmentedPGBot1());
		game1.setBoardSize(10);
		BotAgainstBotPlay play1 = new BotAgainstBotPlay(game1);
		play1.call();
		System.out.println("PGGame tracce size for bot1(byte): "+(Tracer.manager.getJSON("pggame", "1").length()));
		System.out.println("PGGame tracce size for bot2(byte): "+Tracer.manager.getJSON("pggame", "2").length());
	}
	
	public String playPGWithInstrumentation(final BotAgainstBotPlay play1) throws Exception{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
			
			Thread t=new Thread(new Runnable(){
				@Override
				public void run() {
			
					try {
						play1.call();
					} catch (Exception e) {
						e.printStackTrace();
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
	public String playPGWithoutInstrumentation(final BotAgainstBotPlay play2) throws Exception{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
			Thread t=new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						play2.call();
					} catch (Exception e) {
						e.printStackTrace();
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
