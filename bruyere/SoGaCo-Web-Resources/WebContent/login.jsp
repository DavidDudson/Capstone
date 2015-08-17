
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services)application.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
%>
	
<!DOCTYPE html>
<html>
<head>

<title>login</title>
<script src="js/ace.js" charset="utf-8"></script>
<script src="js/jquery-1.10.2.min.js" charset="utf-8"></script>
<script src="js/bootstrap.min.js" charset="utf-8"></script>
<script src="js/bootbox.min.js" charset="utf-8"></script>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/bootstrap-social.css" rel="stylesheet">
<link href='css/bruyere.css' rel='stylesheet' type='text/css' />
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />

<script type="text/javascript" charset="utf-8">
	var usingLdap = true;
	var usingSocial = true;

	window.onload = function() {
		//hello.logout();

		if (usingLdap) {
			if (!usingSocial) {
				$("#ldapLogin").css("width", "100%");
			}
			$("#ldapLogin").fadeIn(400);
		}

		if (usingSocial) {
			if (!usingLdap) {
				$("#socialLogin").css("width", "100%");
			}
			$("#socialLogin").fadeIn(400);
		}
	}
</script>


</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand">SoGaCo - <%= gameName %></a>
			</div>
		</div>
	</div>

	<br>

	<section>

		<!--    
        <div class="loginBlockHeader">
            <h3>Login to Bruyere</h3>
        </div>
        -->

		<div class="loginContainer">

			<div class="authFailure">
				<p>${authMessage}</p>
			</div>

			<div id="ldapLogin" class="loginBlock" style="display: none">

				<div class="paddingBlock"></div>

				<h5>Login using Your Massey Account</h5>
				<br>

				<!-- This form is for handling LDAP authentication requests-->
				<form name="loginform" action="" method="POST">
						<input type="hidden" class="baseURLElement" name="baseURL" value="" />
					Username:<br /> <input type="text" name="username" required
						placeholder="Username" /><br /> Password: <br /> <input
						type="password" name="password" required placeholder="Password" /><br />
					<input type="submit" value="Login"><br>
				</form>

			</div>

			<div id="socialLogin" class="loginBlock" style="display: none">
				<div class="paddingBlock"></div>

				<h5>Login using social networks</h5>
				<br>

				<!-- Login buttons for social authentication -->
				<div class="googlePlusButton">
					<form action="SocialLogin" method="POST">
						<input type="hidden" name="loginType" value="google" />
						<input type="hidden" class="callbackUrlElement" name="callbackurl" value="" />
						<input type="hidden" class="baseURLElement" name="baseURL" value="" />
						<p>
							<button type="submit">
								<i class="fa fa-google-plus"></i>&nbsp;&nbsp; Sign in with
								Google
							</button>
						</p>
					</form>
				</div>

				<div class="facebookButton">
					<form action="SocialLogin" method="POST">
						<input type="hidden" name="loginType" value="facebook" />
						<input type="hidden" class="callbackUrlElement" name="callbackurl" value="" />
						<input type="hidden" class="baseURLElement" name="baseURL" value="" />
						<p>
							<button type="submit">
								<i class="fa fa-facebook"></i>&nbsp;&nbsp; Sign in with Facebook
							</button>
						</p>
					</form>
				</div>

				<form id="socialForm" name="loginform" action="" method="POST"
					style="visibility: hidden">
					<input id="socialFormUser" type="hidden" name="username" /> <input
						id="socialFormMeta" type="hidden" name="password" /> <input
						type="submit" id="ss" value="Login">
				</form>

			</div>
		</div>

	</section>

	<script type="text/javascript">
		var getUrl = window.location;
		var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
		$(".callbackUrlElement").val(baseUrl);
		$(".baseURLElement").val(baseUrl);
	</script>

</body>
</html>