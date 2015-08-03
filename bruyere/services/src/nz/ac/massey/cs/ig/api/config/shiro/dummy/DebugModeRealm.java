package nz.ac.massey.cs.ig.api.config.shiro.dummy;

import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Configuration;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.common.collect.Sets;

/**
 * {@link DebugModeRealm} which is used for authorization and authentication in
 * debug mode. See {@link Configuration#isDebugMode()} for details and how to
 * activate it.
 * 
 * Every user will be authenticated and if there is the substring 'admin' in its
 * name he will be interpreted as a user with admin role.
 * 
 * @author Johannes Tandler
 *
 */
public class DebugModeRealm extends AuthorizingRealm {
	
	public DebugModeRealm(CacheManager mgr) {
		super(mgr);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String name = principals.getPrimaryPrincipal().toString();

		if (name.toLowerCase().contains("admin")) {
			return new SimpleAuthorizationInfo(Sets.newHashSet("teacher"));
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token.getCredentials().toString().length() > 0) {
			UserData data = new UserData(token.getPrincipal().toString());
			return new SimpleAuthenticationInfo(data, token.getCredentials(),
					getName());
		} else {
			return null;
		}
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}

}
