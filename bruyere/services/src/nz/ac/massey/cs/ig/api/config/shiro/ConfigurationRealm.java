package nz.ac.massey.cs.ig.api.config.shiro;

import javax.servlet.ServletContext;

import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.Configuration;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.common.collect.Sets;

public class ConfigurationRealm extends AuthorizingRealm {

	private ServletContext context;

	public ConfigurationRealm(CacheManager cache, ServletContext context) {
		super(cache);
		this.context = context;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Configuration config = getConfig();

		String id = principals.getPrimaryPrincipal().toString();
		if (config.getAdminUserIds().contains(id)) {
			return new SimpleAuthorizationInfo(Sets.newHashSet("teacher"));
		}
		return null;
	}

	private Configuration getConfig() {
		Configuration config = (Configuration) context
				.getAttribute(Configuration.NAME);
		return config;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		Configuration config = getConfig();

		UsernamePasswordToken upt = (UsernamePasswordToken) token;

		String id = upt.getUsername();
		if (config.getPredefinedUsers().containsKey(id)) {
			if (config.getPredefinedUsers().get(id)
					.equals(new String(upt.getPassword()))) {
				return new SimpleAuthenticationInfo(new UserData(id),
						token.getCredentials(), getName());
			}
		}

		return null;
	}

}
