package nz.ac.massey.cs.ig.api;

import javax.servlet.ServletContext;

import org.apache.shiro.subject.Subject;

/**
 * Interface to obtain user info.
 * 
 * @author jens dietrich
 */
public class UserInfo {
	public static String getUserName(ServletContext context)
			throws UnknownUserException {
		try {
			Subject subject = org.apache.shiro.SecurityUtils.getSubject();
			Object userObj = subject.getPrincipal();
			return userObj.toString();
		} catch (Exception x) {
			System.out.println(org.apache.shiro.SecurityUtils.getSubject());
			context.log("Cannot obtain user name from shiro session", x);
			throw new UnknownUserException(
					"Cannot obtain user name from shiro session", x);
		}
	}
}
