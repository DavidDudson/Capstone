<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>

<html ng-app="app" ng-controller="appCtrl">
    <head>
        <title>{{app.name}} Testing</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" >
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0" />

        <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
        <link rel="stylesheet" type="text/css" href="static/css/style.css">
        <link rel="stylesheet" type="text/css" href="static/css/grid.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

        <script src="https://apis.google.com/js/api.js"></script>
        <script src="static/js/jquery-1.11.3.min.js"></script>
        <script src="static/js/jquery-ui.min.js"></script>
        <script src="static/js/gameState.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
        <script src="static/js/app.js"></script>
        <script src="static/js/editor.js"></script>
        <script src="static/js/test.js"></script>
        <script src="static/js/showErrors.min.js"></script>
    </head>
    <body ng-init="user.initialize('${screenName}','${profilePicture}');" ng-app="app">

    <!--Navigation bar-->
    <page-header></page-header>
    <!--End Navigation bar-->

    <div class="container_12">
        <div id="content">


            <div id="main_content">
                <div id="player_one" class="left">
                    <ul>
                        <li> <b> Player 1 </b> </li>
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
                        <li> <b> Player 2 </b> </li>
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

            <div id="sidebar_right" class="sidebar right">

                <div id="game_controls" class="sidebar_box">
                    <div class="right-inner">

                        <div class="sidebar_head">
                            Game Controls
                        </div>

                        <div class="sidebar_content">
                            <div id="controls">
                                <div id="set_one">
                                    <ul class="list_inline">
                                        <li> <a class="fast_prev" onClick="startGame()"> </a> </li> 
                                        <li> <a class="prev" onClick="prevMove()"> </a> </li>
                                        <li> <a class="pause" onClick="playPause()"> </a> </li> 
                                        <li> <a class="forward" onClick="nextMove()"> </a> </li> 
                                        <li> <a class="fast_forward" onClick="endGame()"> </a> </li> 
                                    </ul>
                                </div>

                                <div id="set_two" class="slow_fast">
                                    <div class="left"> Slow </div>
                                    <section> <div id="slider"style="width: 155px"> </div></section>
                                    <div class="right"> Fast </div>
                                    <div class="clear"> </div>

                                    <script>
                                    $(function () {
                                        $("#slider").slider({
                                            range: "min",
                                            value: 200,
                                            min: 1,
                                            max: 1000,
                                            slide: function (event, ui) {
                                                gameSpeed = 1000 - ui.value;
                                                if (playGame) {
                                                    clearInterval(myGame);
                                                    myGame = setInterval(function () {
                                                        nextMove();
                                                    }, gameSpeed);
                                                }
                                            }
                                        });
                                        if (playGame) {
                                            setTimeout(nextMove, gameSpeed);
                                            playPause();
                                        }
                                    });
                                </script>
                                </div>
                            </div>

                            <div id="results">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="results-list">
                                    <tr>
                                        <td width="48%">SPACESHIPS LEFT</td>
                                        <td width="26%" class="t-center">PLAYER 1 <br>
                                            <img src="static/images/layer1-ship1.png"></td>
                                        <td width="26%" class="t-center">PLAYER 2 <br>
                                            <img src="static/images/layer2-ship2.png"></td>
                                    </tr>
                                    <tr>
                                        <td>Alien Mother Ship<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
                                        <td id="p1motherShip" class="b-left">1</td>
                                        <td id="p2motherShip" class="b-left">1</td>
                                    </tr>
                                    <tr>
                                        <td>Alien Carrier Ship<br> <img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
                                        <td id="p1carrier" class="b-left">2</td>
                                        <td id="p2carrier" class="b-left">2</td>
                                    </tr>
                                    <tr>
                                        <td>Alien Destroyer<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
                                        <td id="p1destroyer" class="b-left">2</td>
                                        <td id="p2destroyer" class="b-left">2</td>
                                    </tr>
                                    <tr>
                                        <td>Alien Fighter Ship<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
                                        <td id="p1fighter" class="b-left">2</td>
                                        <td id="p2fighter" class="b-left">2</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="clear"> </div>
        </div>

        <div id="footer">


        </div>
    </div>
    </body>
</html>