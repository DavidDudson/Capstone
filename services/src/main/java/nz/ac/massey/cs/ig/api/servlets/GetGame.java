package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import nz.ac.massey.cs.ig.api.ContextLifecycleManager;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.Services;
import com.google.common.cache.Cache;

/**
 * Get the game state.
 * @author jens dietrich
 */
@WebServlet(name = "GetGame", urlPatterns = { "/games/*" })
public class GetGame extends BasicBruyereServlet {

	private static final long serialVersionUID = -2287341591040255701L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Services services = getServices();

		String gameId = request.getPathInfo();
		if (gameId != null && gameId.startsWith("/"))
			gameId = gameId.substring(1);

		Serializer serializer = services.getGameSupport().getSerializer();

		@SuppressWarnings("unchecked")
		Cache<String, Future<Game<?, ?>>> executingGames = (Cache<String, Future<Game<?, ?>>>) this
				.getServletContext().getAttribute(
						ContextLifecycleManager.CNTXT_ATTR_EXECUTING_GAMES);

		Future<Game<?, ?>> future = executingGames.getIfPresent(gameId);
		if (future == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Game<?, ?> game = null;
		try {
			game = future.get(services.getGameSupport().getMoveTimeout(),TimeUnit.MILLISECONDS);
		} catch (ExecutionException x) {
			Throwable cause = x.getCause();
			if (cause == null)
				cause = x;
			handleException(this, response, "Error accessing game " + gameId, x);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject json = new JSONObject();
			json.put("error", true);
			if (cause instanceof StackOverflowError) {
				json.put("details","StackOverflowError occured. Probably invalid use of recursion.");
			} else {
				json.put("details", cause.getMessage());
			}
			out.println(json);
			out.close();
			return;
		} catch (Throwable x) {
			handleException(this, response, "Error accessing game " + gameId, x);
			x.printStackTrace();
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject json = new JSONObject();
			json.put("error", true);
			json.put("details", x.getMessage());
			out.println(json);
			out.close();
			return;
		}

		// generate response
		response.setContentType(serializer.getGameContentType());
		PrintWriter out = response.getWriter();
		serializer.encodeGame(game, out);
		out.close();

	}

	@Override
	public String getServletInfo() {
		return "get game state";
	}

}
