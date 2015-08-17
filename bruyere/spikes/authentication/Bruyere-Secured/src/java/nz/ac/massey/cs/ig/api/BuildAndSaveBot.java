
package nz.ac.massey.cs.ig.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.cache.Cache;

import nz.ac.massey.cs.ig.core.game.BotMetaData;
import nz.ac.massey.cs.ig.core.game.Language;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.Services;
import static nz.ac.massey.cs.ig.api.Utils.*;
import nz.ac.massey.cs.ig.core.services.BuilderException;
import nz.ac.massey.cs.ig.core.services.defaults.compiler.SourceUtils;


// TODO: TEST THE NEW LANGUAGE BASED BUILDING
// TODO: Maybe switch around how languages work? Make it easier to add new languages?
/**
 * Compile a bot.
 * This is a post.
 * The body contains the source to be compiled and stored.
 * @author jens dietrich
 */
@WebServlet(name = "BuildAndSaveBot", urlPatterns = {"/build/*"})
public class BuildAndSaveBot extends HttpServlet {

	private static final long serialVersionUID = 5938620498399810346L;

	@SuppressWarnings({ "unchecked" })
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Services services = ServiceFactory.getServices(this.getServletContext());
		String[] requestPath = request.getPathInfo().split("/"); 
        String language = requestPath[1];
        String botId = null;
        if (requestPath.length>2) botId = requestPath[2]; 
        String userId = null;
        String botName = null;
        if (botId!=null && botId.startsWith("/")){ botId = botId.substring(1); }
        boolean isNew = botId==null || botId.trim().length()==0;
        String src = null;
        String ctx = this.getServletContext().getContextPath();
        
        boolean validLanguage = false;
        for(String l : services.getLanguages()){
        	if(language.equals(l)){
        		validLanguage = true;
        		break;
        	}
        }
        if(!validLanguage){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid language parameter");
        }
        
        try {
            BufferedReader content = request.getReader();
            String line = null;
            boolean first = true;
            boolean second = true;
            StringBuilder s = new StringBuilder();
            while ((line=content.readLine())!=null) {
            	System.out.println(line);
            	if (first) {
            		if (line.startsWith("user:")) {
            			userId = line.substring(5).trim();
            			first = false;
            			second = true;
            		}
            		else {
            			handleException(this,response,"The first line of content must start with user: followed by the id of the user owning the bot" , null);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The first line of content must start with user: followed by the id of the user owning the bot");
            			return;
            		}
            	}
            	else if(second){
            		if (line.startsWith("botname:")) {
            			botName = line.substring(8).trim();
            			first = false;
            			second = false;
            		}
            		else{
            			botName = "NoNameBot";
                		s.append(line);
                		s.append("\n");
            		}
            	}
            	else {
            		s.append(line);
            		s.append("\n");
            	}
            }
            src = s.toString();    
        }
        catch (IOException x) {
            handleException(this,response,"Error decoding source code from request", x);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error decoding source code");
        }
        
        BuildProblemCollector issues = new BuildProblemCollector();
        String errorId = null;
        
        try {
            if (botId==null) botId = services.getUIDGenerator().nextUID();
            
//            // build
//        	if(language.equals(Language.JAVA)){
//        		services.getBuilder(language).build(botId, src, null, services.getStaticVerifier(), services.getDynamicVerifier(),issues);
//        	}
//        	else if(language.equals(Language.PYTHON)){
    		services.getBuilder(language).build(botId, src, services.getASTVerifier(), services.getStaticVerifier(), services.getDynamicVerifier(),issues);
//        	}
    
            // store
            String timestamp = Utils.getTimeStamp();
            Properties metadata = null;
            if (!isNew) {
                metadata = services.getStorage().getBotMetadata(botId);
            }
            else {
                metadata = new Properties();
                metadata.setProperty(BotMetaData.CREATED,timestamp);
            }
            
            // set new meta data
            metadata.setProperty(BotMetaData.OWNER, userId);
            metadata.setProperty(BotMetaData.LAST_MODIFIED,timestamp);
            metadata.setProperty(BotMetaData.ID,""+botId); 
            metadata.setProperty(BotMetaData.LANGUAGE,language); 
            
            if(language.equals(Language.JAVA)){
                metadata.setProperty(BotMetaData.NAME, SourceUtils.extractClassName(src));
                metadata.setProperty(BotMetaData.QNAME,SourceUtils.extractFullClassName(src));
            }
        	else if(language.equals(Language.PYTHON)){
        		metadata.setProperty(BotMetaData.NAME, botName);
				metadata.setProperty(BotMetaData.QNAME, botName);
        	}
            
            services.getStorage().saveBotSourceCode(botId,src);
            services.getStorage().saveBotMetadata(botId,metadata);
            services.getStorage().setOwner(userId, botId);
        } catch (BuilderException x) {
        	
        	Cache<String,BuildProblemCollector> buildProblems = (Cache<String,BuildProblemCollector>)this.getServletContext().getAttribute(ContextLifecycleManager.CNTXT_ATTR_BUILD_PROBLEMS);
            errorId = services.getUIDGenerator().nextUID();
            
            this.getServletContext().log("set build problems " + errorId);
            this.getServletContext().log(this.getServletContext().getContextPath());
            this.getServletContext().log(this.getServletContext().getRealPath("build-problems"));
            
            buildProblems.put(errorId, issues);
        	
            handleException(this,response,"Cannot build source id " + botId,x);
            response.addHeader("Location-Error", ctx+"/build-problems/"+errorId);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error building source");
        }
        
        // generate response
        response.addHeader("Location",ctx+"/bots-src/" + botId);
        response.addHeader("Location-Metadata",ctx+"/bots-metadata/" + botId);
        response.setStatus(HttpServletResponse.SC_CREATED);
        
    }

    @Override
    public String getServletInfo() {
        return "build and save a bot";
    }

}
