<%-- 
    Created on : 27 Feb 15
    Author     : Jens Dietrich
--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	pageContext.setAttribute("screenName",
			session.getAttribute("userName"));
	pageContext.setAttribute("profilePicture",
			session.getAttribute("userPicture"));
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
			.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editor</title>
<script data-main="js/main-editor" src="js/require.js" charset="utf-8"></script>
<script src="js/ace.js" charset="utf-8"></script>
<script src="js/jquery-1.10.2.min.js" charset="utf-8"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js" charset="utf-8"></script>
<script src="js/bootstrap.min.js" charset="utf-8"></script>
<script src="js/bootbox.min.js" charset="utf-8"></script>
<script src="js/underscore-min.js" charset="utf-8"></script>
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/flick/jquery-ui-1.10.3.custom.min.css" rel="stylesheet">
<link href='css/bruyere.css' rel='stylesheet' type='text/css' />

<script src="js/authMethod.js" charset="utf-8"></script>
<script type="text/javascript">
	window.onload = function() {
		showLogoutPane();
		require([ "bruyere-settings" ], function(m) {
			m.user = "${screenName}"
		});
	}
</script>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="${pageContext.request.contextPath}"><%=gameName%></a>
				<ul class="nav">
					<li class="active"><a href="editor.jsp">Editor</a></li>
					<li><a href="test.jsp">Test</a></li>
					<li><a href="documentation.jsp" target="_blank" id="helpLink">Help</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>
					<li><a href="http://sogaco.massey.ac.nz" target="_blank">About</a></li>
					<shiro:hasRole name="teacher">
						<li><a href="admin/index.jsp">Admin</a></li>
					</shiro:hasRole>
				</ul>
				<div class="profilePictureContent" id="profilePicture3">
					<img id="profilePictureURL" src="${profilePicture}"
						alt="Profile Picture" width="40" height="40">
				</div>
				<div class="profilePictureContent" id="profilePicture2">
					<a href="logout">Logout</a>
				</div>
				<div class="profilePictureContent" id="profilePicture1">${screenName}</div>
			</div>
		</div>
	</div>
	<section>
		<div class="programBox">
			<div class="listTitle">My Bots</div>
			<div id="programList" class="programList">Loading...</div>
		</div>
		<div id="controls">
			<button id="btnNew" class="btn btn-primary" type="button">
				<i class="icon-file icon-white"></i> New
			</button>
			<button id="btnDelete" class="btn btn-primary" type="button">
				<i class="icon-trash icon-white"></i> Delete
			</button>
			<button id="btnSave" class="btn btn-primary" type="button">
				<i class="icon-download-alt icon-white"></i> Save + Compile
			</button>
			<button id="btnUndo" class="btn btn-primary" type="button">
				<i class="icon-circle-arrow-left icon-white"></i> Undo
			</button>
			<button id="btnRedo" class="btn btn-primary" type="button">
				<i class="icon-circle-arrow-right icon-white"></i> Redo
			</button>
			<button id="btnProperties" class="btn btn-primary" type="button">
				<i class="icon-list icon-white"></i> Properties
			</button>
			<button id="btnShare" class="btn btn-primary" type="button">
				<i class="icon-share icon-white"></i> <span>Share</span>
			</button>
		</div>
		<pre id="editor"></pre>
		<div id="output" style="white-space: pre"></div>
	</section>
	
	<div id="dialog-confirm" title="Discard unsaved changes?" style="display:none;">
  		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>You're editor contains unsaved changes. If you switch to another bot your changes are lost. Do you still want to switch?</p>
	</div>

	<script type="text/javascript">
		require(
				[ "js.cookie", "domReady" ],
				function(Cookies) {

					var cookieName = "editorToolTipDisplayed";
					if (!Cookies.get(cookieName)) {
						Cookies.set(cookieName, "true", {
							expires : 3650
						});

						$('#helpLink')
								.popover(
										{
											title : "Help",
											placement : "bottom",
											content : "Here you can find help about the game and available API's.",
											trigger : "manual"
										});
						$('#helpLink').popover('show');
						$('body').click(function() {
							$('#helpLink').popover('hide');
						});
					}
				});
	</script>
</body>
</html>
