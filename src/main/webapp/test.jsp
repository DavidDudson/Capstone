<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>

<html ng-app="app" ng-controller="appCtrl">
<head>
    <title>Star Battle Testing</title>

    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/editor.css">

    <script src="https://apis.google.com/js/api.js"></script>
    <script src="static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="static/js/lib/jquery-ui.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/bots.js"></script>
    <script src="static/js/build.js"></script>
    <script src="static/js/user.js"></script>
    <script src="static/js/sidebar.js"></script>
    <script src="static/js/bot_selector.js"></script>
    <script src="static/js/notification_bar.js"></script>
    <script src="static/js/game.js"></script>
    <script src="static/js/test.js"></script>
    <script src="static/js/lib/showErrors.min.js"></script>
</head>
<body ng-init="createUser('${screenName}','${profilePicture}'); createBuiltInBots();" ng-app="app">

<!--Navigation bar-->
<page-header></page-header>
<!--End Navigation bar-->

<div>
    <progressbar class="progress-striped"
                 ng-class="notificationBar.active"
                 ng-value="notificationBar.progress" type="{{notificationBar.type}}"
                 style="width:100%; height: 40px"><b>{{notificationBar.text}}</b></progressbar>
</div>

<div class="container_10" style="color:white" ng-controller="testCtrl">
    <div id="content">
        <!--Main container-->
        <div class="container">
            <div class="row">
                <sidebar bot_selector="botSelector" user_bots built_in_bots shared_bots></sidebar>

                <section class="col-lg-7 col-md-10 col-md-pull-0 col-sm-10 col-sm-pull-1">
                    <div id="main_content">
                        <div id="player_one" class="left">
                            <ul>
                                <li><b> {{botSelector.bots[0] ? botSelector.bots[0].name : "Select Bot 1"}} </b></li>
                            </ul>
                            <ul class="grid_box">
                                <%for (int i = 0; i < 10; i++) {%>
                                <%for (int j = 0; j < 10; j++) {%>
                                <li id="a<%=i * 10 + j%>"></li>
                                <%}%>
                                </br>
                                <%}%>
                            </ul>
                        </div>

                        <div id="player_two" class="left">
                            <ul>
                                <li><b> {{botSelector.bots[1] ? botSelector.bots[1].name : "Select Bot 2"}} </b></li>
                            </ul>

                            <ul class="grid_box">
                                <%for (int i = 0; i < 10; i++) {%>
                                <%for (int j = 0; j < 10; j++) {%>
                                <li id="b<%=i * 10 + j%>"></li>
                                <%}%>
                                </br>
                                <%}%>
                            </ul>
                        </div>
                    </div>
                </section>
                <section class="col-lg-3 col-lg-offset-0 col-md-4 col-md-offset-1 col-sm-5 col-sm-offset-2">

                    <div id="sidebar_right" class="sidebar right">

                        <div id="game_controls" class="sidebar_box">
                            <div class="right-inner">

                                <div class="sidebar_head">
                                    Game Controls
                                </div>

                                <div class="sidebar_content">
                                    <div id="controls">
                                        <div class="btn-group">
                                            <button type="button" ng-click="game.restart()" class="btn btn-primary">
                                                <i class="fa fa-fast-backward"></i></button>
                                            <button type="button" ng-click="game.step_backward()"
                                                    class="btn btn-primary">
                                                <i class="fa fa-step-backward"></i></button>
                                            <button type="button" ng-click="game.play_pause(botSelector)"
                                                    class="btn btn-primary">
                                                <i class="fa"
                                                   ng-class="game.inProgress && !game.paused ?  'fa-pause' : 'fa-play'"></i>
                                            </button>
                                            <button type="button" ng-click="game.step_forward()"
                                                    class="btn btn-primary">
                                                <i class="fa fa-step-forward"></i></button>
                                            <button type="button" ng-click="game.end()" class="btn btn-primary">
                                                <i class="fa fa-fast-forward"></i></button>
                                        </div>

                                        <%--<div id="set_two" class="slow_fast">--%>
                                        <%--<div class="left"> Slow</div>--%>
                                        <%--<section>--%>
                                        <%--<div id="slider" style="width: 155px"></div>--%>
                                        <%--</section>--%>
                                        <%--<div class="right"> Fast</div>--%>
                                        <%--<div class="clear"></div>--%>

                                        <%--<script>--%>
                                        <%--$(function () {--%>
                                        <%--$("#slider").slider({--%>
                                        <%--range: "min",--%>
                                        <%--value: 200,--%>
                                        <%--min: 1,--%>
                                        <%--max: 1000,--%>
                                        <%--slide: function (event, ui) {--%>
                                        <%--gameSpeed = 1000 - ui.value;--%>
                                        <%--if (playGame) {--%>
                                        <%--clearInterval(myGame);--%>
                                        <%--myGame = setInterval(function () {--%>
                                        <%--nextMove();--%>
                                        <%--}, gameSpeed);--%>
                                        <%--}--%>
                                        <%--}--%>
                                        <%--});--%>
                                        <%--if (playGame) {--%>
                                        <%--setTimeout(nextMove, gameSpeed);--%>
                                        <%--playPause();--%>
                                        <%--}--%>
                                        <%--});--%>
                                        <%--</script>--%>
                                        <%--</div>--%>
                                    </div>

                                    <div id="results">
                                        <table width="100%" border="0" cellpadding="0" cellspacing="0"
                                               class="results-list">
                                            <tr>
                                                <td width="48%">SPACESHIPS LEFT</td>
                                                <td width="26%" class="t-center">PLAYER 1 <br>
                                                    <img src="static/images/layer1-ship1.png"></td>
                                                <td width="26%" class="t-center">PLAYER 2 <br>
                                                    <img src="static/images/layer2-ship2.png"></td>
                                            </tr>
                                            <tr>
                                                <td>Alien Mother Ship<br><img src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"></td>
                                                <td id="p1motherShip" class="b-left">1</td>
                                                <td id="p2motherShip" class="b-left">1</td>
                                            </tr>
                                            <tr>
                                                <td>Alien Carrier Ship<br> <img src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"></td>
                                                <td id="p1carrier" class="b-left">2</td>
                                                <td id="p2carrier" class="b-left">2</td>
                                            </tr>
                                            <tr>
                                                <td>Alien Destroyer<br><img src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"></td>
                                                <td id="p1destroyer" class="b-left">2</td>
                                                <td id="p2destroyer" class="b-left">2</td>
                                            </tr>
                                            <tr>
                                                <td>Alien Fighter Ship<br><img src="static/images/layer1-ship.png"><img
                                                        src="static/images/layer1-ship.png"></td>
                                                <td id="p1fighter" class="b-left">2</td>
                                                <td id="p2fighter" class="b-left">2</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
</body>
</html>