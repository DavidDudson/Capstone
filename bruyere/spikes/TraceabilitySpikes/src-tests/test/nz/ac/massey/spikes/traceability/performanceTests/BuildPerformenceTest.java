package test.nz.ac.massey.spikes.traceability.performanceTests;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.LogService;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.core.services.storage.StorageException;
import nz.ac.massey.cs.ig.games.primegame.PGBot;
import nz.ac.massey.cs.ig.languages.java.JavaSupport;
import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.Instrumenter;
import nz.ac.massey.spikes.traceability.tracer.Tracer;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuildPerformenceTest {
	private String className ="nz.ac.massey.spikes.traceability.mockData.PrimeGameBot";
	private StringBuilder PGBOTsource =new StringBuilder(
			"package nz.ac.massey.spikes.traceability.mockData;\n"
			+"import java.util.List;\n"
			+"import nz.ac.massey.cs.ig.games.primegame.PGBot;\n"
			+"public class PrimeGameBot extends PGBot{\n"
			+"public PrimeGameBot(String botId) {\n"
			+		"super(botId);\n"
    		+"}\n"

			+"@Override\n"
			+"public Integer nextMove(List<Integer> game) {\n"
			+		"assert game.size()>0;\n"
			+		"return game.get(game.size()-1);\n"
			+	"}\n"
			+"}"
			);
	
	private Class<?>[] classesInWhitelist = new Class[] { Game.class,
			Bot.class, Object.class, String.class, Class.class,
			Number.class, Integer.class, Character.class, Float.class,
			Double.class, Long.class, Short.class, Boolean.class,
			Math.class, Iterable.class, Comparable.class, Comparator.class, Exception.class,
			Error.class, RuntimeException.class,
			ArrayIndexOutOfBoundsException.class,
			NullPointerException.class,
			UnsupportedOperationException.class, ClassCastException.class,
			IllegalArgumentException.class, IllegalStateException.class,
			SecurityException.class, StackOverflowError.class,
			java.lang.AssertionError.class,
			StringIndexOutOfBoundsException.class,
			ReflectiveOperationException.class,
			NumberFormatException.class, NegativeArraySizeException.class,
			CloneNotSupportedException.class, ArithmeticException.class,
			ArrayStoreException.class, Collection.class, Iterator.class,
			List.class, ArrayList.class, Vector.class, LinkedList.class,
			AbstractList.class, Stack.class, Set.class, TreeSet.class,
			HashSet.class, SortedSet.class, EnumSet.class,
			LinkedHashSet.class, BitSet.class, Arrays.class,
			Collections.class, Map.class, HashMap.class, Date.class,
			Calendar.class, GregorianCalendar.class, Random.class ,Tracer.class,PGBot.class};

	@Mock
	Services services;
	@Mock
	LogService logservice;
	@Mock
	Logger logger;
	@Mock
	GameSupport gameSupport;

	BuildService build;
	BuildProblemCollector issues;
	BotData PGbot;
	String botId="pgbot";
	
	@Before
	public void setup(){
		build =new BuildService(services);
		when(services.getLogService()).thenReturn(logservice);
		when(services.getLogService().getLogger(Mockito.anyString())).thenReturn(logger);
		when(services.getProgrammingLanguageSupport(Mockito.anyString())).thenReturn(new JavaSupport());
		when(services.getGameSupport()).thenReturn(gameSupport);
		when(services.getGameSupport().getWhitelistedClasses()).thenReturn(Arrays.asList(classesInWhitelist));
		PGbot =new BotData(botId);
		PGbot.setOwner(new UserData());
		PGbot.setLanguage("JAVA");

		issues = new BuildProblemCollector();
	}
	@Test
	public void testWithoutInstrumentation() throws Exception {
		long startTime=System.currentTimeMillis();
		for(int i =0; i<10;i++){
			System.out.println("iteration: "+(i+1));
			System.gc();
			System.out.println(" without instrumentation testing finished, "+withoutInstrumentation(500));
			System.out.println(" with instrumentation testing finished, "+withInstrumentation(500));		
			
		}
		long endTime =System.currentTimeMillis();
		System.out.println("tested fininshed,total time(milisecond) is:"+(endTime-startTime));
	}
	
	public String withInstrumentation(int iteration) throws Exception{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
	
			Thread t=new Thread(new Runnable(){
				@Override
				public void run() {
					
					try {
						PGbot.setSrc(new Instrumenter().instrument(PGBOTsource.toString(),className,botId,"game"));
						//build.buildBot(PGbot, issues);
					} catch (Exception e) {

						e.printStackTrace();
					}	
				}
				
			});
			long startTime=System.currentTimeMillis();
			t.start();
			t.join();
			long endTime =System.currentTimeMillis();
			totleTime=totleTime+(endTime-startTime);
		    Runtime runtime= Runtime.getRuntime();
		    totleMemory= totleMemory+(runtime.totalMemory() - runtime.freeMemory());
			System.gc();
		}	
		return "average time(millisecond) is: "+String.valueOf((totleTime/iteration))+" average used memory in bytes: "+String.valueOf(totleMemory/iteration);
	}
	public String withoutInstrumentation(int iteration) throws StorageException, BuilderException, InterruptedException{
		long totleTime=0;
		long totleMemory=0;
		for(int i=0;i<iteration;i++){
			Thread t=new Thread(new Runnable(){
				@Override
				public void run() {
					
					try {
						PGbot.setSrc(PGBOTsource.toString());
						//build.buildBot(PGbot, issues);
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			});
			long startTime=System.currentTimeMillis();
			t.start();
			t.join();
			long endTime =System.currentTimeMillis();
			totleTime=totleTime+(endTime-startTime);
		    Runtime runtime= Runtime.getRuntime();
		    totleMemory= totleMemory+(runtime.totalMemory() - runtime.freeMemory());
		    System.gc();
		}	
		return "average time(millisecond) is: "+String.valueOf((totleTime/iteration))+" average used memory in bytes: "+String.valueOf(totleMemory/iteration);
	}
}
