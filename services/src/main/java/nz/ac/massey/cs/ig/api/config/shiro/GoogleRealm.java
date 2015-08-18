package nz.ac.massey.cs.ig.api.config.shiro;

import nz.ac.massey.cs.ig.api.config.shiro.SocialToken.TokenType;
import nz.ac.massey.cs.ig.api.servlets.SocialLogin;

public class GoogleRealm extends SocialRealm {

	public GoogleRealm() {
		super("google");
	}

	@Override
	protected String getTokenURI() {
		return "https://accounts.google.com/o/oauth2/token";
	}

	@Override
	protected String getClientId() {
		return SocialLogin.GOOGLE_API_KEY;
	}

	@Override
	protected String getClientSecret() {
		return SocialLogin.GOOGLE_API_SECRET;
	}

	@Override
	protected TokenType getSupportedTokenType() {
		return TokenType.GOOGLE;
	}

}
