/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LDAPAuthenticationHandler {

	final static Logger log = Logger.getLogger(LDAPAuthenticationHandler.class);

	
	private boolean userAuthenticated = false;
	
	public LDAPAuthenticationHandler(){
            PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
            log.info("LDAP authentication handler instansiated");      
	}
	
	public boolean performAuthentication (String userName, String password){		
		log.info("Init LDAP auth module");	
		
		//used to determine the path to the shiroLDAP.ini file
		String path = LDAPAuthenticationHandler.class.getResource("shiroLDAP.ini").toString();
                
                //if we are running windows %20 will break the program so we have to replace it with a space
                if (path.contains("%20")){
                    path = path.replace("%20", " ");
                }
		
		//initialize a factory security manager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(path);
	
		log.info("Init LDAP ini configuration");
		
		//initialize a security manager
		SecurityManager securityManager = factory.getInstance();
		
		
		log.info("Init LDAP security manager");
			
		//set the security manager
		SecurityUtils.setSecurityManager(securityManager);
		
		//get the subject or current user from the security utils
		//this is useful as we can use this in a similar way to transfer data through the created session
		//to other modules within the program.
		Subject LDAPUser = SecurityUtils.getSubject();
		
		log.info("Shiro LDAP auth module initialized");
		log.info("Attempting to authenticate user through LDAP");
		
		//create a user name password token
		log.info("Generating token information");
		log.info(userName);
		log.info(password);
		UsernamePasswordToken token = new UsernamePasswordToken( userName, password);
					
		try{
			//attempt to login with the provided token using the realm defined in shiroLDAP.ini
			LDAPUser.login(token);
			
			/*
				This commented block represents session testing code, used to pass variables through the classes in
				this program via the users session
				
				get the users session
                                Session session = currentUser.getSession();
			
				we can then set variables on this session to be obtained later
				session.setAttribute("user", token.getUsername());
				session.setAttribute("userRole", "student");
			*/
                        
                       
			log.info("Authentication steps complete");
			log.info("Authenticated with LDAP");
			//log.info("provided token information: " + token.getUsername().toString() + " " + token.getPassword());
				
		//in the case of authentication failure we throw the relevant exception
		}catch ( UnknownAccountException uae ) {
			log.info("login failed err 1  " + uae);
			return userAuthenticated;
		} catch ( IncorrectCredentialsException ice ) {
			log.info("login failed err 2  " + ice);
			return userAuthenticated;
		} catch ( LockedAccountException lae ) { 
			log.info("login failed err 3  " + lae);
			return userAuthenticated;
		} catch ( ExcessiveAttemptsException eae ) { 
			log.info("login failed err 4  " + eae);
			return userAuthenticated;
		} catch ( AuthenticationException ae ) {
			log.info("login failed err 5  " + ae);
			return userAuthenticated;
		}
		
		//if the current user was authenticated set user authenticated to true and return
		if(LDAPUser.isAuthenticated()) {
			userAuthenticated = true;
			return userAuthenticated;
		}
		
		//if we failed return user authenticated which is set to false
		else{
                        LDAPUser.logout();
			return userAuthenticated;
		}
		
	}
}
