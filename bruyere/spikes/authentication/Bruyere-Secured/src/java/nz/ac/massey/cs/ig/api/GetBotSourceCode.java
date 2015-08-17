
package nz.ac.massey.cs.ig.api;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.StorageException;
import static nz.ac.massey.cs.ig.api.Utils.*;

/**
 * Get the source code of a bot.
 * @author jens dietrich
 */
@WebServlet(name = "GetBotSourceCode", urlPatterns = {"/bots-src/*"})
public class GetBotSourceCode extends HttpServlet {

	private static final long serialVersionUID = 7924285699142594422L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String botId = request.getPathInfo();
        if (botId!=null && botId.startsWith("/")) botId = botId.substring(1);
        String src = null;
        
        try {
            src = services.getStorage().getBotSourceCode(botId);
        } catch (StorageException ex) {
            handleException(this,response,"Cannot find bot with id " + botId,null); 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error finding source for bot " + botId);
        }
        
        // generate response
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(src);
        out.close();
    }

    @Override
    public String getServletInfo() {
        return "get the source code of a bot";
    }

}
