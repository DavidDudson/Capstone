/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//Class for handling user storage operations
public class userStorageHandler {
	
    //logger for logging information
    final static Logger log = Logger.getLogger(userStorageHandler.class);
    
    //various safety variables to ensure correct usage of the methods
    private int requiredAmountOfUserData = 2;
    
    //defined path for storage file, and the name of that file
    private Path storagePath = Paths.get("userStorage.txt");
    private String path = "userStorage.txt";

    //on instansiation we check if the file exists, if not we create it
    public userStorageHandler(){
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));

        if (Files.exists(storagePath)){
            log.info("The storage file exists");
        }

        if (Files.notExists(storagePath)){
            log.info("Storage file missing, creating...");
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("userStorage.txt","UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            
            writer.close();
            
            log.info("File created");

        }

    }
    
    /*
        This method request parse the user storage for all user data that is stored within the file
        The return type is  : List<List<String>>
        Example return info : ( ("123456","student",...) , (...) )
    
    */
    public List<List<String>> getAllUserData(){
        try{
            FileReader reader = new FileReader(path);
            BufferedReader textReader = new BufferedReader(reader);

            List<List<String>>  userData = new  ArrayList<>();
            String line;

            while((line = textReader.readLine()) != null){
                List<String> userDataToAdd = new ArrayList<>(Arrays.asList(line.split(",")));
                userData.add(userDataToAdd) ;
            }

            textReader.close();

            return userData;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /*
        This method gets a specific users data
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : List<String>
        Example Return info   : ("123456","student",...)
    */
    public List<String> getSpecificUserData(String userName){
    	//requires that the user is checked to exist in storage before pulling for information
    	//may throw a null pointer exception if the user isn't in the storage
    	for(List<String> userData: this.getAllUserData()){
    		if (userData.get(0).equals(userName)){
    			return userData;
    		}
    		
    	}
    	
    	return null;
    }
    
    /*
        This method adds a user to the storage file
        The Parameter type is : List<String>
        Exmaple Parameter     : ("123456","student",...)
    */
    public void addUser(List<String> userProperties){

        //if the input doesnt contain at least a certain amount of information an error is thrown
        if (userProperties.size() < requiredAmountOfUserData){
            throw new IllegalArgumentException();
        }
        
        //other wise we append the user data into the file
        else{
            StringBuilder newUser = new StringBuilder();
            for (String userVariable : userProperties){
                newUser.append(userVariable);
                newUser.append(",");
            }
            newUser.append("\n");
            
            try{
                Writer storage = new BufferedWriter(new FileWriter(path, true));
                storage.append(newUser);
                storage.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    /*
        This method checks if a user exists within the storage
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : boolean
        Example Return info   : true | false
    */
    public boolean checkIfUserExists(String userName){

        List<List<String>> listOfUsers = this.getAllUserData();

        for (List<String> singleUserData : listOfUsers){

            if (singleUserData.get(0).equals(userName)){
                return true;
            }

        }
        return false;
    }

    /*
        This method gets a specific users role
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : String
        Example Return info   : "student" | "admin"
    */
    public String getUserRole(String userName){
        
        //first we check if the user actually exists within the storage and retrieve their role information
        if (this.checkIfUserExists(userName)){
            List<String> userData = this.getSpecificUserData(userName);
            
            String userRole = userData.get(1);
            return userRole;
        }
        
        //if not we are obivously allowed to be logged in and so we simply add a new user to the storage and give the default role of student
        else{
            List<String> userProperties = new ArrayList<>();
            userProperties.add(userName);
            userProperties.add("student");
            
            this.addUser(userProperties);
            
            String userRole = "student";
            return userRole;
            
        }
    }
    
    /*
        This method is a helper function for setting a users role
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : Boolean
        Example Return info   : true | false
    */
    private boolean setUserHelper(String searchParam){
        
            boolean resultOfSearching = false;
            
            try{
            File userStorageFile = new File(path);
            
            File tempFile = new File(userStorageFile.getAbsolutePath() + ".tmp");
            
            BufferedReader textReader = new BufferedReader(new FileReader(path));
            
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            
            String line = null;
            
            while ((line = textReader.readLine()) != null){
                
                if (!line.contains(searchParam)){
                    
                    pw.println(line);
                    pw.flush();
                    
                }
                else{
                    
                    resultOfSearching = true;
                }
                    
            }
            
            pw.close();
            textReader.close();

            userStorageFile.delete();
            tempFile.renameTo(userStorageFile);

            return resultOfSearching;
            
            }
            catch(IOException e){
                return resultOfSearching;
            }
    }
    
     /*
        This method is used to set the role of an existing user
        The Parameter type is : String, String, String
        Exmaple Parameter     : "123456", "student", "admin"
        The return type is    : String
        Example Return info   : "roleset" | "error"
    */
    public String setUserRole(String userName, String oldRole, String newRole){
        
        String searchParam = userName + "," + oldRole;

        boolean setUserResult = this.setUserHelper(searchParam);
        
        if (setUserResult){
            List<String> userProperties = new ArrayList<>();
            userProperties.add(userName);
            userProperties.add(newRole);
          
            this.addUser(userProperties);
            
            String result = "roleSet";
            return result;
        }
        else{
            String result = "error";
            return result;
        }
    }

}
