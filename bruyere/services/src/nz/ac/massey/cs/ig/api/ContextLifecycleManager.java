package nz.ac.massey.cs.ig.api;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nz.ac.massey.cs.ig.api.config.PropertiesConfiguration;
import nz.ac.massey.cs.ig.api.config.shiro.CustomShiroWebEnviroment;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.ServiceFactory;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.defaults.ExtensibleServiceFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class ContextLifecycleManager implements ServletContextListener {

	public static final String CNTXT_ATTR_EXECUTING_GAMES = "executing_games";
	public static final String CNTXT_ATTR_BUILD_PROBLEMS = "build_problems";

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		Services services = (Services) e.getServletContext().getAttribute(
				Services.NAME);
		
		Configuration config = (Configuration)e.getServletContext().getAttribute(Configuration.NAME);
		config.save();

		e.getServletContext().log("Stopping application threads");
		services.getExecutorServiceForGames().shutdownNow();

		e.getServletContext().log("Emptying executing games cache");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Cache<String, Future<Game>> executingGames = (Cache<String, Future<Game>>) e
				.getServletContext().getAttribute(CNTXT_ATTR_EXECUTING_GAMES);
		executingGames.invalidateAll();

		e.getServletContext().log("Emptying build problems cache");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Cache<String, Future<Game>> buildProblems = (Cache<String, Future<Game>>) e
				.getServletContext().getAttribute(CNTXT_ATTR_BUILD_PROBLEMS);
		buildProblems.invalidateAll();

		services.destroy();
	}
	
	

	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext servletContext = e.getServletContext();
		
		String rootPath = initRootPath(servletContext);
		servletContext.setAttribute(Services.ROOT_PATH, rootPath);
		
		Configuration config = null;
		if(servletContext.getInitParameter(Configuration.NAME) == null) {
			config = new PropertiesConfiguration(rootPath);
		} else {
			try {
				@SuppressWarnings("unchecked")
				Constructor<Configuration> cons = (Constructor<Configuration>)Class.forName(servletContext.getInitParameter(Configuration.NAME)).getConstructor(String.class);
				config = cons.newInstance(rootPath);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		servletContext.setAttribute(Configuration.NAME, config);

		
		if (servletContext.getInitParameter("shiroEnvironmentClass") == null) {
			servletContext.setInitParameter("shiroEnvironmentClass",
					CustomShiroWebEnviroment.class.getName());
		}

		servletContext.log("Creating executing games cache");
		@SuppressWarnings("rawtypes")
		Cache<String, Future<Game>> executingGames = CacheBuilder.newBuilder()
				.maximumSize(10000).expireAfterAccess(1, TimeUnit.HOURS)
				// .softValues()
				.removalListener(new RemovalListener<String, Future<Game>>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, Future<Game>> rn) {
						rn.getValue().cancel(true);
					}
				}).build();
		servletContext.setAttribute(CNTXT_ATTR_EXECUTING_GAMES, executingGames);

		servletContext.log("Creating build problems cache");
		Cache<String, BuildProblemCollector> buildProblems = CacheBuilder
				.newBuilder().maximumSize(10000)
				.expireAfterAccess(15, TimeUnit.MINUTES)
				// .softValues()
				.build();
		servletContext.setAttribute(CNTXT_ATTR_BUILD_PROBLEMS, buildProblems);

		// initialize services
		Services services = initializeServices(servletContext, config);
		servletContext.setAttribute(Services.NAME, services);
	}
	


	/**
	 * configures root path for database
	 *
	 * @param context
	 */
	private String initRootPath(ServletContext context) {
		// get tomcat home
		String rootPath = System.getProperty("catalina.base");
		// add ./.SoGaCo/ folder
		rootPath += File.separator + ".SoGaCo" + File.separator;

		// add name of current context path to root path
		String contextPath = context.getContextPath();
		if (contextPath.startsWith("/"))
			contextPath = contextPath.substring(1);

		rootPath += contextPath;

		// create directory if it doesnt exist
		File file = new File(rootPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		return rootPath;
	}

	@SuppressWarnings("unchecked")
	public Services initializeServices(ServletContext context, Configuration config) {
		Class<? extends ServiceFactory> servicesClass = ExtensibleServiceFactory.class;

		// load service class name
		String serviceClassName = context
				.getInitParameter(ServiceFactory.SERVICE_FACTORY_CLASS_KEY);
		if (serviceClassName != null) {
			try {
				servicesClass = (Class<? extends ServiceFactory>) Class
						.forName(serviceClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		// build service
		try {
			ServiceFactory factory = servicesClass.newInstance();
			return factory.build(config);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;

	}

}
