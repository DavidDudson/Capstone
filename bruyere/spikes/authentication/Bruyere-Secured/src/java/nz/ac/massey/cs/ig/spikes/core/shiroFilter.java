/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.*;
import org.apache.shiro.web.util.WebUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class shiroFilter  extends FormAuthenticationFilter{
	
        //logger used for logging
	final static Logger log = Logger.getLogger(shiroFilter.class);
        
        //variables for various functions of the auth module, these are set using the shiro.ini file in web-inf
        private boolean whiteListEnabled = false;
        private String adminUserName = "admin";
        private String adminPassword = "admin";
        private String unauthorizedMessage = "Unauthorized";
        private String authFailureMessage = "Authentication Failure";
        private String fallBackUrl = "../index.html";
        private static boolean enableLDAP = true;
        private static boolean enableSocial = false;

        public static boolean isEnableLDAP() {return enableLDAP;}

        public static boolean isEnableSocial() {return enableSocial;}

        //setters for the preceeding variables so they can be set with the shiro.ini file
        public void setEnableLDAP(boolean enableLDAP) {this.enableLDAP = enableLDAP;}

        public void setEnableSocial(boolean enableSocial) {this.enableSocial = enableSocial;}

        public void setfallBackUrl(String fallBackUrl) {this.fallBackUrl = fallBackUrl;}
        
        public void setUnauthorizedMessage(String unauthorizedMessage) {this.unauthorizedMessage = unauthorizedMessage;}

        public void setAuthFailureMessage(String authFailureMessage) {this.authFailureMessage = authFailureMessage;}
        
        public void setAdminUserName(String adminUserName) {this.adminUserName = adminUserName;}

        public void setAdminPassword(String adminPassword) {this.adminPassword = adminPassword;}
               
        public void setWhiteListEnabled(boolean whiteListEnabled) {this.whiteListEnabled = whiteListEnabled;}
        
	public shiroFilter(){
            PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
            log.info("Shiro filter instansiated and called apon");
        }
	
        //The main method that will handle any incoming login requests
        @Override
	protected boolean executeLogin(ServletRequest arg0, ServletResponse arg1) throws Exception {
                
                
            
                //auth actions to perform if the request is a social login request
                if (this.getUsername(arg0).equals("socialLoginRequest")){
                    
                    log.info("Receiving social systems login request");
                    
                    //use a json parser to parse the authentication token out
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(this.getPassword(arg0));
                    String socialID = json.get("email").toString();
                    log.info(json.toString());
                    StringBuilder builder = new StringBuilder();
                    builder.append(json.get("first_name").toString());
                    builder.append(" ");
                    builder.append(json.get("last_name").toString());
                            
                    String socialName = builder.toString();
                    String socialPicture = json.get("picture").toString();
                   
                    //instansiate a social authentication handler and log the user in
                    socialAuthenticationHandler handler = new socialAuthenticationHandler();
                    boolean result = handler.performAuthentication();
                    
                    /*
                        if the user is logged in we then check if the white list is enabled and if so if the user is white listed
                        if all checks out we return true and redirect them to the page they requested, in any other instance the subject is logged out
                        and shown the login page with an error message
                    */
                    if(result){
                    
                    if (whiteListEnabled){
                        log.info("white list enabled");
                        
                        whiteListHandler whitelist = new whiteListHandler();

                        result = whitelist.checkIfUserWhitelisted(socialID);
                        
                        if (result){
                            log.info("user is on the whitelist");
                            setUserSessionData(socialID,socialName,socialPicture);
                            WebUtils.redirectToSavedRequest(arg0, arg1, fallBackUrl);
                            return result;
                        }
                        
                        else{
                            log.info("user is not on the white list");    
                            Subject socialUser = SecurityUtils.getSubject();
                            socialUser.logout();
                            log.info("user has been logged out of the system");
                            arg0.setAttribute("authMessage", this.unauthorizedMessage);
                            arg0.getRequestDispatcher("login.jsp").forward(arg0, arg1);
                            return result;
                        }
                        
                    }
                    else{
                        //if the white list is disabled we can let everyone log in
                        setUserSessionData(socialID,socialName,socialPicture);
                        WebUtils.redirectToSavedRequest(arg0, arg1, fallBackUrl);
                        return result;
                        
                    }
                    }
                    
                    else{
                        Subject socialUser = SecurityUtils.getSubject();
                        socialUser.logout();
                        log.info("authentication failure, user logged out of system");
                        arg0.setAttribute("authMessage", this.authFailureMessage);
                        arg0.getRequestDispatcher("login.jsp").forward(arg0, arg1);
                        return result;
                    }
                    
                    
                }
                
                          
                //in the case that admin credentials are provided we can access the admin tools without being white listed
                else if((this.getUsername(arg0).equals(adminUserName)) && (this.getPassword(arg0).equals(adminPassword))){
                    
                    log.info("Admin user detected");
                    
                    //simply create an admin auth handler to create a session for the subject
                    adminAuthenticationHandler adminHandler = new adminAuthenticationHandler();
                    boolean result = adminHandler.performAuthentication();
                    
                    if (result){
                        WebUtils.redirectToSavedRequest(arg0, arg1, fallBackUrl);
                        return true;
                    }
                    else{
                        return false;
                    }                  
                }
                
                //other wise it is assumed we are trying to access with ldap credentials so we perform that request
                else{
		
                    //instansiate a new ldap auth handler and perform authentication with provided credentials
                    LDAPAuthenticationHandler handler = new LDAPAuthenticationHandler();
                    boolean result = handler.performAuthentication(this.getUsername(arg0),this.getPassword(arg0));

                                        
                    /*
                        if the user is logged in we then check if the white list is enabled and if so if the user is white listed
                        if all checks out we return true and redirect them to the page they requested, in any other instance the subject is logged out
                        and shown the login page with an error message
                    */
                    if (result){

                        if (whiteListEnabled){
                            log.info("white list enabled");

                            whiteListHandler whitelist = new whiteListHandler();

                            result = whitelist.checkIfUserWhitelisted(this.getUsername(arg0));

                            if (result){
                                log.info("user is on the white list");
                                setUserSessionData(this.getUsername(arg0),this.getUsername(arg0),"");
                                WebUtils.redirectToSavedRequest(arg0, arg1, fallBackUrl);
                                return result;
                            }
                            else{
                                log.info("user is not on the white list");    
                                Subject currentUser = SecurityUtils.getSubject();
                                currentUser.logout();
                                log.info("user has been logged out of the system");
                                arg0.setAttribute("authMessage", this.unauthorizedMessage);
                                arg0.getRequestDispatcher("login.jsp").forward(arg0, arg1);
                                return result;
                            }

                        }

                        else{
                            //if the white list is disabled we can let everyone log in
                            setUserSessionData(this.getUsername(arg0),this.getUsername(arg0),"");
                            WebUtils.redirectToSavedRequest(arg0, arg1, fallBackUrl);
                            return result;
                        }


                    }
                    else{
                            Subject currentUser = SecurityUtils.getSubject();
                            currentUser.logout();
                            log.info("authentication failure, user logged out of system");
                            arg0.setAttribute("authMessage", this.authFailureMessage);
                            arg0.getRequestDispatcher("login.jsp").forward(arg0, arg1);      
                            return result;
                    }
                }
                
	}
        
        /*
            Method that sets session meta data for the subject
            Currently sets:
                "role" which is either admin or student
                "userName" which is the primary username that will be displayed on the site
                "userPicture" which is the url to the subjects profile pic
        */
        private void setUserSessionData(String userName, String shownUserName, String pictureURL){
            //first we need to set the role data so we call on out user storage method
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            
            userStorageOperation(userName, session);
            
            session.setAttribute("userName", shownUserName);
            session.setAttribute("userPicture", pictureURL);
            
        }
       
        
        //method to perform a check if a user is already within the user storage, and if so we load their role into their session
        //other wise we create a new entry in the storage for them and assign them the role of student
        private void userStorageOperation(String userName, Session session){
            userStorageHandler storage = new userStorageHandler();
            
            if (storage.checkIfUserExists(userName)){
                log.info("User exists in the storage, loading role");
                List<String> userData = storage.getSpecificUserData(userName);
                session.setAttribute("role", userData.get(1));
            }
                            
            else {
                log.info("User not found in storage, adding now");

                List<String> user = new ArrayList<String>();
                user.add(userName);
                user.add("student");

                storage.addUser(user);

                session.setAttribute("role", "student");
                
            }
        }
        
        @Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		String message = ae.getMessage();
		request.setAttribute(getFailureKeyAttribute(), message);
	}
        
}
