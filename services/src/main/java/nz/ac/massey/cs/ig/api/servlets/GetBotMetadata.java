package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.utils.BotMetadataSerializer;

import org.json.JSONStringer;

/**
 * Get the source code of a bot.
 * 
 * @author jens dietrich
 */
@WebServlet(name = "GetBotMetadata", urlPatterns = { "/bots-metadata/*" })
public class GetBotMetadata extends BasicBruyereServlet {

	private static final long serialVersionUID = 7829910174136740731L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Services services = getServices();

		String botId = request.getPathInfo();
		if (botId != null && botId.startsWith("/"))
			botId = botId.substring(1);
		Properties metadata = null;

		try {

			EntityManager manager = services.createEntityManager();
			BotData data = manager
					.createQuery(
							"SELECT a from BotData a WHERE a.id = :id",
							BotData.class).setParameter("id", botId)
					.getSingleResult();

			metadata = BotMetadataSerializer.serialize(data);

			// generate response
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();

			JSONStringer json = new JSONStringer();
			json.object();
			for (Object k : metadata.keySet()) {
				json.key((String) k).value(metadata.get(k));
			}
			json.endObject();

			out.print(json);
			out.close();
		} catch (Exception ex) {
			handleException(this, response,
					"Cannot find metadata for bot with id " + botId, null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error building source");
		}

	}

	@Override
	public String getServletInfo() {
		return "get the meta data of a bot";
	}

}
