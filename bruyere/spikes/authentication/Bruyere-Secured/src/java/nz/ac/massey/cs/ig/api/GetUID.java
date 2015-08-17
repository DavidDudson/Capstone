
package nz.ac.massey.cs.ig.api;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.Services;

/**
 * Get a UID from the server.
 * @author jens dietrich
 */
@WebServlet(name = "GetUID", urlPatterns = {"/uid"})
public class GetUID extends HttpServlet {

	private static final long serialVersionUID = -4403799957528789888L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(services.getUIDGenerator().nextUID());
        out.close();
        
    }

    @Override
    public String getServletInfo() {
        return "get a new UID";
    }

}
