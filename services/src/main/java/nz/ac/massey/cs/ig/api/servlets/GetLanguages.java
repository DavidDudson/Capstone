package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.Services;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "GetLanguages", urlPatterns = {"/languages"})
public class GetLanguages extends BasicBruyereServlet {

	private static final long serialVersionUID = 5436675667599263267L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Services services = getServices();
    	JSONObject languages = new JSONObject();
    	
    	for(ProgrammingLanguageSupport language : services.getSupportedProgrammingLanguages()){
    		try {
				languages.put(language.getIdentifier(), language.getLanguageInfo());
			} catch (JSONException x) {
				this.getServletContext().log("Error encoding language info in JSON",x);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
    	}
    	
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(languages.toString());
        out.close();
    }
}
