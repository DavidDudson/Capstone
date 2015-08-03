package nz.ac.massey.cs.ig.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import nz.ac.massey.cs.ig.core.game.Language;
import nz.ac.massey.cs.ig.core.services.Services;

@WebServlet(name = "GetLanguages", urlPatterns = {"/getLanguages"})
public class GetLanguages extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5436675667599263267L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Services services = ServiceFactory.getServices(this.getServletContext());
    	JSONObject languages = new JSONObject();
    	String[] languageList = services.getLanguages();
    	for(String language : languageList){
    		try {
				languages.put(language, Language.getLanguageInfo(language));
			} catch (JSONException e) {
				// Do nothing
			}
    	}
    	
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(languages.toString());
        out.close();
    }
}
