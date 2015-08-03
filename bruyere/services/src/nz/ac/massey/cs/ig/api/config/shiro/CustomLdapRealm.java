package nz.ac.massey.cs.ig.api.config.shiro;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomLdapRealm extends JndiLdapRealm {
	
	public static final String DISPLAY_NAME_PROPERTY = "displayName";

	@Override
	protected AuthenticationInfo createAuthenticationInfo(
			AuthenticationToken token, Object ldapPrincipal,
			Object ldapCredentials, LdapContext ldapContext)
			throws NamingException {

		Attributes attr = ldapContext.getAttributes(ldapPrincipal.toString());

		Attribute displayName = attr.get("displayName");
		String name = displayName.get().toString();
		
		UserData data = new UserData(token.getPrincipal().toString());
		data.setName(name);
	
		return new SimpleAuthenticationInfo(data, ldapCredentials, getName());
	}
	
	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection arg0) {
		return null;
	}
}
