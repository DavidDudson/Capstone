package nz.ac.massey.cs.ig.api.config.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.game.model.UserRoles;
import nz.ac.massey.cs.ig.core.services.Services;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserDataAuthorizer extends AuthorizingRealm {
	
	private ServletContext context;
	
	public UserDataAuthorizer(CacheManager cacheManager, ServletContext context) {
		super(cacheManager);
		this.context = context;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		String userId = principals.getPrimaryPrincipal().toString();
		
		Services services = (Services)context.getAttribute(Services.NAME);
		EntityManager mg = services.createEntityManager();
		
		UserData data = mg.find(UserData.class, userId);
		mg.close();
		if(data == null) {
			return null;
		} 
		
		Set<String> roles = new HashSet<>();
		for(UserRoles role : data.getRoles()) {
			roles.add(role.name().toLowerCase());
		}
		
		//roles.add("teacher");
		
		AuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		return null;
	}

}
