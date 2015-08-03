package nz.ac.massey.cs.ig.api.servlets;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.shiro.subject.Subject;

/**
 * Helper servlet to handle logins and their sessions correctly.
 * 
 * @author Johannes Tandler
 *
 */
public abstract class LoginServlet extends BasicBruyereServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1552355260891013499L;

	/**
	 * set default session data
	 * 
	 * @param data
	 * @param session
	 */
	protected void setSessionData(UserData data, HttpSession session) {
		session.setAttribute("userName", data.getName() != null
				&& data.getName().length() > 0 ? data.getName() : data.getId());
		session.setAttribute("userPicture", data.getPhotoUrl());
		session.setAttribute("userId", data.getId());
	}

	/**
	 * Refreshes the database entry to a given {@link UserData} or creates it if
	 * necessary
	 * 
	 * @param data
	 * @return
	 */
	protected UserData refreshUserData(UserData data) {
		EntityManager manager = getServices().createEntityManager();
		UserData oldData = manager.find(UserData.class, data.getId());

		// update oldData
		if (oldData != null) {
			oldData.setName(data.getName());
			oldData.setEmail(data.getEmail());
			oldData.setPhotoUrl(data.getPhotoUrl());
		}

		EntityTransaction trans = manager.getTransaction();

		// persist it
		trans.begin();
		if(oldData != null) {
			oldData = manager.merge(oldData);
		} else {
			manager.persist(data);
			oldData = data;
		}
		trans.commit();

		return oldData;
	}

	/**
	 * loads the specific user data from the given platform
	 * 
	 * @param subject
	 * @return
	 */
	protected abstract UserData getUserData(Subject s);

	/**
	 * post process a successful login by doing the following steps:
	 * 
	 * <ul>
	 * <li>get user data
	 * <li>refresh database entry or create it if none exists
	 * <li>sets the session data
	 * </ul>
	 * 
	 * @param subject
	 * @param session
	 */
	protected void postProcessLogin(Subject subject, HttpServletRequest request) {
		Map<String, String[]> paras = request.getParameterMap();
		if(paras.containsKey("baseURL")) {
			request.getSession().setAttribute("baseURL", paras.get("baseURL")[0]);
		}
		
		// gets the user data
		UserData data = getUserData(subject);
		// refresh the user data
		data = refreshUserData(data);
		// set session data
		setSessionData(data, request.getSession());
	}
}
