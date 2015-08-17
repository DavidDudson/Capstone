package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Services;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service to return all user ids.
 *
 * @author jens dietrich
 */

@WebServlet(name = "GetUsers", urlPatterns = { "/users" })
public class GetUsers extends BasicBruyereServlet {

	private static final long serialVersionUID = 8965175168858659772L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Object detailed = request.getParameter("detailed");

		JSONArray users = new JSONArray();

		Services services = getServices();
		EntityManager manager = services.createEntityManager();
		
		if (detailed != null && Boolean.parseBoolean(detailed.toString())) {
			List<UserData> data = manager.createQuery("SELECT a from UserData a", UserData.class).getResultList();
			
			for(UserData user : data) {
				JSONObject ob = new JSONObject();
				ob.put("id", user.getId());
				ob.put("name", user.getName());
				ob.put("email", user.getEmail());
				ob.put("botCount", user.getBots().size());
				if(user.hasRole("teacher")) {
					ob.put("role", "Teacher");
				} else {
					ob.put("role", "Student");
				}
				users.put(ob);
			}
		} else {
			List<String> userIds = manager.createQuery(
					"Select a.id from UserData a", String.class)
					.getResultList();

			for (String user : userIds) {
				users.put(user);
			}
		}

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(users.toString());
		out.close();
	}
}
