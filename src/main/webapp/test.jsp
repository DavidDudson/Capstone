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

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" >
        <title>${gameName} Testing</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0" />
        <script src="static/js/jquery-1.10.2.min.js"></script>
        <script src="static/js/gameState.js"></script>
        <link rel="stylesheet" type="text/css" href="static/css/style.css">
        <link rel="stylesheet" type="text/css" href="static/css/grid.css">
        <script src="https://apis.google.com/js/api.js"></script>
        <script src = "static/js/test.js"></script>

    </head>

    <body onload="getUserBots()">

        <!--Header-->
        <div id="header">
            <div id="nav_container">


                <div class="container_12" style="padding:0;">

                    <div id="nav_menu" class="left">
                        <div id="logo" class="left">
                            <a href="index.jsp"> ${gameName} </a>
                        </div>
                        <nav class="menu">
                            <a class="toggle-nav" href="#">&#9776;</a>
                            <ul class="list_inline active">
                                <li> <a href="editor.jsp"> Editor </a></li>
                                <li> <a href=""> Survey </a> </li>

                            </ul>

                        </nav>
                    </div>	


                    <div id="user" class="right">
                        <ul class="list_inline">
                            <li id="profilePicture2" class="username">${screenName}</li>
                            <li> <a class="logout" href="index.jsp"> Logout </a> </li>
                            <c:if test="${profilePicture != null}">
                            <li class="profilePictureContent" id="profilePicture3"><img
                                    id="profilePictureURL" src="${profilePicture}"
                                    class="img-responsive img-rounded center-block"
                                    alt="Profile Picture"></li>
                            </c:if>
                        </ul>
                    </div>

                </div>

                <div class="clear"> </div>

            </div>
        </div>
        <!--End Header-->


        <div class="container_12">
            <div id="content">
                <div id="sidebar_left" class="sidebar left">
                    <div id="my_bots" class="sidebar_box">
                        <div class="sidebar_box_inner">

                            <div class="sidebar_head">
                                My Bots
                            </div>

                            <div class="sidebar_content">
                                <ul id="userBots" class="list_block">
                                </ul>
                            </div>


                        </div>  
                    </div>

                    <div id="built-in_bots" class="sidebar_box">
                        <div class="sidebar_box_inner">
                            <div class="sidebar_head">
                                Built-in Bots
                            </div>

                            <div class="sidebar_content">
                                <ul class="list_block">
                                    <li id="firstSquareBot" value="FirstSquareBot" class="bot" onclick="setBots('FirstSquareBot')">First square bot</li>
                                    <li id="lastSquareBot" value="LastSquareBot" class="bot" onclick="setBots('LastSquareBot')">Last square bot</li>
                                    <li id="betterBot" value="BetterBot" class="bot" onclick="setBots('betterBot')">better bot</li>
                                    <li id="dreadnought" value="Dreadnought" class="bot" onclick="setBots('dreadnought')">Dreadnought</li>
                                </ul>
                            </div>

                        </div>
                    </div>

                    <div id="shared_bots" class="sidebar_box">
                        <div class="sidebar_box_inner">
                            <div class="sidebar_head">
                                Shared Bots
                            </div>

                            <div class="sidebar_content">
                                <div id="search">
                                    <form method="">
                                        <input class="search_box" type="text" value="Search..." onBlur="if (this.value == '') {
                                                                    this.value = 'Search...'
                                                                }" onFocus="if (this.value == 'Search...') {
                                                                            this.value = ''
                                                                }" />

                                        <input class="search_btn" type="submit" value="" />
                                    </form>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>

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

        <script>
            $(document).ready(function () {
                $('.toggle-nav').click(function (e) {
                    $(this).toggleClass('active');
                    $('.menu ul').toggleClass('active');

                    e.preventDefault();
                });
            });
        </script>
    </body>
</html>