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
		
		<h1>五子棋</h1>
		<p>
		五子棋是一种两人对弈的纯策略型棋类游戏，通常双方分别使用黑白两色的棋子，下在棋盘直线与横线的交叉点上，先形成5子连线者获胜。
棋具与围棋通用，起源于中国上古时代的传统黑白棋种之一。主要流行于华人和汉字文化圈的国家以及欧美一些地区。
容易上手，老少皆宜，而且趣味横生，引人入胜；不仅能增强思维能力，提高智力，而且富含哲理，有助于修身养性。已在各个游戏平台有应用
			<a href="http://baike.baidu.com/link?url=kNJz_4L95PHyisnUHpGYeiGctZAOS7Bm9IuzeAhqBaJ4VRNrQXoBsDFlODQjxJ6-4YGrHLbvU_kbWCMlD413ea">[百度百科]</a><br/><br/>
		</p>
		<i>更多游戏规则和历史请百度一下<a href="http://baike.baidu.com/link?url=kNJz_4L95PHyisnUHpGYeiGctZAOS7Bm9IuzeAhqBaJ4VRNrQXoBsDFlODQjxJ6-4YGrHLbvU_kbWCMlD413ea">五子棋</a></i>
		
		<h2>五子棋机器人 API</h2>
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

		所以基本上， 你需要做的是实现<code>nextMove</code>函数，<code>game.getCurrentBoard</code> 这个函数可以让你调取当前棋盘各个位置的状态。“me” 表示是你自己的棋子，“enemy”表示是对手棋子，“empty”表示的是
		没有棋子的位置。
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
