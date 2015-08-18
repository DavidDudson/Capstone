package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.api.UserInfo;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;
import nz.ac.massey.cs.ig.core.services.event.logging.GameEvent;
import nz.ac.massey.cs.ig.core.services.storage.StorageException;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;

import com.google.common.cache.Cache;

/**
 * Create a bot against bot game. This is a post. The body has to have at least
 * two lines: each containing the id of a bot additional lines will be past to
 * the GameFactory as parameters (e.g., some games may use this to set the size
 * of the board) In the response there is a Location header with the url of the
 * newly created game
 * 
 * @author jens dietrich
 */
@WebServlet(name = "CreateBotAgainstBotGame", urlPatterns = { "/creategame_b2b" })
public class CreateBotAgainstBotGame extends BasicBruyereServlet {

	private static final long serialVersionUID = -8157078705335467632L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Services services = getServices();
		String botId1 = null;
		String botId2 = null;
		BufferedReader content = request.getReader();

		try {
			botId1 = content.readLine();
			botId2 = content.readLine();
		} catch (IOException x) {
			handleException(
					this,
					response,
					"Illegal request: the request body has to have two lines with the ids of the two bots playing",
					x);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error creating a game");
		}

		if (botId1 == null || botId2 == null) {
			handleException(
					this,
					response,
					"Illegal request: the request body has to have two lines with the ids of the two bots playing",
					null);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Request body must have two lines with botIds");
		}

		List<String> params = new ArrayList<String>();
		String line = null;
		try {
			while ((line = content.readLine()) != null) {
				params.add(line);
			}
		} catch (IOException x) {
			handleException(
					this,
					response,
					"Illegal request: the request body has to have two lines with the ids of the two bots playing",
					x);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error creating a game");
		}

		// get user id = one user must be logged in to create game
		String userId = null;
		try {
			userId = UserInfo.getUserName(getServletContext());
		} catch (Exception x) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"User name unknown to server");
			return;
		}
		final String fUserId = userId;

		// create game
		String gameId = services.getUIDGenerator().nextUID(userId);

		// build bots
		String[] ids = new String[] { botId1, botId2 };
		BotFactory[] botFactories = new BotFactory[2];
		final BotData[] datas = new BotData[2];
		
		for(int i=0;i<ids.length;i++) {
			datas[i] = services.createEntityManager().find(BotData.class, ids[i]);
		}
		
		
 		BuildService bService = new BuildService(services);

		// build each bot
		for (int i = 0; i < botFactories.length; i++) {
			String botId = ids[i];

			// build bot!!!
			BuildResult buildResult = null;
			try {
				buildResult = bService.buildBot(botId,
						services.createEntityManager());
			} catch (StorageException e) {
				e.printStackTrace();
				// build failed!
				String message = "Error creating a game - unknown bot "
						+ botId;
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
				return;
			}

			if (buildResult.isError()) {
				// build failed!
				String message = "Error creating a game - cannot build bot "
						+ botId;
				System.out.println(buildResult.getIssues());
				response.addHeader("Issues",buildResult.getIssues().toString());
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
				return;
			} else {
				botFactories[i] = buildResult.getBotFactory();
			}
		}

		Bot<?, ?>[] bots = new Bot[2];
		try {
			for (int i = 0; i < 2; i++) {
				bots[i] = botFactories[i].createBot();
			}
		} catch (Exception e) {
			handleException(this, response, "Illegal bots:", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error creating a game");
			return;
		}

		GameFactory factory = services.getGameSupport().getGameFactory();
		Game<?, ?> game = factory.createGame(gameId, bots[0], bots[1]);

		// submit game to be played
		ExecutorService es = services.getExecutorServiceForGames();

		final BotAgainstBotPlay blub = new BotAgainstBotPlay(game);
		
		Object baseUrl = request.getSession().getAttribute("baseURL");
		final String sogacoInstanceId = baseUrl != null ? baseUrl.toString() : null;

		Future<Game<?, ?>> future = es.submit(new Callable<Game<?, ?>>() {

			@Override
			public Game<?, ?> call() throws Exception {
				Game<?, ?> result = blub.call();
				
				try {
					if(services.getEventLogger() != null) {
						postGameEvent(getServices(), result, datas[0], datas[1], fUserId, sogacoInstanceId);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		});
		@SuppressWarnings("unchecked")
		Cache<String, Future<Game<?, ?>>> executingGames = (Cache<String, Future<Game<?, ?>>>) this
				.getServletContext().getAttribute("executing_games");
		executingGames.put(gameId, future);

		// timeout !
		// TODO check whether async servlets could be used to improve
		// performance here
		// future.get(services.getBotAgainstBotGameTimeouts(),
		// TimeUnit.MILLISECONDS);
		String ctx = this.getServletContext().getContextPath();
		response.addHeader("Location", ctx + "/games/" + game.getId());
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	private void postGameEvent(Services services, Game<?, ?> game, BotData data1, BotData data2, String currentUser, String sogacoInstanceId) {
		EventLogger target = services.getEventLogger();

		GameEvent event = new GameEvent();
		event.setBot1Id(game.getPlayer1().getId());
		event.setBot2Id(game.getPlayer2().getId());
		event.setPlayer1Id(data1.getOwner().getId());
		event.setPlayer2Id(data2.getOwner().getId());
		event.setUserId(currentUser);
		event.setGameId(services.getGameSupport().getName());
		event.setSogacoInstanceId(sogacoInstanceId);
		int[] versions = services.getGameSupport().getVersion();
		if (versions != null) {
			event.setGameVersionMajor(versions[0]);
			event.setGameVersionMinor(versions[1]);
			event.setGameVersionRevision(versions[2]);
		}
		
		if(game.getError() != null) {
			event.setError(game.getError().toString());
		}
		
		GameState state = game.getState();
		switch(state) {
		case PLAYER_1_WON:
			event.setGameResult(nz.ac.massey.cs.ig.core.services.event.logging.GameEvent.GameState.PLAYER_1_WON);
			break;
		case PLAYER_2_WON:
			event.setGameResult(nz.ac.massey.cs.ig.core.services.event.logging.GameEvent.GameState.PLAYER_2_WON);
			break;
		case TIE:
			event.setGameResult(nz.ac.massey.cs.ig.core.services.event.logging.GameEvent.GameState.TIE);
			break;
		default :
			event.setGameResult(nz.ac.massey.cs.ig.core.services.event.logging.GameEvent.GameState.OTHER);
		}

		target.logGameEvent(event);
	}

	@Override
	public String getServletInfo() {
		return "create a bot against bot game";
	}

}
