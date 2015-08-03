
package nz.ac.massey.cs.ig.api;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.Language;
import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;

/**
 * Get the source code template for a new bot.
 * @author jens dietrich
 */
@WebServlet(name = "GetBotTemplate", urlPatterns = {"/template/*"})

public class GetBotTemplate extends HttpServlet {

	private static final long serialVersionUID = -5123168044659522521L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
		String[] requestPath = request.getPathInfo().split("/"); 
        String language = requestPath[1];
        String templateName = requestPath[2]; 
        if (templateName!=null && templateName.startsWith("/")) templateName = templateName.substring(1);
        String src = services.getTemplateFactory().getTemplate(language, templateName);
        if (src==null) {
            handleException(this,response,"Cannot find template with name " + templateName,null);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error building source");
        }
        else {   
	        // generate response
	        response.setContentType("text/plain");
	        PrintWriter out = response.getWriter();
	        out.print(src);
	        out.close();
        }
        
    }

    @Override
    public String getServletInfo() {
        return "get the template for a new bot";
    }

}
