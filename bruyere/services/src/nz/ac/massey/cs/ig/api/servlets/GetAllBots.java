package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.utils.BotMetadataSerializer;

import org.json.JSONException;
import org.json.JSONWriter;

/**
 * Service to return bot metadata.
 * 
 * @author jens dietrich
 */

@WebServlet(name = "GetAllBots", urlPatterns = { "/allbots" })
public class GetAllBots extends BasicBruyereServlet {

	private static final long serialVersionUID = 1257115235806643212L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Services services = getServices();

		EntityManager manager = services.createEntityManager();
		Collection<BotData> bots = manager.createQuery("SELECT a from BotData a",
				BotData.class).getResultList();

		// generate response
		response.setContentType("application/vnd.collection+json");
		PrintWriter out = response.getWriter();

		try {

			JSONWriter json = new JSONWriter(out);
			json.object().key("collection").object().key("version")
					.value("1.0").key("items").array();

			for (BotData botMetaData : bots) {
				Properties metadata = BotMetadataSerializer.serialize(botMetaData);
				json.object();
				for (Object property : metadata.keySet()) {
					json.key((String) property).value(
							metadata.getProperty((String) property));
				}
				json.endObject();
			}
			json.endArray().endObject().endObject();
		} catch (JSONException x) {
			handleException(this, response, "Error encoding bot(s) metadata ",
					null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error encoding bot metadata");
		}
		out.close();

	}
}
