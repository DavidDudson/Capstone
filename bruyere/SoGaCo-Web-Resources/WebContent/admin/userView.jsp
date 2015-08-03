<%-- 
    Created on : 19.05.2015
    Author     : Johannes Tandler
--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
			.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
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

<title>Admin > User</title>
<link rel="shortcut icon" type="image/x-icon" href="../sogaco.png" />
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
				<a class="navbar-brand" href="${pageContext.request.contextPath}"><%=gameName%></a>
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
		<div class="page-header">
			<h1>User ${user.name}</h1>
		</div>

		<div class="row">
			<div class="col-md-3">
				<strong>Id :</strong>
			</div>
			<div class="col-md-3">${user.id}</div>
			<div class="col-md-3">
				<strong>Email :</strong>
			</div>
			<div class="col-md-3">${user.email}</div>
		</div>

		<p>&nbsp;</p>

		<div role="tabpanel">

			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<c:forEach var="bot" items="${user.bots}" varStatus="status">
					<li role="presentation" class="${status.first ? 'active' : ''}"><a
						href="#${bot.id}" aria-controls="messages" role="tab"
						data-toggle="tab">Bot ${bot.name}</a></li>
				</c:forEach>
				<li role="presentation"><a href="#logs" aria-controls="profile"
					role="tab" data-toggle="tab">Logs</a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content">
				<c:forEach var="bot" items="${user.bots}" varStatus="status">
					<div role="tabpanel"
						class="tab-pane ${status.first ? 'active' : ''}" id="${bot.id}">
						<div class="page-header">
							<h3>MetaData</h3>
						</div>
						<div class="row">
							<div class="col-md-3">
								<strong>Id :</strong>
							</div>
							<div class="col-md-3">${bot.id}</div>
							<div class="col-md-3">
								<strong>Name :</strong>
							</div>
							<div class="col-md-3">${bot.name}</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<strong>Language :</strong>
							</div>
							<div class="col-md-3">${bot.language}</div>
							<div class="col-md-3">
								<strong></strong>
							</div>
							<div class="col-md-3"></div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<strong>Created :</strong>
							</div>
							<div class="col-md-3">${bot.created}</div>
							<div class="col-md-3">
								<strong>Last Modified :</strong>
							</div>
							<div class="col-md-3">${bot.lastModified}</div>
						</div>
						<div class="page-header">
							<h3>Source</h3>
						</div>
						<pre>${bot.src}</pre>
					</div>
				</c:forEach>
				<div role="tabpanel" class="tab-pane" id="logs">
					<pre>${userLog}</pre>
				</div>
			</div>

		</div>

	</div>



	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<script src="js/jquery.dataTables.min.js" charset="utf-8"></script>
	<script src="js/tab.js" charset="utf-8"></script>
	<script type="text/javascript"
		src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	<script src="js/authMethod.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</body>
</html>
