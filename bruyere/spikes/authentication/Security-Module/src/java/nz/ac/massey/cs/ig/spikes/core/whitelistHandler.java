/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//class for handling white list operations
public class whiteListHandler {
    
    //defining the logger
    final static Logger log = Logger.getLogger(whiteListHandler.class);
    
    //variables for the file path and file name
    private Path whitelistPath = Paths.get("whitelist.txt");
    private String path = "whitelist.txt";
    
    //on instansiation we check if the file exists, if it doesnt we create it
    public whiteListHandler(){ 
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
        
        if (Files.exists(whitelistPath)){
            log.info("The storage file exists");
        }

        if (Files.notExists(whitelistPath)){
            log.info("whitelist file missing, creating...");
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("whitelist.txt","UTF-8");
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
        This method gets the white list data as a string with a hash code on the end
        The return type is    : String
        Example Return info   : "123456,234567,345678,...,hashcode"
    */
    public String getWhiteListString(){
        
        try{
           StringBuilder builder = new StringBuilder();
           FileReader reader = new FileReader(path);
           BufferedReader textReader = new BufferedReader(reader);
           String line;
            
           while((line = textReader.readLine()) != null){
                builder.append(line);
                builder.append(",");
           }
           builder.append(builder.toString().hashCode());
           
           reader.close();
           textReader.close();
            
           return builder.toString();
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
        This method gets the white list data as a list
        The return type is    : List<String>
        Example Return info   : ("123456","23456",...)
    */
    public List<String> getWhiteListData(){
        
        try{
            FileReader reader = new FileReader(path);
            BufferedReader textReader = new BufferedReader(reader);
            
            List<String> whitelistData = new ArrayList<>();
            String line;
            
            while((line = textReader.readLine()) != null){
                whitelistData.add(line);
            }
            
            reader.close();
            textReader.close();
            
            return whitelistData;
        
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
        This method checks if a requested user is whitelisted
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : boolean
        Example Return info   : true | false
    */
    public boolean checkIfUserWhitelisted (String userName){
           
            List<String> whitelistData = this.getWhiteListData();
            
            if (whitelistData == null){
                return false;
            }
            else{
                for( String user : whitelistData){
                    if (user.equals(userName.toLowerCase())){
                        return true;
                    }
                }
                
            }
            
            return false;
    }
    
    /*
        This is a private helper method for adding a user to the whitelist
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : boolean
        Example Return info   : true | false
    */
    private boolean addUser(String userName){
        if(userName != null && !userName.isEmpty()){
            StringBuilder newUser = new StringBuilder();
            newUser.append(userName.toLowerCase());
            newUser.append("\n");
            
            try{
                Writer storage = new BufferedWriter(new FileWriter(path, true));
                storage.append(newUser);
                storage.close();
                return true;
            }
            catch(IOException e){
                return false;
            }  
        }
        else{
            return false;
        }

    
    }
    
    /*
        This is a private helper method for removing a user from the whitelist
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : boolean
        Example Return info   : true | false
    */
    private boolean removeUser(String userName){
        if(userName != null && !userName.isEmpty()){
            
            try{
            File whiteListFile = new File(path);
            
            File tempFile = new File(whiteListFile.getAbsolutePath() + ".tmp");
            
            BufferedReader textReader = new BufferedReader(new FileReader(path));
            
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            
            String line = null;
            
            while ((line = textReader.readLine()) != null){
                
                if (!line.trim().equals(userName)){
                    pw.println(line);
                    pw.flush();
                    
                }                
            }
            
            pw.close();
            textReader.close();

            whiteListFile.delete();
            tempFile.renameTo(whiteListFile);

            return true;
            }
            catch(IOException e){
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    /*
        This method removes a specific user from the whitelist
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : String
        Example Return info   : "userNoLongerExists" | "userRemoved" | "error"
    */
    public String removeUserFromWhiteList(String userName){
         //first we check if the user is actually within the white list
         if (!this.checkIfUserWhitelisted(userName)){
            String result = "userNoLongerExists";
            return result;
         }
         //then we try to remove the user
         else{
             boolean removalResult = this.removeUser(userName);
             
             if (removalResult){
                 String result = "userRemoved";
                 return result;
             }
             else{
                 String result = "error";
                 return result;
             }
         }       
    }
    
    /*
        This method adds a user to the whitelist
        The Parameter type is : String
        Exmaple Parameter     : "123456"
        The return type is    : String
        Example Return info   : "userExists" | "userAdded" | "error"
    */
    public String addUserToWhitelist (String userName){ 
        
        if (this.checkIfUserWhitelisted(userName)){
            String result = "userExists";
            return result;
        }
        else{
            boolean additionResult = this.addUser(userName);
            
            if (additionResult){
                String result = "userAdded";
                return result;
            }
            else{
                String result = "error";
                return result;
            }
        }
    }
    
    /*
        This method adds multiple user to the whitelist at once
        The Parameter type is : List<String> 
        Exmaple Parameter     : ("123456","234567","345678",...)
        The return type is    : List<String>
        Example Return info   : ("allExist") | ("partialSuccess",succededUsers, failedUsers, failedAmount) | ("success") | ("error")
    */
    public List<String> addMultipleToWhiteList(List<String> multipleData){
        
        //variables for multiple outcomes
        List<String> result = new ArrayList<String>();
        Integer userAmount = 0;
        Integer succeededUsers = 0;
        Integer failedUsers = 0;
        StringBuilder failedResults = new StringBuilder();
        
        //first we trim the unneeded white space and spaces
        for (String user : multipleData){
            if (!this.checkIfWhiteSpace(user)){
                //we record how many users we have to add
                userAmount += 1;
                
                //we then check if the users are already white listed
                if (this.checkIfUserWhitelisted(user.trim())){
                    user = user.trim();
                    //if they are we record the username and that one user has failed
                    failedUsers += 1;
                    failedResults.append(user);
                    failedResults.append(", ");
                }
                //other wise we add the user to the whitelist
                else{
                    user = user.trim();
                    this.addUserToWhitelist(user);
                    succeededUsers += 1;
                }
            }
            else{
                //if white space character we ignore
            }
        }
        
        //if all the users failed it means that they all exist already within the whitelist
        if (failedUsers == userAmount){
            result.add("allExist");
            
            return result;
        }
        
        //if some of the users failed and other succeeded we note this and pass it on to the client so it can be displayed
        else if (failedUsers > 0){
            result.add("partialSuccess");
            result.add(succeededUsers.toString());
            result.add(failedUsers.toString());
            result.add(failedResults.toString());
            
            return result;
        }
        
        //if all users were added to the white list we return this
        else if (succeededUsers == userAmount){
            result.add("success");
            result.add(succeededUsers.toString());
          
            return result;
            
        }
        
        //in the event of an unforseen error we return error
        else{
            result.add("error");
            return result;
        }
        
    }
    
    /*
        This method clears all values from the whitelist
        The return type is    : String
        Example Return info   : "cleared" | "error"
    */
    public String clearWhiteList(){
        try{
            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();
            String result = "cleared";
            return result;
        }
        catch(FileNotFoundException e){
            String result = "error";
            return result;
        }
        
        
    }
    
    /*
        This is a private helper function for determining if a "user" is just whitespace characters
        The Parameter type is : String
        Exmaple Parameter     : "123456" | "        "
        The return type is    : boolean
        Example Return info   : true | false
    */
    private boolean checkIfWhiteSpace(String itemToCheck){
        if (itemToCheck.matches("\\s*+\\n*")){
            return true;
        }
        else{
            return false;
        }
    }
    
    
}
