package nz.ac.massey.cs.ig.sogaco.logging.endpoints;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import nz.ac.massey.cs.ig.core.services.event.logging.GameEvent;
import nz.ac.massey.cs.ig.sogaco.logging.JPAUtils;

@Path("/gameevents")
public class GameEventsEndpoint {

	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GameEvent[] getBuildEvents(@Context UriInfo uriInfo) {
		EntityManager manager = JPAUtils.getEntityManager(context);

		MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
		CriteriaQuery<GameEvent> cq = manager.getCriteriaBuilder().createQuery(GameEvent.class);
		Root<GameEvent> root = cq.from(GameEvent.class);
		
		for(String parameter : parameters.keySet()) {
			List<String> values = parameters.get(parameter);
			cq.where(root.get(parameter).in(values));
		}
		
		List<GameEvent> events = manager.createQuery(cq).getResultList();
		manager.close();
		
		return events.toArray(new GameEvent[]{});
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GameEvent[] postBuildEvents(GameEvent[] events) {
		EntityManager manager = JPAUtils.getEntityManager(context);
		EntityTransaction trans = manager.getTransaction();
		trans.begin();
		for(GameEvent event : events) {
			manager.persist(event);
		}
		trans.commit();
		manager.close();
		
		return events;
	}
}
