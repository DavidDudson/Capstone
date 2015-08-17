package nz.ac.massey.cs.ig.api;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.Services;

public class ContextLifecycleManager implements ServletContextListener {
	
	public static final String CNTXT_ATTR_EXECUTING_GAMES = "executing_games";
	public static final String CNTXT_ATTR_BUILD_PROBLEMS = "build_problems";
	

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		
		Services services = ServiceFactory.getServices(e.getServletContext());
		e.getServletContext().log("Stopping application threads");
		services.getExecutorServiceForGames().shutdownNow();
		
		
		e.getServletContext().log("Emptying executing games cache");
		Cache<String,Future<Game>> executingGames = (Cache<String,Future<Game>>)e.getServletContext().getAttribute(CNTXT_ATTR_EXECUTING_GAMES);
		executingGames.invalidateAll();
		
		e.getServletContext().log("Emptying build problems cache");
		Cache<String,Future<Game>> buildProblems = (Cache<String,Future<Game>>)e.getServletContext().getAttribute(CNTXT_ATTR_BUILD_PROBLEMS);
		buildProblems.invalidateAll();
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		e.getServletContext().log("Creating executing games cache");
		Cache<String,Future<Game>> executingGames = CacheBuilder.newBuilder()
	            .maximumSize(10000)
	            .expireAfterAccess(1, TimeUnit.HOURS)
	            // .softValues()
	            .removalListener(new RemovalListener<String,Future<Game>>() {           
	                @Override
	                public void onRemoval(RemovalNotification<String,Future<Game>> rn) {
	                    rn.getValue().cancel(true);
	                }
	            })
	            .build();
		e.getServletContext().setAttribute(CNTXT_ATTR_EXECUTING_GAMES, executingGames);
		
		e.getServletContext().log("Creating build problems cache");
		Cache<String,BuildProblemCollector> buildProblems = CacheBuilder.newBuilder()
	            .maximumSize(10000)
	            .expireAfterAccess(15, TimeUnit.MINUTES)
	            // .softValues()
	            .build();
		e.getServletContext().setAttribute(CNTXT_ATTR_BUILD_PROBLEMS, buildProblems);
	}

}
