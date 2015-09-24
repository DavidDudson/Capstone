<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="nz.ac.massey.cs.ig.core.services.Services" %>
<%
    Services services = (Services) application.getAttribute(Services.NAME);

    pageContext.setAttribute("isDebug",services.getConfiguration().isDebug());
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
    pageContext.setAttribute("gameName", services.getGameSupport().getName());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${gameName} About</title>
    <script type="text/javascript" src="static/js/Blockly/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/logic.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/loops.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/math.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/text.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/lists.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/variables.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/procedures.js"></script>
    <script type="text/javascript" src="static/js/Blockly/java/customBlocks.js"></script>
    <script type="text/javascript" src="static/js/Blockly/messages.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/logic.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/loops.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/math.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/text.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/lists.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/variables.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/procedures.js"></script>
    <script type="text/javascript" src="static/js/Blockly/blocks/customBlocks.js"></script>

    <script src="static/js/jquery-1.10.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>

    <link rel="stylesheet" type="text/css" href="static/css/editor.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <link rel="stylesheet" type="text/css" href="static/css/about.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="static/js/editor.js"></script>
    <script src="static/js/test.js"></script>
</head>
<body onload="setupWorkspace(); getUserBots(addUserBotsToUI)">

<!--Header-->
<div id="header">
    <div id="nav_container">
        <div class="container_12" style="padding:0;">
            <div id="nav_menu" class="left">
                <nav class="menu">
                    <a class="toggle-nav" href="#">&#9776;</a>
                    <ul class="list_inline active">
                        <li><a href="index.jsp"> ${gameName} </a></li>
                        <li><a href="editor.jsp"> Editor </a></li>
                        <li ><a href="test.jsp"> Test </a></li>
                        <li><a href="http://tinyurl.com/ptbweh9"> Survey </a></li>
                    </ul>

                </nav>
            </div>
            <div id="user" class="right">
                <ul class="list_inline">
                    <li> <a d="profilePicture2" class="username" href="">${screenName}</a> </li>
                    <li class="logout" href="index.jsp"> Logout </li>
                    <c:if test="${profilePicture != null}">
                        <li class="profilePictureContent" id="profilePicture3"><img
                                id="profilePictureURL" src="${profilePicture}"
                                class="img-responsive img-rounded center-block"
                                style="width: 40px; margin: 5px;" alt="Profile Picture"></li>
                    </c:if>
                </ul>
            </div>
        </div>
        <div class="clear"> </div>
    </div>

</div>
<!--End Header-->
<div class="container_13">
    <h1>StarBattle - Learn Coding Through Gaming</h1>
    <p>
        Starbattle is an online educational platform developed by five software engineering students at <a href="http://www.massey.ac.nz">Massey University</a> for their final year capstone project. The idea is that primary school up to early high school aged students can use a web based development environment to learn coding.
        They can use this platform to code bots to play a version of the popular Milton Bradley game ‘battleships’. They can battle their bots against built in bots or bots created by their friends.
        We hope that using starbattle will engage children in learning coding and furthering their understanding of computer science as a whole.
    </p>
    <p>
        The advantages for education providers is that the platform requires no additional setup, installation or knowledge of coding. They can simply use the application from within any web browser. Teaching children how to code through gamification has proven to be highly motivating for the student and improve their efficacy. And our hope is that through the use of our application the children that use it will continue to discover the possibilities of code.
    </p>
    <p>
        <h3>Special thanks to:</h3>
        <ul> 
            <li>Massey University’s <a href="http://sogaco.massey.ac.nz">SoGaCo platform</a></li>
            <li>John Toebes of <a href="http://www.extremenetworks.com">Extreme Networks</a></li>
            <li>Google <a href="https://developers.google.com/blockly">Blockly</a></li>
        </ul>
       
    </p>

</div>


</body>
</html>
