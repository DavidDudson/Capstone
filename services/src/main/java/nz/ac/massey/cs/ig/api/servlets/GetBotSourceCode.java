package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;

/**
 * Get the source code of a bot.
 * 
 * @author jens dietrich
 */
@WebServlet(name = "GetBotSourceCode", urlPatterns = { "/bots-src/*" })
public class GetBotSourceCode extends BasicBruyereServlet {

	private static final long serialVersionUID = 7924285699142594422L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Services services = getServices();

		String botId = request.getPathInfo();
		if (botId != null && botId.startsWith("/"))
			botId = botId.substring(1);
		String src = null;

		EntityManager manager = services.createEntityManager();
		BotData data = manager
				.createQuery("Select a from BotData a where a.id = :botid",
						BotData.class).setParameter("botid", botId)
				.getSingleResult();

		if (data == null) {
			handleException(this, response, "Cannot find bot with id " + botId,
					null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error finding source for bot " + botId);
			return;
		}

		src = data.getSrc();

		// generate response
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.print(src);
		out.close();
	}

	@Override
	public String getServletInfo() {
		return "get the source code of a bot";
	}

}
