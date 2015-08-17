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
					<li><a href="index.jsp">Home</a></li>
					<li><a href="Games.jsp">Active Games</a></li>
					<li class="active"><a href="People.jsp">People</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco" target="_blank">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div style="clear: both"></div>

	<div class="container"
		style="background-color: white; padding-left: 20px; padding-right: 20px; box-shadow: 0 10px 10px #000;">


		
		<div class="page-header">
			<!-- <h1>People</h1> -->
		</div>
		
		<div class="contributor-table">

			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/jens.png" alt="Jens Dietrich"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">
					<a href="https://sites.google.com/site/jensdietrich/" target="top">Jens
						Dietrich</a>
				</div>
				<div class="contributor-blurb">Jens is Associate Professor in
					Software Engineering at Massey. He came up with the idea of a
					web-based educational IDE. He coordinates the project, but still
					contributes code.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/jtandler.png" alt="Johannes Tandler"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Johannes Tandler</div>
				<div class="contributor-blurb">Johannes is a student at the TU
					Dresden. He worked on SoGaCo during an internship with Jens at
					Massey. He is currently the top committer.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/elliot.png" alt="Elliot Hathaway"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Elliot Hathaway</div>
				<div class="contributor-blurb">Elliot wrote the first
					prototype of a web-based PrimeGame version during his BE-Hon
					project at Massey. His user interface design is still being used.</div>
			</div>
			
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/li.png" alt="Li Sui"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Li Sui</div>
				<div class="contributor-blurb">Li is currently doing a MSc
					degree at Massey. He works on a code instrumentation and
					tracability module, and on the integration of a traditional Chinese
					game.</div>
			</div>
			
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/manfred.png" alt="Manfred Meyer"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">
					<a
						href="http://www.w-hs.de/service/informationen-zur-person/person/meyer/"
						target="top">Manfred Meyer</a>
				</div>
				<div class="contributor-blurb">Manfred came up with the idea
					for the PrimeGame many years ago. The PrimeGame was the first game
					we supported, and this motivated us to create SoGaCo.</div>
			</div>

			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/isaac.png" alt="Isaac Udy"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Isaac Udy</div>
				<div class="contributor-blurb">Issac wrote the first version
					of the Python module (in particular, the AST-based verifier) when
					he studied Software Engineering at Massey in 2014.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/marcel.png" alt="Marcel Kroll"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Marcel Kroll</div>
				<div class="contributor-blurb">Marcel worked on the
					authentication module in the (southern) summer of 2014/15.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/jensf.png" alt="Jens Fendler"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Jens Fendler</div>
				<div class="contributor-blurb">Jens met Jens while both worked
					together in Namibia. This Jens still lives there, and worked with
					Manfred on using the PrimeGame at the Polytechnic of Namibia.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/patrick.png" alt="Patrick Rynhart"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Patrick Rynhart</div>
				<div class="contributor-blurb">Patrick is a systems engineer at Massey ITS. Patrick helps us to integrate SoGaCo into the Massey and Azure infrastructure</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/eva.png" alt="Eva Heinrich"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">Eva Heinrich</div>
				<div class="contributor-blurb">Eva co-supervises Li's MSc
					project (together with Jens), and is helping us to develop an
					evaluation strategy for SoGaCo.</div>
			</div>
			<div class="contributor">
				<div class="contributor-pic">
					<img src="static/you.png" alt="you"
						style="width: 160px; float: top;">
				</div>
				<div class="contributor-name">
					<br />
				</div>
				<div class="contributor-blurb">If you are interested to
					contribute to this project, for instance by enrolling in a
					postgraduate degree under Jens' supervision, please contact Jens
					directly.</div>
			</div>

			<p />
			<br />
			<p />
			<br />

		</div>
	</div>


	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<%@include file="WEB-INF/views/ganalytics.html"%>
</body>
</html>
