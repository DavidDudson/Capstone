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

import nz.ac.massey.cs.ig.api.UnknownUserException;
import nz.ac.massey.cs.ig.api.UserInfo;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Configuration;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.utils.BotMetadataSerializer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 * Get the bots owned by a user. Returns a json encoded collection of bot
 * metadata, not the actual source code ! There is a separate service for this
 * purpose.
 * 
 * @author jens dietrich
 */
@WebServlet(name = "GetBotsByUser", urlPatterns = { "/userbots/*" })
public class GetBotsByUser extends BasicBruyereServlet {

	private static final long serialVersionUID = 5369697631884655636L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Services services = getServices();

		// get user id
		String userId = request.getPathInfo();
		if (userId != null && userId.startsWith("/"))
			userId = userId.substring(1);
		
		String currentUser = null;
		try {
			currentUser = UserInfo.getUserName(request.getServletContext());
		} catch (UnknownUserException e) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					"Unknown error");
			e.printStackTrace();
			return;
		}

		boolean isUserItSelf= false;
		if (userId.equals("__current_user")) {
			isUserItSelf = true;
			userId = currentUser;
		} else if(userId.equals(currentUser)) {
			isUserItSelf = true;
		}

		EntityManager storage = services.createEntityManager();

		// authorize access
		// builtin bots are not secured
		if (!Configuration.BUILTIN_BOTS_PSEUDO_USER.equals(userId)) {
			// get current user
			Subject subject = SecurityUtils.getSubject();

			// if user is not logged in or even remembered
			if (!subject.isAuthenticated() && !subject.isRemembered()) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"NO ACCESS");
				return;
			}

			// check if user is a teacher or the requested user itself
			boolean isTeacher = subject.hasRole("teacher");

			if (!isTeacher && !isUserItSelf) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"You don't have access to bots of user " + userId);
				return;
			}

			// if there is no user with this name
			// right now this breaks ui when somebody access the editor for the
			// first time.
			// Maybe we should create user entries once they log in!?
			/*
			 * if(!storage.containsUser(userId)) {
			 * response.sendError(HttpServletResponse
			 * .SC_OK,"User has no bots right now"); return; }
			 */
		}

		// generate response
		response.setContentType("application/vnd.collection+json");
		PrintWriter out = response.getWriter();

		List<BotData> botIds = null;
		try {
			botIds = storage.createQuery(
					"Select a from BotData a where a.owner.id = :owner",
					BotData.class).setParameter("owner", userId).getResultList();
			JSONWriter json = new JSONWriter(out);
			json.object().key("collection").object().key("version")
					.value("1.0").key("items").array();

			for (BotData bot : botIds) {
				Properties metadata = BotMetadataSerializer.serialize((bot));
				json.object();
				for (Object property : metadata.keySet()) {
					json.key((String) property).value(
							metadata.getProperty((String) property));
				}
				json.endObject();
			}
			json.endArray().endObject().endObject();
		} catch (JSONException x) {
			handleException(this, response,
					"Error encoding bot(s) metadata for user " + userId, null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error encoding bot metadata");
		}
		out.close();

	}

	@Override
	public String getServletInfo() {
		return "get a list of bot metadata for a user";
	}

}
