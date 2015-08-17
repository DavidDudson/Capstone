package nz.ac.massey.cs.ig.api.config.shiro.login.social;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import nz.ac.massey.cs.ig.api.servlets.SocialLogin;
import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class FacebookHelper extends LoginHelper {
	
	public FacebookHelper() {
		client = HttpClients.createDefault();
	}
	
	private String createAppProof(String accessToken)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Key sk = new SecretKeySpec(SocialLogin.FACEBOOK_API_SECRET.getBytes(),
				"HmacSHA256");
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		final byte[] hmac = mac.doFinal(accessToken.getBytes());

		StringBuilder sb = new StringBuilder(hmac.length * 2);
		Formatter formatter = new Formatter(sb);
		for (byte b : hmac) {
			formatter.format("%02x", b);
		}
		formatter.close();

		return sb.toString();
	}

	public UserData getUserData(String accessToken)
			throws ClientProtocolException, IOException {
		String app_proof = null;
		try {
			app_proof = createAppProof(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		HttpGet get = new HttpGet("https://graph.facebook.com/me?access_token="
				+ accessToken + "&appsecret_proof=" + app_proof);
		HttpResponse response = client.execute(get);

		String userData = readStream(response.getEntity().getContent());

		JSONObject json = new JSONObject(userData);

		String userId = json.getString("id");
		String name = json.getString("name");
		String pictureImage = "http://graph.facebook.com/" + userId + "/picture";
		
		UserData user = new UserData(userId);
		user.setName(name);
		user.setPhotoUrl(pictureImage);
		
		return user;
	}
}
