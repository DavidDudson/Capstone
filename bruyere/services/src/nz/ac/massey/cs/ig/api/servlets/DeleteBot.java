package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.BotCache;
import nz.ac.massey.cs.ig.core.services.Services;

/**
 * Delete a bot. This is a delete.
 * 
 * @author jens dietrich
 */
@WebServlet(name = "DeleteBot", urlPatterns = { "/delete/*" })
public class DeleteBot extends BasicBruyereServlet {

	private static final long serialVersionUID = -1251766586507914463L;

	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Services services = getServices();
		String botId = request.getPathInfo();
		if (botId != null && botId.startsWith("/"))
			botId = botId.substring(1);

		// manage lifecycle - notify builders to release refs
		BotCache cache = services.getBotCache();
		if (cache.isBotCached(botId)) {
			cache.releaseCachedBot(botId);
		}
		
		// changes on database
		EntityManager manager = services.createEntityManager();
		EntityTransaction trans = manager.getTransaction();
		trans.begin();
		manager.createQuery("DELETE FROM BotData WHERE id = :id")
				.setParameter("id", botId).executeUpdate();
		trans.commit();

		// generate response
		response.setStatus(HttpServletResponse.SC_OK);

	}

	@Override
	public String getServletInfo() {
		return "remove a bot";
	}

}
