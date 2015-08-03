/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.tests;

import nz.ac.massey.cs.ig.spikes.core.LDAPAuthenticationHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcel Kroll
 */
public class LDAPAuthenticationHandlerTest {
    
    public LDAPAuthenticationHandlerTest() {
    }
    
    //make sure to clear the subject just in case this is a problem
    @After
    public void tearDown() {
        Subject LDAPUser = SecurityUtils.getSubject();
        LDAPUser.logout();
    }
    
    //This method will test the LDAP authention class, however to run this test you will need to running a
    //testing development server, with the user testingUser with the password testingPassword
    @Test
    public void testPerformAuthentication() {
 
        String userName = "testingUser";
        String password = "testingPassword";
 
        LDAPAuthenticationHandler instance = new LDAPAuthenticationHandler();
        boolean expResult = true;
        
        boolean result = instance.performAuthentication(userName, password);
        assertEquals(expResult, result);
    }
    
    //this test will test if the authentcation returns correctly after attempting to authenticate
    //with incorrect credentials, this does not require a user defeined on the server as it is aiming to fail
    @Test
    public void testPerformAuthenticationIncorrectCredentials(){
        String userName = "testingUser";
        String password = "incorrectPassword";
 
        LDAPAuthenticationHandler instance = new LDAPAuthenticationHandler();
        boolean expResult = false;
        
        boolean result = instance.performAuthentication(userName, password);
        assertEquals(expResult, result);
    }
    
}
