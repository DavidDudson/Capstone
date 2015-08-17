<%-- 
    Created on : 28.05.2015
    Author     : Johannes Tandler
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>SoGaCo - About</title>
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/bruyere-about.css" rel="stylesheet">
</head>
<body style="background-color: #2A779E;">

	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}">SoGaCo</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="index.jsp">Home</a></li>
					<li><a href="Games.jsp">Active Games</a></li>
					<li><a href="People.jsp">People</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco" target="_blank">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div style="clear: both"></div>

	<div class="container" style="background-color: white; padding-left: 20px; padding-right: 20px; box-shadow: 0 10px 10px #000; font-size: large">


		<h1>SoGaCo - Social Gaming & Coding</h1>

		SoGaCo is an educational
		platform developed by the Software Engineering Group at <a href="http://www.massey.ac.nz/">Massey
		University</a>. With SoGaCo, students at highschool and
		university undergrad level can use a simple web-based development
		environment to code bots that play simple math / board games on their
		behalf. We expect that using SoGaCo will improve the learning outcomes
		of students by motivating them to engage. Note that this is just a
		claim at the moment that still has to be confirmed by research.
		<p />
		<p />
		The advantage for education providers is that SoGaCo can be easily
		deployed in the cloud with providers like Amazon, Google or Microsoft,
		and no on-site installation and administration is necessary. SoGaCo
		can be used to teach basic programming concepts, algorithms and data
		structures, and selected topics in artificial intelligence. SoGaCo
		currently supports several games (PrimeGame, Othello, Mancala, coming
		soon: TicTacToe and Chinese Checkers), programming languages (Java and
		Python, planned: Ruby and JavaScript) and authentication methods
		(LDAP, Google and Facebook via OpenAuth).
		<p />
		<p />
		Underneath SoGaCo is a sophisticated modular service-based architecture that processes
		bots using a verification pipeline in order to prevent injection and denial of service attacks. 
		The SoGaCo project offers many opportunities for potential collaborators. If you are interested 
		to use SoGaCo in your teaching, or have ideas how to extend or improve it, please 
		let us know. For more infos, contact <a href="https://sites.google.com/site/jensdietrich/">Jens Dietrich</a>. 
		
		<p /> <br/>
		<p /> <br/>
	</div>


	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<%@include file="WEB-INF/views/ganalytics.html" %>
</body>
</html>
