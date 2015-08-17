
package nz.ac.massey.cs.ig.api;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.StorageException;
import static nz.ac.massey.cs.ig.api.Utils.*;

import org.json.JSONException;
import org.json.JSONWriter;

/**
 * Get the bots owned by a user.
 * Returns a json encoded collection of bot metadata.
 * @author jens dietrich
 */
@WebServlet(name = "GetBotsByUser", urlPatterns = {"/userbots/*"})
public class GetBotsByUser extends HttpServlet {

	private static final long serialVersionUID = 5369697631884655636L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String userId = request.getPathInfo();
        if (userId!=null && userId.startsWith("/")) userId = userId.substring(1);
        Set<String> botIds = null;
        
        // generate response
        response.setContentType("application/vnd.collection+json");
        PrintWriter out = response.getWriter();
        
        try {
            botIds = services.getStorage().getBotIds(userId);
            JSONWriter json = new JSONWriter(out);
            json.object()
                .key("collection")
                .object()
                    .key("version").value("1.0")
                    .key("items").array();
                    
                    for (String botId:botIds) {
                        Properties metadata = services.getStorage().getBotMetadata(botId);
                        json.object();
                        for (Object property:metadata.keySet()) {
                            json.key((String)property).value(metadata.getProperty((String)property));
                        }
                        json.endObject();
                    }
                    json.endArray()
                .endObject()
            .endObject();
        }
        catch (JSONException x) {
            handleException(this,response,"Error encoding bot(s) metadata for user " + userId,null);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error encoding bot metadata");
        }
        catch (StorageException ex) {
            handleException(this,response,"Cannot find bots for user " + userId,null);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error building source");
        }
        out.close();
        
    }

    @Override
    public String getServletInfo() {
        return "get a list of bot metadata for a user";
    }

}
