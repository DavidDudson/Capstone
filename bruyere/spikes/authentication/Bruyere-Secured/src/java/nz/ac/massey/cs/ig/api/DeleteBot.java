
package nz.ac.massey.cs.ig.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.StorageException;
import static nz.ac.massey.cs.ig.api.Utils.*;

/**
 * Delete a bot.
 * This is a delete.
 * @author jens dietrich
 */
@WebServlet(name = "DeleteBot", urlPatterns = {"/delete/*"})
public class DeleteBot extends HttpServlet {

	private static final long serialVersionUID = -1251766586507914463L;

	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String botId = request.getPathInfo();
        if (botId!=null && botId.startsWith("/")) botId = botId.substring(1);
        
        try {
        	services.getStorage().deleteBot(botId);   
        } catch (StorageException x) {  	
            handleException(this,response,"Cannot delete bot " + botId,x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting bot");
        }
        
        // generate response
        response.setStatus(HttpServletResponse.SC_OK);
        
    }

    @Override
    public String getServletInfo() {
        return "remove a bot";
    }

}
