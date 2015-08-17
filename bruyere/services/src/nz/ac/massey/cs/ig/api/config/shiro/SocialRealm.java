package nz.ac.massey.cs.ig.api.config.shiro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.api.config.shiro.SocialToken.TokenType;
import nz.ac.massey.cs.ig.api.config.shiro.login.social.FacebookHelper;
import nz.ac.massey.cs.ig.api.config.shiro.login.social.GoogleHelper;
import nz.ac.massey.cs.ig.api.config.shiro.login.social.LoginHelper;
import nz.ac.massey.cs.ig.core.game.model.UserData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.json.JSONObject;

public abstract class SocialRealm extends AuthenticatingRealm {

	protected HttpClient client;

	public SocialRealm(String realmName) {
		super();

		this.setName(realmName);

		client = HttpClientBuilder.create().build();
		setCredentialsMatcher(new CredentialsMatcher() {
			@Override
			public boolean doCredentialsMatch(AuthenticationToken token,
					AuthenticationInfo info) {
				return token != null && info != null;
			}
		});
	}

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

	protected abstract String getTokenURI();

	protected abstract String getClientId();

	protected abstract String getClientSecret();

	private String getAccessToken(SocialToken token)
			throws ClientProtocolException, IOException {
		String code = token.getVerifier();
		String redirectURI = token.getCallbackURL();

		String uri = getTokenURI();

		HttpPost get = new HttpPost(uri);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("client_id", getClientId()));
		nvps.add(new BasicNameValuePair("client_secret", getClientSecret()));
		nvps.add(new BasicNameValuePair("redirect_uri", redirectURI));
		nvps.add(new BasicNameValuePair("code", code));
		nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));

		get.setEntity(new UrlEncodedFormEntity(nvps));

		HttpResponse response = client.execute(get);

		String json = readStream(response.getEntity().getContent());

		JSONObject ob = new JSONObject(json);

		Object accessToken = ob.get("access_token");

		return accessToken != null ? accessToken.toString() : null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken t)
			throws AuthenticationException {

		SocialToken token = (SocialToken) t;

		try {
			try {

				String accessToken = getAccessToken(token);
				if (accessToken == null) {
					return null;
				}
				
				LoginHelper helper = lookupLoginHelper(token);
				UserData data = helper.getUserData(accessToken);

				AuthenticationInfo info = new SimpleAuthenticationInfo(data, accessToken, getName());
				return info;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private LoginHelper lookupLoginHelper(SocialToken token) {
		if(token.getType() == TokenType.FACEBOOK) {
			return new FacebookHelper();
		} else {
			return new GoogleHelper();
		}
	}

	protected abstract TokenType getSupportedTokenType();

	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof SocialToken) {
			return ((SocialToken) token).getType() == getSupportedTokenType();
		}
		return super.supports(token);
	}
}
