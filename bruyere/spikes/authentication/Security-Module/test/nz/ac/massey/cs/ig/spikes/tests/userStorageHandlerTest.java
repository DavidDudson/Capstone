/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.tests;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nz.ac.massey.cs.ig.spikes.core.userStorageHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class userStorageHandlerTest {
    
    final static Logger log = Logger.getLogger(userStorageHandlerTest.class);
   
    public userStorageHandlerTest(){
       PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
    }
    
    @Before
    public void setUp() {
        userStorageHandler instance = new userStorageHandler();
        try{
            PrintWriter writer = new PrintWriter("userStorage.txt");
            writer.print("");
            writer.close();   
        }
        catch(FileNotFoundException e){
            fail("There was an issue clearing the file");
        }
        List<String> user1 = new ArrayList<>(Arrays.asList("marcel","student"));
        List<String> user2 = new ArrayList<>(Arrays.asList("jens","admin"));
        List<String> user3 = new ArrayList<>(Arrays.asList("smith","student"));
        
        instance.addUser(user1);
        instance.addUser(user2);
        instance.addUser(user3); 
    }
    
    @After
    public void tearDown() {
    }

    //first we check all the user data within user storage to make sure that it is correct
    @Test
    public void testGetAllUserData() {
        log.info("Test get all User Data from Storage");
        userStorageHandler instance = new userStorageHandler();
        List<String> user1 = new ArrayList<>(Arrays.asList("marcel","student"));
        List<String> user2 = new ArrayList<>(Arrays.asList("jens","admin"));
        List<String> user3 = new ArrayList<>(Arrays.asList("smith","student"));
        List<List<String>> expResult = new ArrayList<>(Arrays.asList(user1,user2,user3));
        List<List<String>> result = instance.getAllUserData();
        assertEquals(expResult, result);
    }
    
    //next check a specific users data
    @Test
    public void testGetSpecificUserData() {
        log.info("Test get a Specific Users Data from Storage");
        String userName = "marcel";
        userStorageHandler instance = new userStorageHandler();
        List<String> expResult = new ArrayList<>(Arrays.asList("marcel","student"));
        List<String> result = instance.getSpecificUserData(userName);
        assertEquals(expResult, result);
    }
    
    //test adding a user to the storage, then check if it worked out
    @Test
    public void testAddUser() {
        log.info("Test Adding of User to Storage");
        List<String> userProperties = new ArrayList<>(Arrays.asList("baxter","student"));
        userStorageHandler instance = new userStorageHandler();
        instance.addUser(userProperties);
        
        boolean result = instance.checkIfUserExists("baxter");
        String result2 = instance.getUserRole("baxter");
        boolean expResult = true;
        String expResult2 = "student";
        
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }
    
    //test to check if users exist within the storage
    @Test
    public void testCheckIfUserExists() {
        log.info("Test to Check Method that Checks if a User Exists Within Storage");
        String userName1 = "marcel";
        String userName2 = "jens";
        String userName3 = "baxter";
        
        userStorageHandler instance = new userStorageHandler();
        
        boolean result1 = instance.checkIfUserExists(userName1);
        boolean result2 = instance.checkIfUserExists(userName2);
        boolean result3 = instance.checkIfUserExists(userName3);
        
        boolean expResult = true;
        boolean expResult2 = false;
        
        assertEquals(expResult, result1);
        assertEquals(expResult, result2);
        assertEquals(expResult2, result3);
    }
    
    //now we test getting a users role
    @Test
    public void testGetUserRole() {
        log.info("Test get User Role");
        String userName1 = "marcel";
        String userName2 = "jens";
        
        userStorageHandler instance = new userStorageHandler();
        
        String expResult1 = "student";
        String expResult2 = "admin";
        
        String result1 = instance.getUserRole(userName1);
        String result2 = instance.getUserRole(userName2);
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }
    
    //now we set a user role and then check if it worked correctly
    @Test
    public void testSetUserRole() {
        log.info("setUserRole");
        String userName = "marcel";
        String oldRole = "student";
        String newRole = "admin";
        
        userStorageHandler instance = new userStorageHandler();
        
        String expResult = "roleSet";
        String expResult2 = "admin";
        
        String result = instance.setUserRole(userName, oldRole, newRole);
        String result2 = instance.getUserRole("marcel");
        
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

}
