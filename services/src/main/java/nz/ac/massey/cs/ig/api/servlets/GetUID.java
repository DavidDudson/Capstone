
package nz.ac.massey.cs.ig.api.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.api.UserInfo;
import nz.ac.massey.cs.ig.core.services.Services;

/**
 * Get a UID from the server.
 * @author jens dietrich
 */
@WebServlet(name = "GetUID", urlPatterns = {"/uid"})
public class GetUID extends BasicBruyereServlet {

	private static final long serialVersionUID = -4403799957528789888L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Services services = getServices();        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String userId = null;
		try {
			userId = UserInfo.getUserName(getServletContext());
		}
		catch (Exception x) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"User name unknown to server");
			return;
		}
		
		
        out.print(services.getUIDGenerator().nextUID(userId));
        out.close();
        
    }

    @Override
    public String getServletInfo() {
        return "get a new UID";
    }

}
