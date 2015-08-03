package test.nz.ac.massey.cs.ig.core.services.api;

import java.util.HashSet;
import java.util.Set;

import nz.ac.massey.cs.ig.languages.java.JavaSupport;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Populate the database with some test data for user "foo".
 * @author jens dietrich
 */
public class CreateTestData extends AbstractServiceTests {

    public CreateTestData() {
    	super();
    }
    
    @Test
    public void create2UserBots() throws Exception {
    	create2Bots("foo");
    }
    
    
    /**
     * TODO : Why should it be possible to add bots to the built in pseudo user!?
     * @throws Exception
     */
    @Ignore
    @Test
    public void create2PublicBots() throws Exception {
    	create2Bots(Settings.BUILTIN_BOTS_PSEUDO_USER);
    }
    
    private void create2Bots(String userId) throws Exception {
    	login(userId);
    	
    	String src1 = readSource("testdata/GreedyBuiltinBot.src");
    	String src2 = readSource("testdata/CautiousBuiltinBot.src");

        HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src1);
        checkStatus(response,HttpStatusCodes.SC_CREATED);
        String botAdr1 = response.getLastHeader("Location").getValue();
        String botId1 = botAdr1.substring(botAdr1.lastIndexOf('/')+1);  
        
        response = this.putBot(null, JavaSupport.IDENTIFIER, src2);
        checkStatus(response,HttpStatusCodes.SC_CREATED);
        String botAdr2 = response.getLastHeader("Location").getValue();
        String botId2 = botAdr2.substring(botAdr2.lastIndexOf('/')+1);  
        
        response = this.getResource("/userbots/"+userId,false);
        checkContentType(response,"application/vnd.collection+json");
        JSONObject json = this.decodeJSON(response);
        
        System.out.println(json);
        
        JSONObject coll = json.getJSONObject("collection");
        JSONArray items = (JSONArray) coll.get("items");
        
        System.out.println(items);
        
        Set<String> ids = new HashSet<>();
        for (int i=0;i<items.length();i++) {
        	JSONObject botMetaData = items.getJSONObject(i);
        	String _id = botMetaData.getString("id");
        	ids.add(_id);
        }
        
        assertTrue(ids.contains(botId1));
        assertTrue(ids.contains(botId2));
        assertEquals(2,ids.size());
    }
}
