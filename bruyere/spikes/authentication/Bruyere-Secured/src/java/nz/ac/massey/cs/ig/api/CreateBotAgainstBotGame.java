
package nz.ac.massey.cs.ig.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.cache.Cache;

import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.BotMetaData;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.Language;
import nz.ac.massey.cs.ig.core.services.Builder;
import nz.ac.massey.cs.ig.core.services.BuilderException;
import nz.ac.massey.cs.ig.core.services.GameFactory;
import nz.ac.massey.cs.ig.core.services.StorageException;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;

/**
 * Create a bot against bot game.
 * This is a post.
 * The body has to have at least two lines:
 * each containing the id of a bot
 * additional lines will be past to the GameFactory as parameters (e.g., some games
 * may use this to set the size of the board)
 * In the response there is a Location header with the url of the newly created game
 * @author jens dietrich
 */
@WebServlet(name = "CreateBotAgainstBotGame", urlPatterns = {"/creategame_b2b"})
public class CreateBotAgainstBotGame extends HttpServlet {

	private static final long serialVersionUID = -8157078705335467632L;

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Services services = ServiceFactory.getServices(this.getServletContext());
		String ctx = this.getServletContext().getContextPath();
        String botId1 = null;
        String botId2 = null;
        BufferedReader content = request.getReader();
        
        try {
            botId1 = content.readLine();
            botId2 = content.readLine();
        }
        catch (IOException x) {
            handleException(this,response,"Illegal request: the request body has to have two lines with the ids of the two bots playing", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game");
        } 
        
        if (botId1==null || botId2==null) {
            handleException(this,response,"Illegal request: the request body has to have two lines with the ids of the two bots playing", null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Request body must have two lines with botIds");
            return;
        }
 
        List<String> params = new ArrayList<String>();
        String line = null;
        try {
            while ((line=content.readLine())!=null) {
                params.add(line);
            }
        }
        catch (IOException x) {
            handleException(this,response,"Illegal request: the request body has to have two lines with the ids of the two bots playing", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game");
        }
        
        // create game
		GameFactory factory = services.getGameFactory();
        String gameId = services.getUIDGenerator().nextUID();
        Game game = factory.createGame(gameId,botId1,botId2,params);
        
        // build bots
        Bot bot1,bot2 = null;
        String language = null;
        String src = null;
        BuildProblemCollector issues = new BuildProblemCollector();
        try {
            src = services.getStorage().getBotSourceCode(botId1);
            language = services.getStorage().getBotMetadata(botId1).getProperty(BotMetaData.LANGUAGE);
            
        	if(language.equals(Language.JAVA)){
        		bot1 = services.getBuilder(language).build(botId1, src, null, services.getStaticVerifier(), services.getDynamicVerifier(),issues);
        	}
        	else if(language.equals(Language.PYTHON)){
        		bot1 = services.getBuilder(language).build(botId1, src, services.getASTVerifier(), null, services.getDynamicVerifier(),issues);
        	}
        	else{
        		throw new BuilderException("The bot's language was not set correctly.");
        	}
        }
        catch (StorageException x) {
            handleException(this,response,"Error creating a game - source for bot " + botId1 + " not found", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game - source for bot " + botId1 + " not found");
            return;
        }
        catch (BuilderException x) {
            handleException(this,response,"Error creating a game - cannot build bot " + botId1, x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game - cannot build bot " + botId1);
            return;
        }
        
        issues = new BuildProblemCollector();
        try {
            src = services.getStorage().getBotSourceCode(botId2);
            language = services.getStorage().getBotMetadata(botId2).getProperty(BotMetaData.LANGUAGE);
            
        	if(language.equals(Language.JAVA)){
        		bot2 = services.getBuilder(language).build(botId2, src, null, services.getStaticVerifier(), services.getDynamicVerifier(),issues);
        	}
        	else if(language.equals(Language.PYTHON)){
        		bot2 = services.getBuilder(language).build(botId2, src, services.getASTVerifier(), null, services.getDynamicVerifier(),issues);
        	}
        	else{
        		throw new BuilderException("The bot's language was not set correctly.");
        	}
        }
        catch (StorageException x) {
            handleException(this,response,"Error creating a game - source for bot " + botId2 + " not found", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game - source for bot " + botId2 + " not found");
            return;
        }
        catch (BuilderException x) {
            handleException(this,response,"Error creating a game - cannot build bot " + botId2, x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating a game - cannot build bot " + botId2);
            return;
        }
        
        // submit game to be played
        ExecutorService es = services.getExecutorServiceForGames();
        Future<Game> future = es.submit(new BotAgainstBotPlay(game,bot1,bot2));
        Cache<String,Future<Game>> executingGames = (Cache<String,Future<Game>>)this.getServletContext().getAttribute("executing_games");       
        executingGames.put(gameId,future);

        // timeout !
        // TODO check whether async servlets could be used to improve performance here
        // future.get(services.getBotAgainstBotGameTimeouts(), TimeUnit.MILLISECONDS);
        response.addHeader("Location",ctx+"/games/" + game.getId());
        response.setStatus(HttpServletResponse.SC_CREATED);      
    }

    @Override
    public String getServletInfo() {
        return "create a bot against bot game";
    }

}
