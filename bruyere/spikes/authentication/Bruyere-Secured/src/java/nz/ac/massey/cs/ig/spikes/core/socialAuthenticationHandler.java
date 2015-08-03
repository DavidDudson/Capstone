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
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


//simple auth handler to create a social subject and create a session for that subject
public class socialAuthenticationHandler {
    
    final static Logger log = Logger.getLogger(socialAuthenticationHandler.class);
    
    public socialAuthenticationHandler(){
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
        log.info("Social auth handler instansiated");
    }
    
    public boolean performAuthentication(){
  
        UsernamePasswordToken token = new UsernamePasswordToken( "socialLogin", "socialPassword");

        Subject socialSubject = SecurityUtils.getSubject();

        Session session = socialSubject.getSession();

        try{
            socialSubject.login(token);


        }catch ( UnknownAccountException uae ) {
                log.info("login failed err 1  " + uae);
                return false;
        } catch ( IncorrectCredentialsException ice ) {
                log.info("login failed err 2  " + ice);
                return false;
        } catch ( LockedAccountException lae ) { 
                log.info("login failed err 3  " + lae);
                return false;
        } catch ( ExcessiveAttemptsException eae ) { 
                log.info("login failed err 4  " + eae);
                return false;
        } catch ( AuthenticationException ae ) {
                log.info("login failed err 5  " + ae);
                return false;
        }
        
        if (socialSubject.isAuthenticated()){
            boolean userAuthenticated = true;
            return userAuthenticated;
        }
        else{
            socialSubject.logout();
            boolean userAuthenticated = false;
            return userAuthenticated;
        }   
    }
    
}
