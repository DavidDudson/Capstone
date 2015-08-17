package test.nz.ac.massey.cs.ig.core.services.api.server;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import nz.ac.massey.cs.ig.api.ContextLifecycleManager;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.Services;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler.Context;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.reflections.Reflections;

public class TestServer {

	private Server server;

	private Context context;

	public TestServer() throws Exception {
		initServer();
	}

	private void initServer() throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		System.setProperty("catalina.base", new File("target").getAbsolutePath());
		server = new Server(8080);

		ServletContextHandler ctx = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		ctx.setResourceBase(".");
		ctx.setContextPath("/");
		String ini = getClass().getResource("/shiro.ini").toString();
		ctx.setInitParameter("shiroConfigLocations", ini);
		ctx.setInitParameter("shiroEnvironmentClass",
				IniWebEnvironment.class.getName());
		ctx.setInitParameter(Configuration.NAME, TestConfiguration.class.getName());

		ContextLifecycleManager manager = new ContextLifecycleManager();
		ctx.addEventListener(manager);

		ctx.addFilter(org.apache.shiro.web.servlet.ShiroFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE,
						DispatcherType.ERROR, DispatcherType.FORWARD));

		ctx.addEventListener(new EnvironmentLoaderListener());
		ctx.addEventListener(new ServletContextListener() {

			@Override
			public void contextDestroyed(ServletContextEvent arg0) {}

			@Override
			public void contextInitialized(ServletContextEvent arg0) {
				Services services = (Services) context
						.getAttribute(Services.NAME);
				EntityManager manager = services.createEntityManager();
				UserData data = manager.find(UserData.class, "foo");
				if (data == null) {
					EntityTransaction trans = manager.getTransaction();
					trans.begin();
					manager.persist(new UserData("foo"));
					trans.commit();
				}
				manager.close();
			}
		});

		server.setHandler(ctx);

		context = ctx.getServletContext();

		Reflections reflections = new Reflections(
				"nz.ac.massey.cs.ig.api.servlets");
		Set<Class<? extends HttpServlet>> subTypes = reflections
				.getSubTypesOf(HttpServlet.class);

		for (Class<? extends HttpServlet> clazz : subTypes) {
			addServlet(ctx, clazz);
		}
	}

	public void start() throws Exception {
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	private void addServlet(ServletContextHandler context,
			Class<? extends HttpServlet> type) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		WebServlet info = type.getAnnotation(WebServlet.class);
		if (!Modifier.isAbstract(type.getModifiers())) {
			for (String pattern : info.urlPatterns()) {
				HttpServlet servlet = type.getConstructor().newInstance();
				context.addServlet(new ServletHolder(servlet), pattern);
			}
		}
	}

	public Services getServices() {
		return (Services) context.getAttribute(Services.NAME);
	}
}
