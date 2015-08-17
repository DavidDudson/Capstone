
package nz.ac.massey.cs.ig.api;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;
import nz.ac.massey.cs.ig.core.services.BuilderException;

/**
 * Set the owner of a bot.
 * This is a post.
 * The body has to have only two lines:
 * the bot id followed by the owner id
 * @author jens dietrich
 */
@WebServlet(name = "SetOwner", urlPatterns = {"/setowner"})
public class SetOwner extends HttpServlet {

	private static final long serialVersionUID = -5042590330434180998L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String botId = null;
        String userId = null;
        
        try {
            BufferedReader content = request.getReader();
            botId = content.readLine();
            userId = content.readLine();
        }
        catch (IOException x) {
            handleException(this,response,"Illegal request: the request body has to have two lines a bot id followed by an owner id", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error decoding source code");
        }
        
        try {  
            services.getStorage().setOwner(userId, botId);
            
        } catch (BuilderException x) {
            handleException(this,response,"Cannot set owner " + userId + " for bot " + botId,x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error setting owner");
        }
        
        // generate response
        response.setStatus(HttpServletResponse.SC_OK);
        
    }

    @Override
    public String getServletInfo() {
        return "set the owner of a bot";
    }

}
