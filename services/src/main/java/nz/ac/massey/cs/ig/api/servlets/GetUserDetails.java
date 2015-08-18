package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Services;


@WebServlet(name = "GetUserDetails", urlPatterns = { "/userDetails/*" })
public class GetUserDetails extends BasicBruyereServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7715022789858238062L;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String userId = request.getPathInfo();
		if (userId != null && userId.startsWith("/"))
			userId = userId.substring(1);
		
		Services services = getServices();
		EntityManager mg = services.createEntityManager();
        UserData data = mg.find(UserData.class, userId);
        mg.close();
        
        request.setAttribute("user", data);
        request.setAttribute("userLog", getServices().getLogService().getCurrentLog(userId));
        request.getRequestDispatcher("/admin/userView.jsp").forward(request, resp);
	}

}
