
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
			.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
%>

<!DOCTYPE html>
<html>
<head>

<title>login</title>

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-social.css" rel="stylesheet">

<link href="css/bruyere-theme.css" rel="stylesheet">

<link href="css/loginPage.css" rel="stylesheet">

<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
    var getUrl = window.location;
    var baseUrl = getUrl.protocol + "//" + getUrl.host + "/"
            + getUrl.pathname.split('/')[1];
    $(".callbackUrlElement").val(baseUrl);
    $(".baseURLElement").val(baseUrl);
</script>
</head>

<body>

	<nav class="navbar navbar-default navbar-static-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}"><%=gameName%></a>
			</div>
		</div>
	</nav>

	<div style="clear: both"></div>
	<br />

	<div class="main container">
		<div class="row">

			<div class="col-md-8">
				<div class="text-center">
					<h1 class="about">
						Welcome to the SoGaCo - <%=gameName%></h1>
				</div>
					Click <strong><a href="editor.jsp">here</a></strong> to edit java bots,
					Click <strong><a href="easyJ.jsp">here</a></strong> to edit blockly bots.
					and <strong><a href="test.jsp">here</a></strong> to test bots.
				</div>
			</div>
			<div class="col-md-4">
				<div id="ldapLogin" class="text-center">

					<h3>Login with your Massey account</h3>
					<br>

					<% if(request.getAttribute("authMessage") != null) { %>
					<div class="alert alert-danger" role="alert">${authMessage}</div>
					<% } %>
					<!-- This form is for handling LDAP authentication requests-->
					<form name="loginform" action="login" method="POST"
						class="center-block loginForm">
						<input type="hidden" class="baseURLElement" name="baseURL"
							value="" /> <input type="text" class="form-control"
							name="username" required="required" placeholder="Username" value="${username}" /> <input
							type="password" name="password" class="form-control"
							required="required" placeholder="Password" /><br /> <input
							class="form-control" type="submit" value="Login"><br>
					</form>
				</div>
				<div id="socialLogin" class="loginBlock text-center">
					<div class="paddingBlock"></div>

					<h4>or by</h4>
					<br>

					<!-- Login buttons for social authentication -->
					<div class="googlePlusButton">
						<form action="SocialLogin" method="POST" style="width: 200px;"
							class="center-block">
							<input type="hidden" name="loginType" value="google" /> <input
								type="hidden" class="callbackUrlElement" name="callbackurl"
								value="" /> <input type="hidden" class="baseURLElement"
								name="baseURL" value="" />
							<p>
								<button type="submit"
									class="btn btn-block btn-social btn-google">
									<i class="fa fa-google-plus"></i>Sign in with Google
								</button>
							</p>
						</form>
					</div>

					<div class="facebookButton">
						<form action="SocialLogin" method="POST" style="width: 200px;"
							class="center-block">
							<input type="hidden" name="loginType" value="facebook" /> <input
								type="hidden" class="callbackUrlElement" name="callbackurl"
								value="" /> <input type="hidden" class="baseURLElement"
								name="baseURL" value="" />
							<p>
								<button type="submit"
									class="btn btn-block btn-social btn-facebook">
									<i class="fa fa-facebook"></i>Sign in with Facebook
								</button>
							</p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	
	<div style="position:fixed;bottom:10px;right:20px;z-index:-1;color:LightGray;font-size:10px;">
		V. ${timestamp}
	</div>

</body>
</html>