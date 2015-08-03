/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.core;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Marcel Kroll
 * Simple class to check if a user has role admin, if they do they are passed on to the requested page
 * other wise they are sent to the access denied page
 */
public class adminCheckFilter implements Filter{
    
    final static Logger log = Logger.getLogger(adminCheckFilter.class);
    
    public adminCheckFilter(){
        PropertyConfigurator.configure(getClass().getClassLoader().getResource("conf/log4j.properties"));
        log.info("Filter instansiated");
    }


    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        
        log.info(SecurityUtils.getSubject().getSession().getAttribute("role").toString());
        
        String userRole = SecurityUtils.getSubject().getSession().getAttribute("role").toString();
        
        if (userRole.equals("admin")){
            
            String requestedPage = ((HttpServletRequest)sr).getServletPath().toString();
           
            log.info("Page requested: " + requestedPage + " attempting to reroute");
            
            RequestDispatcher dispatcher = sr.getRequestDispatcher(requestedPage);
            dispatcher.include(sr,sr1);
            
        }
        
        else{
            log.info("User is not authorized to view this page");
            sr.getRequestDispatcher("accessdenied.jsp").forward(sr, sr1);
            
        }
              
    }
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
