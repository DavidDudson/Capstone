package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

/**
 * 
 * Servlet to handle Logins via ldap
 * 
 * @author Johannes Tandler
 *
 */
@WebServlet(name = "Login", urlPatterns = { "/login" })
public class LDAPLoginServlet extends LoginServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4366039340809435655L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getServletConfig().getServletContext()
				.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = req.getParameter("username");
		String pw = req.getParameter("password");

		Subject x = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				username, pw);

		try {
			x.login(token);
			if (x.isAuthenticated()) {
				postProcessLogin(x, req);
				WebUtils.redirectToSavedRequest(req, resp, "editor.jsp");
				return;
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		req.setAttribute("authMessage", "Username or password wrong.");
		getServletConfig().getServletContext()
				.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected UserData getUserData(Subject s) {
		UserData data = (UserData)s.getPrincipal();
		return data;
	}
}
