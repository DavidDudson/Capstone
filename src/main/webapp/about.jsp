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
<html ng-app="app" ng-controller="appCtrl">
<head>
    <meta charset="utf-8">
    <title>{{app.name}} About</title>
    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <link rel="stylesheet" type="text/css" href="static/css/about.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="static/js/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/editor.js"></script>
    <script src="static/js/test.js"></script>
    <script src="static/js/showErrors.min.js"></script>
</head>
<body ng-init="user.initialize('${screenName}','${profilePicture}');" Ng-app="app">

<!--Navigation bar-->
<page-header></page-header>
<!--End Navigation bar-->

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
