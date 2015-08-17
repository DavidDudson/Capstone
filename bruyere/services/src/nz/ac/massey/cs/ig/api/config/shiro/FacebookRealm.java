package nz.ac.massey.cs.ig.api.config.shiro;

import nz.ac.massey.cs.ig.api.config.shiro.SocialToken.TokenType;
import nz.ac.massey.cs.ig.api.servlets.SocialLogin;

public class FacebookRealm extends SocialRealm {

	public FacebookRealm() {
		super("facebook");
	}

	@Override
	protected String getTokenURI() {
		return "https://graph.facebook.com/v2.3/oauth/access_token";
	}

	@Override
	protected String getClientId() {
		return SocialLogin.FACEBOOK_API_KEY;
	}

	@Override
	protected String getClientSecret() {
		return SocialLogin.FACEBOOK_API_SECRET;
	}

	@Override
	protected TokenType getSupportedTokenType() {
		return TokenType.FACEBOOK;
	}

}
