
package nz.ac.massey.cs.ig.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONStringer;

import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;

/**
 * Get the source code of a bot.
 * @author jens dietrich
 */
@WebServlet(name = "GetBotMetadata", urlPatterns = {"/bots-metadata/*"})
public class GetBotMetadata extends HttpServlet {

	private static final long serialVersionUID = 7829910174136740731L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String botId = request.getPathInfo();
        if (botId!=null && botId.startsWith("/")) botId = botId.substring(1);
        Properties metadata = null;
        
        try {
        	metadata = services.getStorage().getBotMetadata(botId);
        	
        	 // generate response
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
                
            JSONStringer json = new JSONStringer();
            json.object();
            for (Object k:metadata.keySet()) {
            	json.key((String)k).value(metadata.get(k));
            }
            json.endObject();

            out.print(json);
            out.close();
        } catch (Exception ex) {
            handleException(this,response,"Cannot find metadata for bot with id " + botId,null);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error building source");
        } 
         
    }

    @Override
    public String getServletInfo() {
        return "get the meta data of a bot";
    }

}
