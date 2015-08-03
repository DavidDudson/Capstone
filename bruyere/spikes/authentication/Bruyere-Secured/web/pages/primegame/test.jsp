
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
<script data-main="js/main-test" src="js/require.js"></script>
<script src="js/ace.js"></script>
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.min.js"></script>
<script src="js/underscore-min.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="pipe.png" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href='css/bruyere.css' rel='stylesheet' type='text/css' />
<link href='css/flick/jquery-ui-1.10.3.custom.css' rel='stylesheet' type='text/css' />
<script src="../js/authMethod.js"></script> <script type="text/javascript">window.onload = function(){showLogoutPane()}</script>
</head>
<body>
    <%
        pageContext.setAttribute("screenName", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userName")); 
        pageContext.setAttribute("profilePicture", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userPicture"));
    %>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="/primegame">PrimeGame</a>
				<ul class="nav">
					<li><a href="editor.jsp">Editor</a></li>
					<li class="active"><a href="test.jsp">Test</a></li>
					<li><a href="tournament.jsp">Tournaments</a></li>
				</ul>
                                    <div class="profilePictureContent" id="profilePicture3"><img id="profilePictureURL" src="${profilePicture}" alt="Profile Picture" width="40" height="40"></div>
                                    <div class="profilePictureContent" id="profilePicture2"><a href="../../logout">Logout</a></div> 
                                    <div class="profilePictureContent" id="profilePicture1">${screenName}</div>
			</div>
		</div>
	</div>
	<section>
		<div id="info" class="alert alert-info">Select 2 bots to test against each other.</div>
		<div class="testProgramBox">
			<div class="programBox">
				<div class="listTitle">My Bots</div>
				<div id="ownBotsList" class="programList programListSmall">Loading...</div>
			</div>

			<div class="programBox">
				<div class="listTitle">Built-In Bots</div>
				<div id="publicBotsList" class="programList programListSmall">Loading...</div>
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
				<br />
				<br />
				<div id="gameSpeed">
					<!--   <b>Animation Speed:</b><br/>-->
					Slow
					<div id="slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all" aria-disabled="false">
                    	<a class="ui-slider-handle ui-state-default ui-corner-all" href="#" style="left: 50%;"></a>
                   	</div>
                   	Fast
                </div>
                
                <div class="scoreboard">
                	<span id="player1score" class="playerscore player1score">0</span>:<span id="player2score" class="playerscore player2score">0</span>
                </div>
                
				<!--   <div id="output"></div> -->
				<div id="gameKey">
					<!--   <span class="keyTitle">Key</span><br />-->
					<div class="square-small player1Played"></div>
					<span class="keyText">bot 1 (played)</span><p/>
					<div class="square-small player1Forced"></div>
					<span class="keyText">bot 1 (forced)</span><p/>
					<div class="square-small player2Played"></div>
					<span class="keyText">bot 2 (played)</span><p/>
					<div class="square-small player2Forced"></div>
					<span class="keyText">bot 2 (forced)</span><p/>
				</div>
			</div>
			
		</div>
		<div id="game">		
		</div>


	</section>
	
	<div id="gameloadingdialog" title="Basic dialog">
		<div id="gameloadingprogressbar"></div>
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
</body>
</html>
