package nz.ac.massey.cs.ig.api.servlets;

import static nz.ac.massey.cs.ig.api.servlets.Utils.handleException;

import java.io.BufferedReader;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.api.ContextLifecycleManager;
import nz.ac.massey.cs.ig.api.UserInfo;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.BotCache;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.core.services.event.EventLogger;
import nz.ac.massey.cs.ig.core.services.event.logging.BuildEvent;
import nz.ac.massey.cs.ig.core.utils.Utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.cache.Cache;

// TODO: TEST THE NEW LANGUAGE BASED BUILDING
// TODO: Maybe switch around how languages work? Make it easier to add new languages?
/**
 * Compile a bot. This is a post. The body contains the source to be compiled
 * and stored.
 * 
 * @author jens dietrich
 */
@WebServlet(name = "BuildAndSaveBot", urlPatterns = { "/bots", "/bots/*" })
public class BuildAndSaveBot extends BasicBruyereServlet {

	private static final long serialVersionUID = 5938620498399810346L;

	/**
	 * Updates a given bot
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String botId = req.getPathInfo().substring(1);

		Services services = getServices();
		EntityManager manager = services.createEntityManager();

		BotData data = manager.find(BotData.class, botId);
		if (data == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Unknown bot id");
			return;
		}

		String userId = null;
		try {
			userId = UserInfo.getUserName(getServletContext());
		} catch (Exception x) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"User name unknown to server");
			return;
		}

		if (!userId.equals(data.getOwner().getId())) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"You can only alter your bots");
			return;
		}

		JSONObject ob = null;
		try {
			ob = parseBody(req);
		} catch (IOException | ParseException x) {
			handleException(this, resp,
					"Error decoding source code from request", x);
			return;
		}

		EntityTransaction trans = manager.getTransaction();
		trans.begin();

		data.setLastModified(Utils.getTimeStamp());
		data.setName(ob.get("name").toString());
		data.setSrc(ob.get("src").toString());

		// build
		BuildResult result = processBuildRequest(data, manager, resp);

		if(services.getEventLogger() != null) {
			postBuildEvent(services, data, result, req.getSession().getAttribute("baseURL").toString());
		}

		if (result != null && result.isSuccess()) {
			manager.merge(data);
			trans.commit();
		} else {
			trans.rollback();
		}
	}

	private void postBuildEvent(Services services, BotData data,
			BuildResult result, String sogacoInstanceId) {
		EventLogger target = services.getEventLogger();

		BuildEvent event = new BuildEvent();
		event.setBotId(data.getId());
		event.setGameId(services.getGameSupport().getName());
		int[] versions = services.getGameSupport().getVersion();
		if (versions != null) {
			event.setGameVersionMajor(versions[0]);
			event.setGameVersionMinor(versions[1]);
			event.setGameVersionRevision(versions[2]);
		}
		event.setLanguage(data.getLanguage());
		event.setSrc(data.getSrc());
		event.setUserId(data.getOwner().getId());
		event.setStatus(result.getBuildStatus());
		event.setSogacoInstanceId(sogacoInstanceId);

		if (result.isError()) {
			event.setStacktrace(result.getIssues().toString());
		}

		target.logBuildEvent(event);
	}

	/**
	 * Creates a new bot
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo() != null && request.getPathInfo().length() > 1) {
			throw new UnsupportedOperationException(
					"You can only post new bots");
		}

		Services services = getServices();

		JSONObject ob = null;
		try {
			ob = parseBody(request);
		} catch (IOException | ParseException x) {
			handleException(this, response,
					"Error decoding source code from request", x);
			return;
		}

		String userId = null;
		try {
			userId = UserInfo.getUserName(getServletContext());
		} catch (Exception x) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"User name unknown to server");
			return;
		}

		EntityManager manager = services.createEntityManager();
		EntityTransaction trans = manager.getTransaction();
		trans.begin();

		// store
		BotData data = new BotData(services.getUIDGenerator().nextUID(userId));

		UserData user = manager.find(UserData.class, userId);
		data.setOwner(user);
		user.getBots().add(data);

		data.setLanguage(ob.get("language").toString());
		data.setName(ob.get("name").toString());
		data.setLastModified(Utils.getTimeStamp());
		data.setSrc(ob.get("src").toString());

		// build
		BuildResult result = processBuildRequest(data, manager, response);
		
		if(services.getEventLogger() != null) {
			postBuildEvent(services, data, result, request.getSession().getAttribute("baseURL").toString());
		}

		if (result != null && result.isSuccess()) {
			manager.persist(data);
			manager.merge(user);
			trans.commit();
		} else {
			trans.rollback();
		}
	}

	private JSONObject parseBody(HttpServletRequest request)
			throws IOException, ParseException {
		JSONObject ob = null;
		BufferedReader content = request.getReader();
		ob = (JSONObject) new JSONParser().parse(content);
		content.close();
		return ob;
	}

	@Override
	public String getServletInfo() {
		return "build and save a bot";
	}

	private BuildResult processBuildRequest(BotData data,
			EntityManager manager, HttpServletResponse response)
			throws IOException {
		Services services = getServices();
		ProgrammingLanguageSupport languageSupport = services
				.getProgrammingLanguageSupport(data.getLanguage());
		if (languageSupport == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Invalid language parameter");
			return null;
		}

		// realase any cached versions of this bot
		BotCache cache = services.getBotCache();
		if (cache != null && cache.isBotCached(data.getId())) {
			cache.releaseCachedBot(data.getId());
		}

		try {
			data.setqName(languageSupport.getSourceCodeUtils()
					.extractFullClassName(data.getName(), data.getSrc()));
		} catch (BuilderException e) {
			e.printStackTrace();
		}

		BuildService buildService = new BuildService(services);
		BuildResult result = buildService.buildBot(data);
		String ctx = this.getServletContext().getContextPath();

		if (result.isSuccess()) {
			// generate response
			response.addHeader("Location", ctx + "/bots-src/" + data.getId());
			response.addHeader("Location-Metadata", ctx + "/bots-metadata/"
					+ data.getId());
			response.addHeader("botid", data.getId());
			response.setStatus(HttpServletResponse.SC_CREATED);
			data.setCompilable(true);
			data.setTested(true);
		} else {
			@SuppressWarnings("unchecked")
			Cache<String, BuildProblemCollector> buildProblems = (Cache<String, BuildProblemCollector>) this
					.getServletContext().getAttribute(
							ContextLifecycleManager.CNTXT_ATTR_BUILD_PROBLEMS);
			String errorId = services.getUIDGenerator().nextUID(
					data.getOwner().getId());
			System.out.println(errorId);
			buildProblems.put(errorId, result.getIssues());

			response.addHeader("Location-Error", ctx + "/build-problems/"
					+ errorId);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error building source");
		}
		return result;
	}
}
