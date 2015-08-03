package nz.ac.massey.cs.ig.api.config.shiro;

import java.util.Arrays;

import javax.servlet.ServletContext;

import nz.ac.massey.cs.ig.api.config.shiro.dummy.DebugModeRealm;
import nz.ac.massey.cs.ig.core.services.Configuration;

import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * 
 * Shiro {@link WebEnvironment} to provide a more dynamic approach to initialize
 * shiro than using an ini.
 * 
 * If {@link Configuration#isDebugMode()} is set than there will be no
 * authentication and every user will be accepted. Every user which contains
 * 'admin' in its name will be interpreted as an admin.
 * 
 * @author Johannes Tandler
 *
 */
public class CustomShiroWebEnviroment extends DefaultWebEnvironment {

	/**
	 * sets {@link SecurityManager} and {@link FilterChainResolver}
	 */
	private void init() {
		setSecurityManager(createSecurityManager());
		setFilterChainResolver(createFilterChainResolver());
	}

	/**
	 * creates the {@link SecurityManager} used by {@link #init()}
	 * 
	 * @return
	 */
	private SecurityManager createSecurityManager() {

		Configuration config = (Configuration) getServletContext()
				.getAttribute(Configuration.NAME);

		MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
		if (config.isDebugMode()) {
			DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(
					new DebugModeRealm(cacheManager));
			securityManager.setCacheManager(cacheManager);

			ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer(
					Arrays.asList(new UserDataAuthorizer(cacheManager,
							getServletContext()), new ConfigurationRealm(
							cacheManager, getServletContext()),
							new DebugModeRealm(cacheManager)));
			securityManager.setAuthorizer(authorizer);
			securityManager.setCacheManager(cacheManager);

			return securityManager;
		}

		// staff and student ldap realms
		CustomLdapRealm studentLdapRealm = new CustomLdapRealm();
		CustomLdapRealm staffLdapRealm = new CustomLdapRealm();
		initLdapRealm(studentLdapRealm, "Students");
		initLdapRealm(staffLdapRealm, "Staff");

		// create security manager
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(
				Arrays.asList(new FacebookRealm(), new GoogleRealm(),
						new ConfigurationRealm(cacheManager,
								getServletContext()), studentLdapRealm,
						staffLdapRealm));
		ModularRealmAuthenticator t = (ModularRealmAuthenticator) securityManager
				.getAuthenticator();
		t.setAuthenticationStrategy(new FirstSuccessfulStrategy());

		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer(
				Arrays.asList(new UserDataAuthorizer(cacheManager,
						getServletContext()), new ConfigurationRealm(
						cacheManager, getServletContext())));
		securityManager.setAuthorizer(authorizer);

		securityManager.setCacheManager(cacheManager);

		return securityManager;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
		init();
	}

	/**
	 * configures the {@link JndiLdapRealm} of Massey.
	 * 
	 * @param realm
	 * @param ou
	 */
	private void initLdapRealm(JndiLdapRealm realm, String ou) {
		realm.setUserDnTemplate("CN={0},OU=Palmerston North,OU=" + ou
				+ ",OU=Clients,DC=massey,DC=ac,DC=nz");
		((JndiLdapContextFactory) realm.getContextFactory())
				.setUrl("ldap://tur-ldap.massey.ac.nz");
	}

	/**
	 * Creates the {@link FilterChainResolver} which secures all urls.
	 * 
	 * @return
	 */
	private FilterChainResolver createFilterChainResolver() {
		PassThruAuthenticationFilter authc = new PassThruAuthenticationFilter();
		authc.setLoginUrl("/login");
		LogoutFilter logout = new LogoutFilter();

		AnonymousFilter anonymous = new AnonymousFilter();

		// create filter chain manager
		FilterChainManager fcMan = new DefaultFilterChainManager();
		fcMan.addFilter("anon", anonymous);
		fcMan.addFilter("authc", authc);
		fcMan.addFilter("logout", logout);

		// create chains
		fcMan.createChain("/logout", "logout");
		fcMan.createChain("/index.jsp", "anon");
		fcMan.createChain("/404.jsp", "anon");
		fcMan.createChain("/login", "authc");
		fcMan.createChain("/login.jsp", "authc");
		fcMan.createChain("/admin/**", "authc, roles[teacher]");
		fcMan.createChain("/userDetails/**", "authc, roles[teacher]");
		fcMan.createChain("/users", "authc, roles[teacher]");
		fcMan.createChain("/systemstate", "anon");
		fcMan.createChain("/shareBot", "authc");
		fcMan.createChain("/getSharedBot", "authc");
		fcMan.createChain("/editor.jsp", "authc");
		fcMan.createChain("/test.jsp", "authc");

		// create resolver
		PathMatchingFilterChainResolver resolver = new PathMatchingFilterChainResolver();
		resolver.setFilterChainManager(fcMan);

		return resolver;
	}

}
