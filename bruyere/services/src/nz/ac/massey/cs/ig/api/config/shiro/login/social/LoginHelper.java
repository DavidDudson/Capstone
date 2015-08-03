package nz.ac.massey.cs.ig.api.config.shiro.login.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import nz.ac.massey.cs.ig.core.game.model.UserData;

public abstract class LoginHelper {

	protected HttpClient client;

	protected String readStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();

		return buffer.toString();
	}
	
	public abstract UserData getUserData(String token) throws ClientProtocolException, IOException;
}
