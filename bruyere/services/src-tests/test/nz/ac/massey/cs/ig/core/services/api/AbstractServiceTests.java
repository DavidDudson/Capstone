package test.nz.ac.massey.cs.ig.core.services.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import nz.ac.massey.cs.ig.core.services.Services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import test.nz.ac.massey.cs.ig.core.services.api.server.TestServer;

import com.google.common.io.Files;

/**
 * Abstract test class - tests whether connection to server can be established.
 * 
 * @author jens dietrich
 */
public abstract class AbstractServiceTests {

	/**
	 * Test BrujereServer
	 */
	private TestServer server;

	/**
	 * Instance of {@link HttpClientBuilder} to build used {@link HttpClient}s
	 */
	protected HttpClient httpClient = null;

	private RequestConfig config = null;

	public AbstractServiceTests() {
		super();

		httpClient = HttpClientBuilder.create().build();

		Builder builder = RequestConfig.custom();
		builder.setSocketTimeout(15000);
		builder.setConnectionRequestTimeout(15000);

		config = builder.build();
	}
	
	private static boolean deletedAlready = false;

	@BeforeClass
	public static void deleteOldStorageFolder() {
		if(!deletedAlready) {
			deletedAlready = true;
		} else {
			return;
		}
		
		File file = new File("target/.SoGaCo");
		if (file.exists()) {
			try {
				delete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	/**
	 * Start Brujere server before running any tests.
	 * 
	 * @throws Exception
	 */
	@Before
	public void createServer() throws Exception {
		server = new TestServer();
		server.start();
	}

	/**
	 * Stop Brujere Server after running a test.
	 * 
	 * @throws Exception
	 */
	@After
	public void stopServer() throws Exception {
		server.stop();
		server = null;
	}

	@After
	public void deleteStorage() throws Exception {
		Services services = server.getServices();

		EntityManager em = services.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		Connection c = em.unwrap(Connection.class);
		Statement s = c.createStatement();
		Set<String> tables = new HashSet<String>();
		ResultSet rs = s
				.executeQuery("select s.schemaname || '.' || t.tablename "
						+ "from sys.systables t, sys.sysschemas s "
						+ "where t.schemaid = s.schemaid "
						+ "and t.tabletype = 'T' "
						+ "order by s.schemaname, t.tablename");
		while (rs.next()) {
			tables.add(rs.getString(1));
		}
		rs.close();
		for (String table : tables) {
			s.executeUpdate("DELETE FROM " + table);
		}

		s.close();
		em.flush();
		trans.commit();
		em.getEntityManagerFactory().getCache().evictAll();
		em.close();
	}

	protected String readSource(String fileName) throws Exception {
		File f = new File(fileName);
		return Files.toString(f, Charset.defaultCharset());
	}

	protected HttpResponse putBot(String botId, String lang, String src)
			throws Exception {
		
		HttpEntityEnclosingRequestBase post = null;
		
		if(botId != null) {
			post = new HttpPut(Settings.SERVER + Settings.APPL
					+ "/bots/" + botId);
		} else {
			post = new HttpPost(Settings.SERVER + Settings.APPL
					+ "/bots");
		}
		System.out.println("put bot: " + post.getURI().toString());
		
		JSONObject ob = new JSONObject();
		ob.put("name", "test");
		ob.put("language", lang);
		ob.put("src", src);
		
		post.setEntity(new StringEntity(ob.toString()));
		return httpClient.execute(post);
	}

	protected HttpResponse putGame(String botId1, String botId2)
			throws Exception {
		String adr = Settings.SERVER + Settings.APPL + "/creategame_b2b";
		URI uri = new URI(adr);
		HttpPost post = new HttpPost(uri);
		String s = botId1 + "\n" + botId2 + "\n";
		post.setEntity(new StringEntity(s));
		return httpClient.execute(post);
	}

	protected HttpResponse getResource(String url, boolean serverGeneratedURL)
			throws Exception {
		URI uri = serverGeneratedURL ? new URI(Settings.SERVER + url)
				: new URI(Settings.SERVER + Settings.APPL + url);
		HttpGet get = new HttpGet(uri);
		get.setConfig(config);

		System.out.println("Fetching resource " + uri);
		return httpClient.execute(get);
	}

	protected void checkStatus(HttpResponse response, int status) {
		assertEquals(status, response.getStatusLine().getStatusCode());
	}

	protected void checkContentType(HttpResponse response, String contentType) {
		assertTrue(response.getEntity().getContentType().getValue()
				.startsWith(contentType));
	}

	protected JSONObject decodeJSON(HttpResponse response) throws Exception {
		String data = EntityUtils.toString(response.getEntity());
		System.out.println("decoding json data: " + data);
		return new JSONObject(new JSONTokener(data));
	}

	protected void login(String userId) throws Exception {
		String adr = Settings.SERVER + Settings.APPL + "/login";
		URI uri = new URI(adr);
		HttpPost post = new HttpPost(uri);
		post.setEntity(new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("username", userId),
				new BasicNameValuePair("password", "secret"))));
		httpClient.execute(post);
	}

}
