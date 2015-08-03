/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Marcel Kroll
 */
public class adminHandler implements Filter{

    final static Logger log = Logger.getLogger(adminHandler.class);
    
    public adminHandler(){        
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
        log.trace("Admin handler instansiated");
    }
    
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        String requestMethod = ((HttpServletRequest)sr).getParameter("requestType");
        String requestValue = ((HttpServletRequest)sr).getParameter("requestValue");
        
        if (requestMethod.equals("getWhiteList")){
            
            whiteListHandler handler = new whiteListHandler();
            
            String whiteListData = handler.getWhiteListString();  
            
            JSONObject obj = new JSONObject();
            
            obj.put("requestType", "whiteListString");
            obj.put("whiteListString", whiteListData);
            
            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
            
        }
        
        if (requestMethod.equals("addUser")){
            
            whiteListHandler handler = new whiteListHandler();
            
            String addUserResult = handler.addUserToWhitelist(requestValue);
            
            JSONObject obj = new JSONObject();
            obj.put("requestType", "addUser");
            obj.put("addUser", addUserResult);

            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
            
        }
        
        if (requestMethod.equals("removeUser")){
            
            whiteListHandler handler = new whiteListHandler();
            
            String removeUserResult = handler.removeUserFromWhiteList(requestValue);
            
            JSONObject obj = new JSONObject();
            obj.put("requestType", "removeUser");
            obj.put("removeUser", removeUserResult);

            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
            
        }
        
        if (requestMethod.equals("getUserRole")){
            log.info("client requesting user role information");
            
            userStorageHandler storage = new userStorageHandler();
            
            String userRole = storage.getUserRole(requestValue);
            
            JSONObject obj = new JSONObject();
            obj.put("requestType", "getUserRole");
            obj.put("getUserRole", userRole);

            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
        }
        
        if (requestMethod.equals("setUserRole")){
            log.info("client requesting user role change");
            
            userStorageHandler storage = new userStorageHandler();
            
            List<String> request = Arrays.asList(requestValue.split(","));
            
            log.info(request.toString());
            
            String result = storage.setUserRole(request.get(0), request.get(1), request.get(2));
            
            JSONObject obj = new JSONObject();
            obj.put("requestType", "setUserRole");
            obj.put("setUserRole", result);

            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
            
            
        }
        
        if (requestMethod.equals("clearWhiteList")){
            
            whiteListHandler handler = new whiteListHandler();
            
            String result = handler.clearWhiteList();
            
            JSONObject obj = new JSONObject();
            obj.put("requestType", "clearWhiteList");
            obj.put("clearWhiteList", result);

            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
        }
        
        if (requestMethod.equals("addMultiple")){
            
            whiteListHandler handler = new whiteListHandler();
            
            List<String> usersToAdd = Arrays.asList(requestValue.split("\n"));
            
            List<String> result = handler.addMultipleToWhiteList(usersToAdd);
            
            JSONObject obj = new JSONObject();
            
            if (result.get(0).equals("success")){
                 obj.put("requestType", "addMultiple");
                 obj.put("addMultiple", "success");
                 obj.put("addMultipleMeta", result.get(1));
            }
            else if (result.get(0).equals("allExist")){
                obj.put("requestType", "addMultiple");
                obj.put("addMultiple", "allExist");
            }
            else if (result.get(0).equals("partialSuccess")){
                 obj.put("requestType", "addMultiple");
                 StringBuilder builder = new StringBuilder();
                 builder.append(result.get(1));
                 builder.append(";");
                 builder.append(result.get(2));
                 builder.append(";");
                 builder.append(result.get(3));
                 obj.put("addMultiple", "alreadyInWhiteList");
                 obj.put("addMultipleMeta", builder.toString());
                 
            }
            else{
                obj.put("requestType", "addMultiple");
                obj.put("addMultiple", "error");
            }
            
            PrintWriter out = sr1.getWriter();
            out.print(obj);
            out.close();
            
        }
        
        
    }
    
    

    @Override
    public void init(FilterConfig fc) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
