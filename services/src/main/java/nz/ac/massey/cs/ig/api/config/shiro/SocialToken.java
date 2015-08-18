package nz.ac.massey.cs.ig.api.config.shiro;


import org.apache.shiro.authc.AuthenticationToken;

public class SocialToken implements AuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 913444976237046631L;
	
	private TokenType type;
	
	private String callbackURL;
	
	private String verifier;
	
	public SocialToken(String callBackUrl, String verifier, TokenType type) {
		this.callbackURL = callBackUrl;
		this.verifier = verifier;
		this.type = type;
	}
	
	public TokenType getType() {
		return type;
	}


	public void setType(TokenType type) {
		this.type = type;
	}

	public enum TokenType {
		FACEBOOK,
		GOOGLE
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public String getVerifier() {
		return verifier;
	}

	@Override
	public Object getPrincipal() {
		return verifier;
	}

	@Override
	public Object getCredentials() {
		return new String[] {callbackURL, verifier};
	}
	
}
