package nz.ac.massey.cs.ig.api.config.shiro.login.social;

import java.io.IOException;

import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class GoogleHelper extends LoginHelper {
	
	public GoogleHelper() {
		client = HttpClients.custom()
				.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
	}
	
	public UserData getUserData(String accessToken) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(
				"https://www.googleapis.com/plus/v1/people/me");
		get.addHeader("Authorization","Bearer " + accessToken);
		HttpResponse response = client.execute(get);

		String resp = readStream(response.getEntity().getContent());

		JSONObject json = new JSONObject(resp);

		String userId = json.getString("id");
		String name = json.getString("displayName");
		String pictureId = json.getJSONObject("image").getString("url");
		
		UserData user = new UserData(userId);
		user.setName(name);
		user.setPhotoUrl(pictureId);
		
		return user;
	}
}
