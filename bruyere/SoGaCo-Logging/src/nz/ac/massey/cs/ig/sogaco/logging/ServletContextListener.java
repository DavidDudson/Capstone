package nz.ac.massey.cs.ig.sogaco.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
@WebListener
public class ServletContextListener implements
		javax.servlet.ServletContextListener {

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext()
				.getAttribute("emf");
		emf.close();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

		// get tomcat home
		String rootPath = System.getProperty("catalina.base");
		// add ./.SoGaCo/ folder
		rootPath += File.separator + ".SoGaCo" + File.separator + "Logging"
				+ File.separator;

		if (!Files.exists(Paths.get(rootPath))) {
			try {
				Files.createDirectories(Paths.get(rootPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver",
				"org.apache.derby.jdbc.EmbeddedDriver");

		// add "database" to rootpath
		properties.put("javax.persistence.jdbc.url", "jdbc:derby:" + rootPath
				+ "database" + ";create=true");

		properties.put("eclipselink.ddl-generation", "create-tables");
		properties.put("eclipselink.ddl-generation.output-mode", "database");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				"SoGaCo_PersistenceUnit", properties);
		sce.getServletContext().setAttribute("emf", emf);
	}

}
