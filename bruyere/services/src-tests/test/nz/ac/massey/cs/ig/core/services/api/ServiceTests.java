package test.nz.ac.massey.cs.ig.core.services.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import nz.ac.massey.cs.ig.languages.java.JavaSupport;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Tests various services.
 * 
 * @author jens dietrich
 */
public class ServiceTests extends AbstractServiceTests {

	public ServiceTests() {
		super();
	}

	@Test
	public void testBuildGetRebuildGet() throws Exception {
		String src = readSource("testdata/GreedyBuiltinBot.src");
		login("foo");

		// save new bot
		HttpResponse response = this.putBot(null, "JAVA", src);
		checkStatus(response, HttpStatusCodes.SC_CREATED);

		// fetch bot source from server
		String botAdr = response.getLastHeader("Location").getValue();
		String botMetadataAdr = response.getLastHeader("Location-Metadata")
				.getValue();
		String botId = botAdr.substring(botAdr.lastIndexOf('/') + 1);
		System.out.println("Bot is at location " + botAdr);
		System.out.println("Bot id is " + botId);

		response = getResource(botAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "text/plain");
		String src2 = EntityUtils.toString(response.getEntity());
		assertEquals(src, src2);

		// fetch bot metadata
		response = getResource(botMetadataAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "application/json");
		JSONObject json = decodeJSON(response);
		
		DateTime _lastmodified = ISODateTimeFormat.dateTimeParser().parseDateTime(json.getString("lastmodified"));
		DateTime _created = ISODateTimeFormat.dateTimeParser().parseDateTime(json.getString("created"));
		String _id = json.getString("id");
		assertEquals(botId, _id);
		assertTrue("last modification date should be after created", _lastmodified.isAfter(_created) || _lastmodified.isEqual(_created));

		// modify sources (insert line of comment), and save again
		String srcUpdated = "// a comment\n" + src;
		response = putBot(botId, JavaSupport.IDENTIFIER, srcUpdated);
		checkStatus(response, HttpStatusCodes.SC_CREATED);

		// fetch again
		String botAdr2 = response.getLastHeader("Location").getValue();
		String botMetadataAdr2 = response.getLastHeader("Location-Metadata")
				.getValue();
		String botId2 = botAdr2.substring(botAdr.lastIndexOf('/') + 1);

		System.out.println("Bot is now at location " + botAdr2);
		System.out
				.println("Bot metadata is now at location " + botMetadataAdr2);
		System.out.println("Bot id is now " + botId2);

		assertEquals(botId, botId2); // the bot id should be the same

		response = getResource(botAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "text/plain");
		String srcUpdated2 = EntityUtils.toString(response.getEntity());
		assertEquals(srcUpdated, srcUpdated2);

		// fetch bot metadata again
		response = getResource(botMetadataAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "application/json");
		json = decodeJSON(response);
		DateTime _lastmodified2 = ISODateTimeFormat.dateTimeParser().parseDateTime(json.getString("lastmodified"));
		DateTime _created2 = ISODateTimeFormat.dateTimeParser().parseDateTime(json.getString("created"));
		String _id2 = json.getString("id");
		assertEquals(botId, _id2);
		System.out.println(_lastmodified2);
		System.out.println(_created2);
		assertTrue(_lastmodified2.isAfter(_created2));
	}

	@Test
	public void testCreate2BotsFetchBotsByUser() throws Exception {
		String src1 = readSource("testdata/GreedyBuiltinBot.src");
		String src2 = readSource("testdata/CautiousBuiltinBot.src");
		login("foo");

		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src1);
		checkStatus(response, HttpStatusCodes.SC_CREATED);
		String botAdr1 = response.getLastHeader("Location").getValue();
		String botId1 = botAdr1.substring(botAdr1.lastIndexOf('/') + 1);

		response = this.putBot(null, JavaSupport.IDENTIFIER, src2);
		checkStatus(response, HttpStatusCodes.SC_CREATED);
		String botAdr2 = response.getLastHeader("Location").getValue();
		String botId2 = botAdr2.substring(botAdr2.lastIndexOf('/') + 1);

		response = this.getResource("/userbots/foo", false);
		checkContentType(response, "application/vnd.collection+json");
		JSONObject json = this.decodeJSON(response);

		System.out.println(json);

		JSONObject coll = json.getJSONObject("collection");
		JSONArray items = (JSONArray) coll.get("items");

		System.out.println(items);

		Set<String> ids = new HashSet<>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject botMetaData = items.getJSONObject(i);
			String _id = botMetaData.getString("id");
			ids.add(_id);
		}

		assertTrue(ids.contains(botId1));
		assertTrue(ids.contains(botId2));
		assertEquals(2, ids.size());

	}

	@Test
	public void testGetUID() throws Exception {
		URI uri = new URI(Settings.SERVER + "uid");
		// create and execute the request
		HttpGet request = new HttpGet(uri);
		HttpResponse response = httpClient.execute(request);
		// this string is the unparsed web page (=html source code)
		String content = EntityUtils.toString(response.getEntity());
		assertNotNull(content);
		assertTrue(content.trim().length() > 0);
	}

	@Test
	public void testCompilationFails() throws Exception {
		String src = readSource("testdata/GreedyBuiltinBot_faulty1.src");
		login("foo");

		// save new bot
		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src);
		checkStatus(response, HttpStatusCodes.SC_INTERNAL_SERVER_ERROR);

		// fetch bot source from server
		String errorAdr = response.getLastHeader("Location-Error").getValue();

		System.out.println("Error is at location " + errorAdr);

		response = getResource(errorAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "application/vnd.collection+json");

		// parse json
		JSONObject json = decodeJSON(response);
		JSONArray items = json.getJSONObject("collection")
				.getJSONArray("items");

		assertEquals(1, items.length());

		JSONObject issue = items.getJSONObject(0);
		System.out.println(issue);
		// message might depend on compiler
		// assertEquals("missing return statement",issue.get("message"));
		assertEquals(21, issue.getInt("linenumber"));
		assertEquals("ERROR", issue.getString("kind"));

	}

	@Test
	public void testStaticVerificationFails() throws Exception {
		String src = readSource("testdata/GreedyBuiltinBot_faulty2.src");
		login("foo");

		// save new bot
		HttpResponse response = this.putBot(null, JavaSupport.IDENTIFIER, src);
		checkStatus(response, HttpStatusCodes.SC_INTERNAL_SERVER_ERROR);

		// fetch bot source from server
		String errorAdr = response.getLastHeader("Location-Error").getValue();

		System.out.println("Error is at location " + errorAdr);

		response = getResource(errorAdr, true);
		checkStatus(response, HttpStatusCodes.SC_OK);
		checkContentType(response, "application/vnd.collection+json");

		// parse json
		JSONObject json = decodeJSON(response);
		JSONArray items = json.getJSONObject("collection")
				.getJSONArray("items");

		assertEquals(1, items.length());

		JSONObject issue = items.getJSONObject(0);
		System.out.println(issue);
		// message might depend on compiler
		assertEquals("Using java.io.File is not permitted",
				issue.get("message"));
		assertEquals(19, issue.getInt("linenumber"));
		assertEquals("ERROR", issue.getString("kind"));

	}

}
