
package nz.ac.massey.cs.ig.api;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.google.common.cache.Cache;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.StorageException;
import static nz.ac.massey.cs.ig.api.Utils.*;

/**
 * Get build error details.
 * @author jens dietrich
 */
@WebServlet(name = "GetBuildProblem", urlPatterns = {"/build-problems/*"})
public class GetBuildProblem extends HttpServlet {

	private static final long serialVersionUID = 378253702424095674L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
        String errorId = request.getPathInfo();
        PrintWriter out = response.getWriter();
        
        if (errorId!=null && errorId.startsWith("/")) errorId = errorId.substring(1);

        this.getServletContext().log("get build problems " + errorId);
        
        Cache<String,BuildProblemCollector> buildProblems = (Cache<String,BuildProblemCollector>)this.getServletContext().getAttribute(ContextLifecycleManager.CNTXT_ATTR_BUILD_PROBLEMS);
        BuildProblemCollector issues = buildProblems.getIfPresent(errorId);
        if (issues==null) {
            handleException(this,response,"Cannot find build error with id " + errorId,null); 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error finding build error with id " + errorId);
        }
        else {
        	buildProblems.invalidate(errorId);
        	response.setContentType("application/vnd.collection+json");
        	try {
				BuildProblemJSONEncoder.encode(issues, out);
			} catch (JSONException x) {
				handleException(this,response,"Error encoding build error with id " + errorId,x);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error encoding build error with id " + errorId);
			}
        }

        out.close();
        
    }

    @Override
    public String getServletInfo() {
        return "get the source code of a bot";
    }

}
