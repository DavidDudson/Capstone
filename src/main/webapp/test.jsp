<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    pageContext.setAttribute("screenName",
            session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture",
            session.getAttribute("userPicture"));
    nz.ac.massey.cs.ig.core.services.Services services = (nz.ac.massey.cs.ig.core.services.Services) application
            .getAttribute(nz.ac.massey.cs.ig.core.services.Services.NAME);
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
        <meta name="description" content="">
        <meta name="author" content="">
        <script src="static/js/jquery-1.7.2.min.js"></script>
        <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
        <script src="static/js/gameState.js"></script>
        <link rel="stylesheet" type="text/css" href="static/css/style.css">
        <link rel="stylesheet" type="text/css" href="static/css/grid.css">
        <script src="https://apis.google.com/js/api.js"></script>

    </head>
    <script lang="JavaScript">

        var bot1;
        var bot2;
        var currentSelectedBot;

        jsonMoves = {};
        jsonUserBots = {};
        jsonBuiltInBots = {};

        var currentMoveIndex = 0;
        var player1Move = true;
        var gameEnded = false;
        var gameStart = true;
        var gameSpeed = 500;
        var playGame = false;
        var myGame;


        
        var commons = {
                    DEBUG : true,
                    handleError : function(textStatus, error) {
                        var err = textStatus + ", " + error;
                        console.log("ERROR: " + err);
                    },
                    warn : function(message) {
                        console.log("WARN: " + message);
                    },
                    debug : function(message) {
                        if (this.DEBUG) {
                            console.log("DEBUG: " + message);
                        }
                    },
                    notifyUser : function(title,message) {
                        $("#errordialog-content").html(message);
                        $("#errordialog").dialog({title: title,text: message,modal: true,width:350,height:300});
                    },
                    formatServerError : function(error,maxStackTraceSize) {
                        var botEncounteringError = (error.winner==1)?2:1;
                        var errorInfo = "<b>A server error occured during execution of bot " + botEncounteringError + "</b><br/>" +
                                "error type: " + error.type +"<br/>";
                        if (error.message) errorInfo = errorInfo + "message: " + error.message +"<br/>";
                        errorInfo = errorInfo+"</hr>" + "stacktrace:<br/>";
                        var stackTraceSize = Math.min(error.stacktrace.length, maxStackTraceSize);
                        for (var i=0;i<stackTraceSize;i++) {
                            errorInfo = errorInfo + error.stacktrace[i] + "<br/>";
                        }
                        return errorInfo;
                    }
                };

        function setBots(botID){
            if(bot1 === null){
                bot1 = document.getElementById(botID).value;
            } else if (bot2 === null){
                bot2 = document.getElementByID(botID).value;
            }else{   
                bot2 = bot1;
                bot1 = document.getElementById(botID).value;
            }
            alert(bot1 + " " + bot2 );
        }
        function nextMove() {

            if (currentMoveIndex < jsonMoves.moves.length) {
                var currentMove = jsonMoves.moves[currentMoveIndex];
                if (currentMove.wasPlayer1) {
                    var posA = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(posA);
                    if (currentMove.wasShip) {
                        document.getElementById(posA).innerHTML += "<img src='static/images/hit.png'/>";

                        var sunkElements = currentMove.sunk.length;
                        if (sunkElements > 1) {
                            alterBoatLiveGUI(sunkElements, "p1");
                        }
                    } else {
                        document.getElementById(posA).innerHTML += "<img src='static/images/miss.png'/>";

                    }

                } else {
                    var posB = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(posB);
                    if (currentMove.wasShip) {
                        document.getElementById(posB).innerHTML += "<img src='static/images/hit.png'/>";

                        var sunkElements = currentMove.sunk.length;

                        if (sunkElements > 1) {
                            alterBoatLiveGUI(sunkElements, "p2", "dec");
                        }
                    } else {
                        document.getElementById(posB).innerHTML += "<img src='static/images/miss.png'/>";

                    }
                }
                currentMoveIndex++;
            }else{
                gameEnded = true;
                
            }
        }
        function prevMove() {
            if (currentMoveIndex > 0) {
                currentMoveIndex--;
                
                var currentMove = jsonMoves.moves[currentMoveIndex];
                var pos;
                var sunkElements = currentMove.sunk.length;
                if (currentMove.wasPlayer1) {
                    pos = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(pos).innerHTML = "";
                    if (sunkElements > 0) {
                        alterBoatLiveGUI(sunkElements, "p1", "inc");

                    }
                } else {
                    pos = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(pos).innerHTML = "";
                    if (sunkElements > 0) {
                        alterBoatLiveGUI(sunkElements, "p2", "inc");

                    }
                }

            }else{
                gameStart = true;
                
            }
        }

        function endGame() {
            while (currentMoveIndex !== jsonMoves.moves.length) {
                nextMove();
            }
        }
        function startGame() {
            while (currentMoveIndex !== 0) {
                prevMove();
            }
        }
        function playPause() {
                    if(playGame && !gameStart){
                        makeBotGame();
                    }
            
                    playGame = !playGame;
                    if (playGame) {
                        myGame = setInterval(function () {
                            nextMove();
                        }, gameSpeed);
                    } else {
                        clearInterval(myGame);
                    }
        }

        function makeBotGame() {
            var url = "creategame_b2b";
            var bot1Id = "FirstSquareBot";
            var bot2Id = "LastSquareBot";
            var data = "" + bot1Id + "\n" + bot2Id + "\n";
            var jqxhr = $.ajax({
                url: url,
                type: "POST",
                data: data,
                dataType: "json",
                success : function () {
                    $.getJSON(jqxhr.getResponseHeader("Location"), function(data){
                        jsonMoves = data;



                    });
                }
            });
        };
        
        function getBots() {

            var userBotsURL = "userbots/__current_user";
            var buildBotsURL = "builtinbots";
            
            $.ajax({
                url: userBotsURL,
                type: "GET",
                dataType: "json",
                success : function (data) {
                    jsonUserBots = data;
                }
            });
            
            
        };

    </script>

    <body onload="getBots()">

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

                                <li> <div class="menu-on"> <a href=""> My Bots </a> </div> </li>
                                <li> <div class="menu-on">  <a href=""> Built-in Bots </a> </div> </li>
                                <li> <div class="menu-on">  <a href=""> Shared Bots </a> </div> </li>
                            </ul>

                        </nav>
                    </div>	


                    <div id="user" class="right">
                        <ul class="list_inline">
                            <li> <a d="profilePicture2" class="username" href="">${screenName}</a> </li>
                            <li> <a class="logout" href="index.jsp"> Logout </a> </li>
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


        <div class="container_12">
            <div id="content">
                <div id="sidebar_left" class="sidebar left">
                    <div id="my_bots" class="sidebar_box">
                        <div class="sidebar_box_inner">

                            <div class="sidebar_head">
                                My Bots
                            </div>

                            <div class="sidebar_content">
                                <ul class="list_block">
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot more"> <a href=""> more... </a> </li>
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
                                    <li id="firstSquareBot" value="FirstSquareBot" class="bot" onclick="setBots('firstSquareBot')">First square bot</li>
                                    <li id="lastSquareBot" value="LastSquareBot" class="bot" onclick="setBots('lastSquareBot')">Last square bot</li>
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