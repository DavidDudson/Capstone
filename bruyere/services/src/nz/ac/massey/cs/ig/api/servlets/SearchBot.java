package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONWriter;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.utils.BotMetadataSerializer;

/**
 * 
 * Servlet to share bots
 * 
 * @author Johannes Tandler
 *
 */
@WebServlet(name = "SearchBot", urlPatterns = { "/searchBot" })
public class SearchBot extends BasicBruyereServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1098467402064102069L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String query = req.getParameter("q");

		EntityManager mgr = getServices().createEntityManager();

		List<BotData> bots = mgr
				.createQuery(
						"SELECT b FROM BotData b WHERE b.shared=true AND UPPER(b.name) LIKE UPPER(:q)",
						BotData.class).setParameter("q", "%" + query + "%")
				.getResultList();

		// generate response
		resp.setContentType("application/vnd.collection+json");
		PrintWriter out = resp.getWriter();

		try {

			JSONWriter json = new JSONWriter(out);
			json.object().key("collection").object().key("version")
					.value("1.0").key("items").array();

			for (BotData botMetaData : bots) {
				Properties metadata = BotMetadataSerializer
						.serialize(botMetaData);
				json.object();
				for (Object property : metadata.keySet()) {
					json.key((String) property).value(
							metadata.getProperty((String) property));
				}
				json.endObject();
			}
			json.endArray().endObject().endObject();
		} catch (JSONException x) {
			handleException(this, resp, "Error encoding bot(s) metadata ",
					null);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error encoding bot metadata");
		}
		out.close();

	}
}
