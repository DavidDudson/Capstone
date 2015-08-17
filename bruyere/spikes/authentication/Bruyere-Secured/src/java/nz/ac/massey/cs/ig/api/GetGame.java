
package nz.ac.massey.cs.ig.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.cache.Cache;

import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Serializer;

/**
 * Get the game state.
 * @author jens dietrich
 */
@WebServlet(name = "GetGame", urlPatterns = {"/games/*"})
public class GetGame extends HttpServlet {

	private static final long serialVersionUID = -2287341591040255701L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String gameId = request.getPathInfo();
        if (gameId!=null && gameId.startsWith("/")) gameId = gameId.substring(1);
        
        Serializer<Game<?, ?>> serializer = services.getSerializer();
        Cache<String,Future<Game>> executingGames = (Cache<String,Future<Game>>)this.getServletContext().getAttribute(ContextLifecycleManager.CNTXT_ATTR_EXECUTING_GAMES);
        @SuppressWarnings("rawtypes")
        Future<Game> future = executingGames.getIfPresent(gameId);
        Game game;
        try {
            game = future.get();
        } catch (Exception x) {
            handleException(this,response,"Error accessing game " + gameId, x);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // generate response
        response.setContentType(serializer.getGameContentType());
        PrintWriter out = response.getWriter();
        serializer.encodeGame(game,out);
        out.close();
        
    }

    @Override
    public String getServletInfo() {
        return "get game state";
    }

}
