<%-- 
    Created on : 27 Feb 15
    Author     : Jens Dietrich
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services)application.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
	StringBuilder b = new StringBuilder();
	for (nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport pl:services.getSupportedProgrammingLanguages()) {
		if (b.length()>0) b.append(",");
		b.append(pl.getIdentifier());
	}
	String programmingLanguages = b.toString();
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to SoGaCo - <%= gameName %> </title>

<script src="js/jquery-1.10.2.min.js" charset="utf-8"></script>
<link href='css/bruyere.css' rel='stylesheet' type='text/css' />
<link href='css/flick/jquery-ui-1.10.3.custom.css' rel='stylesheet' type='text/css' />
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />

</head>
<body>

	<section>
		<h1 class="about">Welcome to the SoGaCo - <%= gameName %></h1>
		
		<div class="about">
		SoGaCo is an educational platform to code, share and execute <em>bots</em> playing board games in a web browser. SoGaCo is 
		developed by the Software Engineering group at Massey University in Palmerston North. 
		We currently support several different games and programming languages, the game of the system you are currently using is 
		<strong><%= gameName %></strong>, the programming language(s) supported are: <strong><%= programmingLanguages%></strong>.  
		<p/><p/>
		Click <strong><a href="editor.jsp">here</a></strong> to edit bots, and <strong><a href="test.jsp">here</a></strong> to test bots. 
		</div>
	</section>
</body>
</html>