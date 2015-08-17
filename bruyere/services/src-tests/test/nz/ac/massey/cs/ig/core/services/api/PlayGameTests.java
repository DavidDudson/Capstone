package test.nz.ac.massey.cs.ig.core.services.api;

import static org.junit.Assert.*;
import nz.ac.massey.cs.ig.languages.java.JavaSupport;

import org.apache.http.HttpResponse;
import org.junit.Test;

/**
 * Tests to play games on the server.
 * 
 * @author jens dietrich
 */
public class PlayGameTests extends AbstractServiceTests {

	public PlayGameTests() {
		super();
	}

	@Test
	public void testRunGame1() throws Exception {
		String src1 = readSource("testdata/GreedyBuiltinBot.src");
		String src2 = readSource("testdata/CautiousBuiltinBot.src");
		String userId = "foo";
		login(userId);

		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src1);
		checkStatus(response, HttpStatusCodes.SC_CREATED);
		String botAdr1 = response.getLastHeader("Location").getValue();
		String botId1 = botAdr1.substring(botAdr1.lastIndexOf('/') + 1);

		response = this.putBot(null, JavaSupport.IDENTIFIER, src2);
		checkStatus(response, HttpStatusCodes.SC_CREATED);
		String botAdr2 = response.getLastHeader("Location").getValue();
		String botId2 = botAdr2.substring(botAdr2.lastIndexOf('/') + 1);

		response = putGame(botId1, botId2);
		checkStatus(response, HttpStatusCodes.SC_CREATED);

		String gameAdr = response.getLastHeader("Location").getValue();
		String gameId = gameAdr.substring(gameAdr.lastIndexOf('/') + 1);
		System.out.println("Game is at location " + gameAdr);
		System.out.println("Game id is " + gameId);

		// get game
		response = getResource(gameAdr, true);

		// note that this is game specific, see PGSerializer
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "application/json");

		// do we have do that here!?
		// JSONObject json = this.decodeJSON(response);

		// System.out.println(json);
	}

	@Test
	public void testEndlessBotIsHandled() throws Exception {
		login("foo");

		String src2 = readSource("testdata/EndlessBot.src");

		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src2);
		
		assertNotEquals("EndlessBot should not compile", 200, response.getStatusLine().getStatusCode());
	}
	
	@Test
	public void testStackOverflowBotIsHandled() throws Exception {
		login("foo");

		String src2 = readSource("testdata/StackOverflowBot.src");

		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src2);
		
		assertNotEquals("StackOverflowBot should not compile", 200, response.getStatusLine().getStatusCode());
	}

}
