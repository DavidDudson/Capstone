/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nz.ac.massey.cs.ig.spikes.core.whiteListHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class whiteListHandlerTest {
    
    final static Logger log = Logger.getLogger(whiteListHandlerTest.class);
    
    public whiteListHandlerTest() {
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
    }

    @Before
    public void setUp() {
        whiteListHandler instance = new whiteListHandler();
        instance.clearWhiteList();
        instance.addUserToWhitelist("Jens");
        instance.addUserToWhitelist("Marcel");
        instance.addUserToWhitelist("Smith");
    }
    
    @After
    public void tearDown() {
    }
    
    //first test clears the white list and checks its empty with another method
    @Test
    public void testClearWhiteList() {
        log.info("Test Clear White List");
        whiteListHandler instance = new whiteListHandler();
        String expResult = "cleared";
        String expResult2 = "0";
        String result = instance.clearWhiteList();
        String result2 = instance.getWhiteListString();
        assertEquals(expResult, result);       
        assertEquals(expResult2, result2);
    }

    //next we add a single users to the white list then parse it and check if its correct
    @Test
    public void testAddUserToWhitelist() {
        log.info("Test Add User to White List");
        String userName = "Baxter";
        whiteListHandler instance = new whiteListHandler();
        String expResult = "userAdded";
        boolean expResult2 = true;
        String result = instance.addUserToWhitelist(userName);
        boolean result2 = instance.checkIfUserWhitelisted("Baxter");
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }
    
    //next we will try adding multiple users to the white list at once then check if its correct
    @Test
    public void testAddMultipleToWhiteList() {
        log.info("Test Add Multiple Users to White List");
        List<String> multipleData = new ArrayList<>(Arrays.asList("John","Peter","Sam"));
        whiteListHandler instance = new whiteListHandler();
        List<String> expResult = new ArrayList<>(Arrays.asList("success","3"));
        List<String> result = instance.addMultipleToWhiteList(multipleData);
        List<String> expResult2 = new ArrayList<>(Arrays.asList("jens","marcel","smith","john","peter","sam"));
        List<String> result2 = instance.getWhiteListData();
        String test = instance.getWhiteListString();
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        
    }
    
    //now we will try to remove a user from the middle of the white list and then check if it was removed correctly
    @Test
    public void testRemoveUserFromWhiteList() {
        log.info("Test Remove User from White List");
        String userName = "marcel";
        whiteListHandler instance = new whiteListHandler();
        String expResult = "userRemoved";
        String result = instance.removeUserFromWhiteList(userName);
        List<String> result2 = instance.getWhiteListData();
        List<String> expResult2 = new ArrayList<>(Arrays.asList("jens","smith"));
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }
    
    //now we test to see if we are correctly checking if users are found in the white list correctly
    @Test
    public void testCheckIfUserWhitelisted() {
        log.info("Test Check if User is White Listed");
        String userName = "Jens";
        String userName2 = "Marcel";
        String userName3 = "Smith";
        String userName4 = "Baxter";
        whiteListHandler instance = new whiteListHandler();
        boolean expResult = true;
        boolean expResult2 = false;
        
        boolean result1 = instance.checkIfUserWhitelisted(userName);
        boolean result2 = instance.checkIfUserWhitelisted(userName2);
        boolean result3 = instance.checkIfUserWhitelisted(userName3);
        boolean result4 = instance.checkIfUserWhitelisted(userName4);
        
        assertEquals(expResult, result1);
        assertEquals(expResult, result2);
        assertEquals(expResult, result3);
        assertEquals(expResult2, result4);
    }

    //now all we have left is to check the 2 parsing strings
    //although these are already proven correctly we will test them anyway to ensure completness
    @Test
    public void testGetWhiteListString() {
        log.info("Test get White List String");
        whiteListHandler instance = new whiteListHandler();
        String expResult = "jens,marcel,smith,-970953827";
        String result = instance.getWhiteListString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetWhiteListData() {
        log.info("Test get White List Data");
        whiteListHandler instance = new whiteListHandler();
        List<String> expResult = new ArrayList<>(Arrays.asList("jens","marcel","smith"));
        List<String> result = instance.getWhiteListData();
        assertEquals(expResult, result);
    }

}
