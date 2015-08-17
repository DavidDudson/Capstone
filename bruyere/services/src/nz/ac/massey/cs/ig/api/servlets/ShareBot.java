package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;

/**
 * 
 * Servlet to share bots
 * 
 * @author Johannes Tandler
 *
 */
@WebServlet(name = "ShareBot", urlPatterns = { "/shareBot" })
public class ShareBot extends BasicBruyereServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6198153281258811584L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String botId = req.getParameter("botId");
		EntityManager entityManager = getServices().createEntityManager();
		BotData bot = entityManager.find(BotData.class, botId);
		// if bot is not available
		if(bot == null) {
			String message = "Error creating a game - source for bot "
					+ botId + " not found";
			
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().println(message);
			return;
		}
		
		UserData currentUser = getCurrentUser();
		
		// if bot is not owned by current user
		if(!currentUser.getId().equals(bot.getOwner().getId())) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resp.getWriter().println("You are not allowed to share a bot which is not yours.");
			return;
		}
		
		EntityTransaction trans = entityManager.getTransaction();
		trans.begin();
		if(req.getParameter("unshare") != null) {
			String value = req.getParameter("unshare");
			Boolean val = Boolean.valueOf(value);
			bot.setShared(!val);
		} else {
			bot.setShared(true);
		}
		entityManager.persist(bot);
		trans.commit();
		
		resp.setStatus(HttpServletResponse.SC_OK);
		return;
	}
}
