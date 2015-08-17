<%-- 
    Author     : Li Sui
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

<title>Editor</title>
<link rel="shortcut icon" type="image/x-icon" href="sogaco.png" />
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">

</head>
<body style="background-color: #2A779E;">

	<div style="clear: both"></div>

	<div class="container"
		style="background-color: white; padding-left: 20px; padding-right: 20px; box-shadow: 0 10px 10px #000;">
		
		<h1>Gomoku Game (<a href="documentation-cn.jsp">中文</a>)</h1>
		<p>
		Gomoku is an abstract strategy board game. Also called Gobang or Five in a Row, it is traditionally played with Go pieces (black and white stones) on a go board with 19x19 intersections; however, because once placed, pieces are not moved or removed from the board; gomoku may also be played as a paper and pencil game. This game is known in several countries under different names.

Black plays first if white did not just win, and players alternate in placing a stone of their color on an empty intersection. The winner is the first player to get an unbroken row of five stones horizontally, vertically, or diagonally.
			<a href="https://en.wikipedia.org/wiki/Gomoku">[Wikipedia]</a><br/><br/>
		</p>
		<i>For more details about rules and gameplay see <a href="https://en.wikipedia.org/wiki/Gomoku">Gomoku</a></i>
		
		<h2>Gomoku Bot API</h2>
		<iframe src="doc/index.html" style="width:100%;height:768px;"></iframe>
		
<p/>		

<h3>Java</h3>
<pre><code class="java">
import nz.ac.massey.cs.ig.games.gomoku.EasyGomokuBoard;
import nz.ac.massey.cs.ig.games.gomoku.GomokuBot;
import nz.ac.massey.cs.ig.games.gomoku.GomokuPosition;

public class DumbBot extends GomokuBot{
	public DumbBot(String id) {
		super(id);
	}

	@Override
	public GomokuPosition nextMove(EasyGomokuBoard game) {
		return game.getAvailableMovesForMe().get(0);
	}

}
</code></pre>

			
		Basically, you have to implement the <code>nextMove</code> method. <code>game.getCurrentBoard </code>allows you can access current board with String "me" 
		represented you, "enemy" represented your opponent and "empty" represented available positions. Traversal each position to determine your next move. 
		
		<p/><p/>
		Note that for security reasons, you can only use basic language features and classes. In particular, access to system classes, input/output 
		and threading is not permitted. An error will be displayed if you try to use this functionality in your bots. 		
</div>
		
		
	</div>

	<!--  import lib to highlight code -->
	<link rel="stylesheet" href="css/github.css">
	<script
		src="js/highlight.pack.js"
		charset="utf-8"></script>
	<script src="js/jquery.dataTables.min.js" charset="utf-8"></script>
	<script>
		hljs.initHighlightingOnLoad()
	</script>
	<!--   -->
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"
		charset="utf-8"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
		charset="utf-8"></script>
	<script src="js/jquery.dataTables.min.js" charset="utf-8"></script>
	<script type="text/javascript"
		src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	<script src="js/authMethod.js" charset="utf-8"></script>
</body>
</html>
