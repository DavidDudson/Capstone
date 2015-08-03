<%-- 
    Created on : 25 May 2015
    Author     : Jens Dietrich
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
		
		<h1>PrimeGame Game Rules (<a href="documentation-cn.jsp">中文</a>)</h1>
		<p>
			The PrimeGame is a simple mathematical board game developed by Prof Manfred Meyer and others. Its rules are very simple: the board initially consists of numbers from 
			1 to 100, and in each turn, the player selects a number from the board that has not yet been played. This player then gets this number added to his or her score. 
			However, the opponent gains all factors of this number that are still on the board! 
			<p/>
			For instance, player 1 starts the game and plays the largest number available, 100. Therefore, player 1 will gain 100 points. However, player 2 will get all the factors of 100: 
			1,2,4,5,10,20,25 and 50, this is <strong>117 points</strong> ! A better strategy would be to play 97. Then player 2 would only get 1 point as this is the only factor of 97. 
			Note that after each move, the number played and its factors are removed from the board.
		
		<h2>PrimeGame Bot API</h2>
	
	
		When you implement a PrimeGame playing bot with a certain name (lets say, MyBot), you start with a template like this.
		This bot is fully functional - you can build and test it by playing against built-in bots. However, it is not very smart: 
		it always plays the smallest number that is still on the board.
<p/>		

<h3>Java</h3>
<pre><code class="java">
import java.util.List;
import nz.ac.massey.cs.ig.games.primegame.PGBot;
public class MyBot extends PGBot {
	public MyBot (String botId) {
    	super(botId);
	}
	@Override public Integer nextMove(List<Integer> game) {
		return game.get(0);
	}
}
</code></pre>

<h3>Python</h3>
<pre><code class="python">
# 'game' is a list of currently available numbers
# the function should return one of these numbers
def nextMove(game):
	return game[0]
</code></pre>

			
		Basically, you have to implement the <code>nextMove</code> method, and select and return a number from this list.
		This list will never be empty, but the number you select has to be in this list. Otherwise either the build fails, or 
		you will automatically lose the games you play with this bot.
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
