package nz.ac.massey.cs.ig.api.servlets;

import javax.persistence.EntityManager;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import nz.ac.massey.cs.ig.api.UserInfo;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Services;

/**
 * Basic {@link Servlet} which provides {@link Services} to all
 * {@link HttpServlet}.
 * 
 * @author Johannes Tandler
 *
 */
public abstract class BasicBruyereServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3072796546031450887L;

	/**
	 * Returns current services by getting it from the {@link ServletContext}.
	 * 
	 * @return
	 */
	protected Services getServices() {
		return (Services) getServletContext().getAttribute(Services.NAME);
	}


    /**
     * Returns the current user if available.
     * Otherwise null is returned.
     * @return
     */
    protected UserData getCurrentUser() {
        String userId;
        try {
            userId = UserInfo.getUserName(getServletContext());
        } catch (Exception x) {
            throw new UnsupportedOperationException(x);
        }

        if(userId == null) {
            return null;
        }

        EntityManager mg = getServices().createEntityManager();

        UserData data = mg.find(UserData.class, userId);
        mg.close();

        return data;
    }
}
