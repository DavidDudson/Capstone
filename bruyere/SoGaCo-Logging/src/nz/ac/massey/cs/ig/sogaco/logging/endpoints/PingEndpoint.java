package nz.ac.massey.cs.ig.sogaco.logging.endpoints;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import nz.ac.massey.cs.ig.core.services.event.logging.BuildEvent;
import nz.ac.massey.cs.ig.core.services.event.logging.GameEvent;
import nz.ac.massey.cs.ig.sogaco.logging.JPAUtils;

@Path("/")
public class PingEndpoint {

	@Context
	private ServletContext context;

	@GET
	public String getPing() {
		long buildEventCount = -1;
		long gameEventCount = -1;

		try {
			EntityManager mgr = JPAUtils.getEntityManager(context);
			buildEventCount = mgr
					.createQuery("SELECT b FROM BuildEvent b", BuildEvent.class)
					.getResultList().size();
			gameEventCount = mgr
					.createQuery("SELECT b FROM GameEvent b",
							GameEvent.class).getResultList().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Hi mate!\n");
		builder.append("I'm alive and you?!\n\n");
		builder.append("Already logged :\n");
		builder.append("BuildEvents : " + buildEventCount + "\n");
		builder.append("GameEvents : " + gameEventCount + "\n");
		return builder.toString();
	}
}
