package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.api.config.shiro.SocialToken;
import nz.ac.massey.cs.ig.api.config.shiro.SocialToken.TokenType;
import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

/**
 * Servlet implementation class SocialLogin
 * 
 * Handles implementations of logins via Google and Facebook
 * 
 * @author Johannes Tandler
 */
@WebServlet(name = "SocialLogin", urlPatterns = { "/SocialLogin",
		"/SocialLogin/*" })
public class SocialLogin extends LoginServlet {
	private static final long serialVersionUID = 1L;

	public static final String FACEBOOK_API_KEY = "847876025298764";
	public static final String FACEBOOK_API_SECRET = "225864fb0d74faaa57e4386a12a12f4c";

	public static final String GOOGLE_API_KEY = "816084203123-ec7791fg6g4ncelcm0tsst7nk2qth7sq.apps.googleusercontent.com";
	public static final String GOOGLE_API_SECRET = "KX_YVjzd-Fx_pIw_NA8suJYC";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String type = request.getPathInfo();
		if (type.startsWith("/")) {
			type = type.substring(1);
		}

		String verifier = request.getParameter("code");
		Subject subject = SecurityUtils.getSubject();

		// handle oauth redirect!
		String callBackUrl = subject.getSession().getAttribute("callbackurl").toString();

		// get right token type
		TokenType ttype = null;
		if (type.equals("facebook")) {
			ttype = TokenType.FACEBOOK;
		} else {
			ttype = TokenType.GOOGLE;
		}

		// create token
		SocialToken token = new SocialToken(
				callBackUrl, verifier, ttype);

		// login
		subject.login(token);

		if (subject.isAuthenticated()) {
			request.getSession().setAttribute("loginType", ttype);

			postProcessLogin(subject, request);

			WebUtils.redirectToSavedRequest(request, response, "../editor.jsp");
		} else {
			getServletConfig().getServletContext()
					.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("loginType");

		// store baseURL
		request.getSession().setAttribute("baseURL", request.getParameter("baseURL"));
		
		String callBackUrl = request.getParameter("callbackurl") + "/SocialLogin/" + type;
		Subject sub = SecurityUtils.getSubject();
		sub.getSession().setAttribute("callbackurl", callBackUrl);
		
		callBackUrl = URLEncoder.encode(callBackUrl, "UTF-8");

		String redUrl = null;

		if (type.equals("facebook")) {
			redUrl = "https://www.facebook.com/dialog/oauth?response_type=code&client_id="
					+ FACEBOOK_API_KEY + "&redirect_uri=" + callBackUrl;
		} else {
			String scope = URLEncoder.encode(
					"https://www.googleapis.com/auth/plus.login", "UTF-8");
			redUrl = "https://accounts.google.com/o/oauth2/auth?response_type=code&client_id="
					+ GOOGLE_API_KEY
					+ "&redirect_uri="
					+ callBackUrl
					+ "&scope=" + scope;
		}		
		
		response.sendRedirect(redUrl);
	}

	@Override
	protected UserData getUserData(Subject s) {
		return (UserData)s.getPrincipal();
	}
}
