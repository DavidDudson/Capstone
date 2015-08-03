<%-- 
    Created on : 19.05.2015
    Author     : Johannes Tandler
--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services)application.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
	
	pageContext.setAttribute("screenName",
			session.getAttribute("userName"));
	pageContext.setAttribute("profilePicture",
			session.getAttribute("userPicture"));
%>


<!DOCTYPE html>
<html>
<head>
<base
	href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>Admin</title>
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">

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
				<a class="navbar-brand" href="${pageContext.request.contextPath}"><%= gameName %></a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="editor.jsp">Editor</a></li>
					<li><a href="test.jsp">Test</a></li>
					<li><a href="documentation.jsp" target="_blank">Help</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco" target="_blank">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>
                    <li><a href="http://sogaco.massey.ac.nz">About</a></li>

					<li class="active"><a href="admin/index.jsp">Admin <span
							class="sr-only">(current)</span></a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li class="profilePictureContent" id="profilePicture1"><p
							class="navbar-text">${screenName}</p></li>
					<li><a id="profilePicture2" href="#">Logout</a></li>

					<c:if test="${profilePicture != null}">
						<li class="profilePictureContent" id="profilePicture3"><img
							id="profilePictureURL" src="${profilePicture}"
							class="img-responsive img-rounded center-block"
							style="width: 40px; margin: 5px;" alt="Profile Picture"></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>

	<div style="clear: both"></div>

	<div class="container"
		style="background-color: white; padding-left: 20px; padding-right: 20px; box-shadow: 0 10px 10px #000;">
		<div class="row">
			<div class="col-md-8">
				<div class="page-header">
					<h1>Users</h1>
				</div>
				<div>
					<table id="userTable" class="table table-hover table-bordered"
						style="width: 100%;">
						<thead style="background-color: #337ab7; color: white;">
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Email</th>
								<th># Bots</th>
								<th>Role</th>
								<th data-orderable="false">Details</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class="col-md-4">
				<div class="page-header">
					<h1>General</h1>
				</div>
				<div>
					<a role="button" class="btn btn-sm btn-primary btn-block" href="eclipse">Checkout
						all bots as eclipse project</a>
					<table id="systemTable" class="display table table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th class="text-right">Value</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>RAM :</th>
								<td id="ramUsageField" class="text-right"></td>
							</tr>
							<tr>
								<th>Disk usage :</th>
								<td id="diskUsageField" class="text-right"></td>
							</tr>
							<tr>
								<th># played games :</th>
								<td id="playedGamesField" class="text-right"></td>
							</tr>
							<tr>
								<th># active games :</th>
								<td id="activeGamesField" class="text-right"></td>
							</tr>
							<tr>
								<th># queued games :</th>
								<td id="queuedGamesField" class="text-right"></td>
							</tr>
							<tr>
								<th># completed builds :</th>
								<td id="completedBuildsField" class="text-right"></td>
							</tr>
							<tr>
								<th># active builds :</th>
								<td id="activeBuildsField" class="text-right"></td>
							</tr>
							<tr>
								<th># queued builds :</th>
								<td id="queuedBuildsField" class="text-right"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="page-header">
					<h1>Logging Ping :</h1>
				</div>
				<pre id="logqueuePing" class="text-center">
					Warte auf Antwort ...
				</pre>
			</div>
		</div>
	</div>


	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<script src="js/jquery.dataTables.min.js" charset="utf-8"></script>
	<script type="text/javascript"
		src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	<script src="js/authMethod.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#userTable')
									.dataTable(
											{
												"ajax" : {
													'type' : 'GET',
													'url' : 'users',
													'data' : {
														detailed : 'true'
													},
													"dataSrc" : ""
												},
												"columns" : [
														{
															"data" : "id"
														},
														{
															"data" : "name",
															"defaultContent" : "<i>Not set</i>"
														},
														{
															"data" : "email",
															"defaultContent" : "<i>Not yet set</i>"
														},
														{
															"data" : "botCount"
														},
														{
															"data" : "role"
														},
														{
															"data" : "",
															"defaultContent" : "<a href=\"#\">Details</a>"
														} ],
												"rowCallback" : function(row,
														data, index) {
													var link = row.cells[5].children[0];
													link.href = "userDetails/"
															+ data.id;
												}
											});

							$
									.ajax("systemstate")
									.done(
											function(data) {
												data = data[0];
												$('#ramUsageField').html(
														data.ram);
												$('#diskUsageField').html(
														data.disk);
												$('#playedGamesField')
														.html(
																(typeof data.playedGames === "undefined") ? "-"
																		: data.playedGames);
												$('#activeGamesField')
														.html(
																(typeof data.activeGames === "undefined") ? "-"
																		: data.activeGames);
												$('#queuedGamesField')
														.html(
																(typeof data.gamesQueueLength === "undefined") ? "-"
																		: data.gamesQueueLength);
												$('#completedBuildsField')
														.html(
																(typeof data.completedBuilds === "undefined") ? "-"
																		: data.completedBuilds);
												$('#activeBuildsField')
														.html(
																(typeof data.activeBuilds === "undefined") ? "-"
																		: data.activeBuilds);
												$('#queuedBuildsField')
														.html(
																(typeof data.buildQueueLength === "undefined") ? "-"
																		: data.buildQueueLength);
												$('#logqueuePing').html(data.eventQueueState);
											}).fail(
											function() {
												$('#ramUsageField').html(
														"error");
												$('#diskUsageField').html(
														"error");
												$('#playedGamesField').html(
														"error");
												$('#activeGamesField').html(
														"error");
												$('#queuedGamesField').html(
														"error");
												$('#completedBuildsField')
														.html("error");
												$('#activeBuildsField').html(
														"error");
												$('#queuedBuildsField').html(
														"error");
											})
						});
	</script>
</body>
</html>
