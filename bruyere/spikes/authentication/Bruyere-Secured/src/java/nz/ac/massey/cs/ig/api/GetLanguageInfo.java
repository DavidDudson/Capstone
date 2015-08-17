package nz.ac.massey.cs.ig.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.Language;

@WebServlet(name = "GetLanguageInfo", urlPatterns = {"/languageInfo/*"})
public class GetLanguageInfo extends HttpServlet {

	private static final long serialVersionUID = -2310768414726198872L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String language = request.getPathInfo();
        if (language!=null && language.startsWith("/")) language = language.substring(1);
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.write(Language.getLanguageInfo(language));
        out.close();
    }

}
