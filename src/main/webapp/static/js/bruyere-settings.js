define({
	root : "",
	// now uses variable, the server will replace this by the actual user name stored in a session
	// and obtained from an authentication framework (such as LDAP or openauth)
	user : "_this_user",
	// (pseudo) user for public bots
	public_user : "builtinbots"
});