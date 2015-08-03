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

<title>SoGaCo - Plugins</title>
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
					<li class="active"><a href="Games.jsp">Active Games</a></li>
					<li><a href="People.jsp">People</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco" target="_blank">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>	
				</ul>
			</div>
		</div>
	</nav>

	<div style="clear: both"></div>

	<div class="container"
		style="background-color: white; padding-left: 20px; padding-right: 20px; box-shadow: 0 10px 10px #000;">


		<h1>Active Games</h1>


		<!-- PRIME GAME ::: BEGIN -->
		<div class="page-header" id="primegame">
			<h2>Primegame</h2>
		</div>
		<div class="row">
			<div class="col-md-4">
				<table class="table table-hover table-bordered">
					<tr>
						<td>Game :</td>
						<td><b>Prime Game</b></td>
					</tr>
					<tr>
						<td>Active programming languages :</td>
						<td>Java, Python</td>
					</tr>
				</table>
				<br />
				<h4>Stats :</h4>
				<table data-url="primegame/"
					class="table table-hover table-bordered statsTable">
					<tr>
						<td>Users :</td>
						<td class="userAmount">-</td>
					</tr>
					<tr>
						<td>Bots :</td>
						<td class="botAmount">-</td>
					</tr>
					<tr>
						<td>Played Games :</td>
						<td class="numberOfPlayedGames">-</td>
					</tr>
				</table>
				<br />
				<br />
				<a role="button" href="primegame/editor.jsp" class="btn btn-lg btn-primary btn-block">Try
					It!</a>
			</div>
			<div class="col-md-8">
				<img alt="Primegame Screenshot" src="img/primegame-screen.png"
					class="img-responsive img-rounded" style="border: 1px solid black;">
				Primegame Screenshot
			</div>
		</div>
		<!-- PRIME GAME ::: END -->
		
		<!-- MANCALA ::: BEGIN -->
		<div class="page-header" id="mancala">
			<h2>Mancala</h2>
		</div>
		<div class="row">
			<div class="col-md-4">
				<table class="table table-hover table-bordered">
					<tr>
						<td>Game :</td>
						<td><b>Mancala</b></td>
					</tr>
					<tr>
						<td>Active programming languages :</td>
						<td>Java</td>
					</tr>
				</table>
				<br />
				<h4>Stats :</h4>
				<table data-url="mancala/"
					class="table table-hover table-bordered statsTable">
					<tr>
						<td>Users :</td>
						<td class="userAmount">-</td>
					</tr>
					<tr>
						<td>Bots :</td>
						<td class="botAmount">-</td>
					</tr>
					<tr>
						<td>Played Games :</td>
						<td class="numberOfPlayedGames">-</td>
					</tr>
				</table>
				<br />
				<br />
				<a role="button" href="mancala/editor.jsp" class="btn btn-lg btn-primary btn-block">Try
					It!</a>
			</div>
			<div class="col-md-8">
				<img alt="Mancala Screenshot" src="img/mancala-screen.png"
					class="img-responsive img-rounded" style="border: 1px solid black;">
				Mancala Screenshot
			</div>
		</div>
		<!-- Mancala ::: END -->
		<!-- Othello ::: BEGIN -->
		<div class="page-header" id="othello">
			<h2>Othello</h2>
		</div>
		<div class="row">
			<div class="col-md-4">
				<table class="table table-hover table-bordered">
					<tr>
						<td>Game :</td>
						<td><b>Othello</b></td>
					</tr>
					<tr>
						<td>Active programming languages :</td>
						<td>Java, Python</td>
					</tr>
				</table>
				<br />
				<h4>Stats :</h4>
				<table data-url="othello/editor.jsp"
					class="table table-hover table-bordered statsTable">
					<tr>
						<td>Users :</td>
						<td class="userAmount">-</td>
					</tr>
					<tr>
						<td>Bots :</td>
						<td class="botAmount">-</td>
					</tr>
					<tr>
						<td>Played Games :</td>
						<td class="numberOfPlayedGames">-</td>
					</tr>
				</table>
				<br />
				<br />
				<a href="othello/" role="button" class="btn btn-lg btn-primary btn-block">Try
					It!</a>
			</div>
			<div class="col-md-8">
				<img alt="Othello Screenshot" src="img/othello-screen.png"
					class="img-responsive img-rounded" style="border: 1px solid black;">
				Othello Screenshot
			</div>
		</div>
		<!-- PRIME GAME ::: END -->

	</div>


	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<%@include file="WEB-INF/views/ganalytics.html" %>

	<script type="text/javascript">
		$(window).ready(function() {
			$('.statsTable').each(function(i, e) {
				e = $(e);
				var url = e.data("url") + "systemstate";
				$.get(url).done(function(data) {
					data = data[0];
					e.find(".userAmount").text(data.users);
					e.find(".botAmount").text(data.bots);
					e.find(".numberOfPlayedGames").text(data.playedGames);
				});
			})
		});
	</script>
</body>
</html>
