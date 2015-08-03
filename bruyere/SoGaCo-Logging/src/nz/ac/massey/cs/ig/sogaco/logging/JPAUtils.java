package nz.ac.massey.cs.ig.sogaco.logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

public class JPAUtils {
	
	public static EntityManager getEntityManager(ServletContext context) {

		EntityManagerFactory emf = (EntityManagerFactory) context
				.getAttribute("emf");
		
		EntityManager manager = emf.createEntityManager();
		return manager;
	}

}
