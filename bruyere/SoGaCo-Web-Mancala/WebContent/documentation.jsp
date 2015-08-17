<%-- 
    Created on : 23 May 2015
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

<title>Documentation</title>
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
		<h1>Mancala Game Rules</h1>
		<p>
			Mancala is a traditional African board game with a very long history and many variants. More information can be found on the <a href="http://en.wikipedia.org/wiki/Mancala" target="_blank">Mancala wikipedia page</a>.
			<p/>
			The game board consists of two rows of six pits, with a long "mancala" on each end. The pits and the mancalas of either player are 
			blue and red, respectively. The game is played clockwise. Initially, there are four "stones" in each pit, excluding the mancalas. 
			The blue player starts the game. During a turn, a player grabs all of the stones in a pit and then drops them one by one into succeeding pits or his or her own mancala
			in clockwise direction. Note that the mancala of the opponent is skipped. The player who accumulates most stones in his or her mancala wins. The game ends when all pits of one player are empty.
			</p>
			There are some additional special rules. 
			<ol>
				<li>If the last stone is dropped into the mancala of the player whose turn it is, then this player gets a free turn.</li>
				<li>If the last stone is dropped into an empty pit of the player whose turn it is, this player captures this stones and all stones in the pit directly opposite.</li>
				<li>The player who still has stones in his or her pits when the game ends gets all of those stones (this means that they can be moved into the mancala of this player).</li>
			</ol>
		
		<h2>Mancala Bot API</h2>
	
	
		When you implement a Mancala playing bot with acertain name (lets say, MyBot), you start with a template like this.
<p/>		
<pre><code class="java">
public class MyBot extends MancalaBot {
	public MyBot(String botId) {
		super(botId);
	}

	/**
	 * Given the mancala bot, select the pit to play.
	 */
	@Override
	public Integer nextMove(Mancala board) {
		..	 
	}
};
</code></pre>
			
		Basically, you have to implement the <code>nextMove(Mancala)</code> method, and return the position of the next pit to be played. Your first pit has the number 
		0. This means that if the move method returns 0, your next turn will start with taking all stones out of your first pit. The other pits have positions
		1,2,3,4 and 5. If your method returns a value less than 0 or greater than 5, and error will be produced indicating that this was an illegal move, and 
		you will lose the game. You can also only play a number such that the respective pit has at least one stone left. 
		
		You will need some information about the board to make good decisions which pit to play next. For this purpose, the <code>Mancala</code> type representing the board 
		provides a couple of methods. 
<p/>
<pre><code class="java">
public final class Mancala {	
	/**
	 * Get the stones in one of my pits.
	 * @param position the position of the pit, a value between 0 (first pit) and 5 (last pit)
	 * @return the number of stones in this pit
	 */
	public int getStonesInMyPit(int position) {..}	
	/**
	 * Get the stones in one of the other players pits.
	 * @param position the position of the pit, a value between 0 (first pit) and 5 (last pit)
	 * @return the number of stones in this pit
	 */
	public int getStonesInOtherPit(int position) {..}
	/**
	 * Get the stones in my mancala.
	 * @return the number of stones in my mancala
	 */
	public int getStonesInMyMancala() {..}
	/**
	 * Get the stones in the other players mancala.
	 * @return the number of stones in the other mancala
	 */
	public int getStonesInOtherMancala() {..}
}
</code></pre>

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
