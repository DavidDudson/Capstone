<%-- 
    Created on : 29 May 2015
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
		<h1>素数游戏规则</h1>
		<p>
			素数游戏是一款基于数学的趣味游戏，它是由Manfred Meyer教授和其他人开发的。它的规则很简单：在开始的时候，棋盘是由1到100的数字组成的。在每一个回合，玩家选择从棋盘中选一个从没有选过的数字。然后玩家获得跟这个数字等值的分数。此外，对手玩家将会获得所有还在棋盘上该数字的因数的分值。
			<p/>
			例如， 玩家1开始了游戏，并且选择了最大数字，100。因此，玩家1将会获得100分。 然后玩家2就会获得所有100的因数作为分数：1,2,4,5,10,20,25和50, 加一起就是<strong>117分</strong>！一个更好的策略就是玩家1开始选择97. 那样玩家2就会只得1分因为97的因数只有1. 需要注意的是在每一回合结束后，玩家1所选的数字和玩家2得到的因数都会从棋盘去除。
		<h2>素数游戏机器人程序API</h2>
		当你继承一个素数游戏的机器人程序，需要提供机器人的名字（例如“我的机器人”）， 一个模板将会提供给你。这个提供的机器人程序是完全可以实现的- 你可以直接运行这个机器人和我们内置机器人对战。 然而，这个模板机器人并不是很聪明的：它将在每一回合挑选最小的数字。
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
		所以基本上， 你需要做的是实现<code>nextMove</code>函数， 从list里面选择一个数字。这个list永远不会是空的， 但你所选择的数字必须在这个list里， 否则构建这个机器人一定会失败，或你直接输掉这个游戏。
	
		<p/><p/>
		出于系统安全考虑， 你只能应用基本的语言库。 例如， system类，input/output，和线程是不被允许的。如果你调用这些类，错误信息会提示你的。		
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
