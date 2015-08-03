<%-- 
    Created on : 27 Feb 15
    Author     : Jens Dietrich
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
<script data-main="js/main-test" src="js/require.js" charset="utf-8"></script>
<script src="js/ace.js" charset="utf-8"></script>
<script src="js/jquery-1.10.2.min.js" charset="utf-8"></script>
<script src="js/bootstrap.min.js" charset="utf-8"></script>
<script src="js/bootbox.min.js" charset="utf-8"></script>
<script src="js/underscore-min.js" charset="utf-8"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js" charset="utf-8"></script>
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href='css/bruyere.css' rel='stylesheet' type='text/css' />
<link href='css/tester.css' rel='stylesheet' type='text/css' />
<link href='css/flick/jquery-ui-1.10.3.custom.css' rel='stylesheet'
	type='text/css' />

<script src="js/authMethod.js" charset="utf-8"></script>
<%
	pageContext.setAttribute("screenName",
			session.getAttribute("userName"));
	pageContext.setAttribute("profilePicture",
			session.getAttribute("userPicture"));
	nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
			.getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
	String gameName = services.getGameSupport().getName();
%>

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
					<li><a href="editor.jsp">Editor</a></li>
					<li class="active"><a href="test.jsp">Test</a></li>
					<li><a href="documentation.jsp" target="_blank">Help</a></li>
					<li><a href="https://groups.google.com/forum/#!forum/sogaco" target="_blank">Community</a></li>
					<li><a href="https://www.surveymonkey.com/r/DKWCGV6" target="_blank">Survey</a></li>
					<li><a href="http://sogaco.massey.ac.nz">About</a></li>
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
		<div class="testProgramBox">
			<div class="programBox">
				<div class="listTitle">My Bots</div>
				<div id="ownBotsList" class="programList programListSmall">Loading...</div>
			</div>

			<div class="programBox">
				<div class="listTitle">Built-In Bots</div>
				<div id="publicBotsList" class="programListResponsive">Loading...</div>
			</div>
			<div class="programBox">
				<div class="listTitle">Shared Bots</div>
				<div style="background-color: #DDD; width: 180px; padding-top: 3px;">
					<input type="text" id="searchBox"
						style="width: 120px; float: left;" value="Search ..."
						data-default="true">
					<button id="btnSearch" class="btn btn-primary" type="button"
						style="float: left; margin-left: 5px;">
						<i class="icon-search icon-white"></i>
					</button>
					<div style="clear: both"></div>
				</div>
				<div id="sharedBotsList" class="programListResponsive">
					<ul></ul>
				</div>
			</div>
		</div>


		<div id="gameControlsBox">
			<div class="listTitle">Game Controls</div>
			<div id="container">
				<div id="gameControls">
					<button id="btnStepBackward" class="btn btn-primary" type="button">
						<i class="icon-step-backward icon-white"></i>
					</button>
					<button id="btnPlayBackward" class="btn btn-primary" type="button">
						<i class="icon-play flipH icon-white"></i>
					</button>
					<button id="btnPause" class="btn btn-primary" type="button">
						<i class="icon-pause icon-white"></i>
					</button>
					<button id="btnPlayForward" class="btn btn-primary" type="button">
						<i class="icon-play icon-white"></i>
					</button>
					<button id="btnStepForward" class="btn btn-primary" type="button">
						<i class="icon-step-forward icon-white"></i>
					</button>

				</div>
				<br /> <br />
				<div id="gameSpeed">
					<!--   <b>Animation Speed:</b><br/>-->
					Slow
					<div id="slider"
						class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all"
						aria-disabled="false">
						<a class="ui-slider-handle ui-state-default ui-corner-all"
							href="#" style="left: 50%;"></a>
					</div>
					Fast
				</div>

				<div class="scoreboard">
					<span id="player1score" class="playerscore player1score">0</span>:<span
						id="player2score" class="playerscore player2score">0</span>
				</div>

				<div id="error" class="error"></div>

				<!--  reference to a key element provided by a game, this contains an explanation of the symbols used in the tester -->
				<jsp:include page="static/key.html" />

			</div>

		</div>
		<div id="game"></div>


	</section>


	<div id="gameloadingdialog" title="Basic dialog">
		<div id="gameloadingprogressbar"></div>
	</div>

	<div id="errordialog" title="Basic dialog">
		<div id="errordialog-content"></div>
	</div>

	<!-- Message Modal -->
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog"
		aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Message</h4>
				</div>
				<div class="modal-body">Message....</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<%
		String bot2 = request.getParameter("bot2");
		if (bot2 != null) {
	%>
	<script type="text/javascript">
		require([ "bruyere-service-support" ], function(support) {
				support.preselectSharedPlayer("<%=bot2%>");
		});
	</script>
	<%
		}
	%>
	<script type="text/javascript">
		require(
				[ "js.cookie", "domReady" ],
				function(Cookies) {

					var cookieName = "testToolTipDisplayed";
					if (!Cookies.get(cookieName)) {
						Cookies.set(cookieName, "true", {
							expires : 3650
						});

						$('#ownBotsList')
								.popover(
										{
											title : "Help",
											placement : "right",
											content : "You have to select two bots before you can start a game.",
											trigger : "manual"
										});
						$('#ownBotsList').popover('show');
						$('.testProgramBox').click(function() {
							$('#ownBotsList').popover('hide');
						});
					}
				});
	</script>
</body>
</html>
