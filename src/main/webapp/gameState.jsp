
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="battleShipGame.Section"%>
<%@page import="java.util.Arrays"%>
<%@page import="battleShipGame.ShipLinkedList"%>
<%@page import="battleShipGame.testBot"%>
<%@page import="battleShipGame.Grid"%>

<%!
    int SIZE = 10;
    testBot bot1 = new testBot();
    Grid bot1Grid;
    int[][] bot1Moves = new int[100][4];
    testBot bot2 = new testBot();
    Grid bot2Grid;
    int[][] bot2Moves = new int[100][4];
    public int[][] gameRun(Grid grid, testBot bot) {
        int[][] botMoves = new int[100][4];
        int movesIndex = 0;
        int[] retVals = new int[4];
        int deadShips = 0;
        while (deadShips != grid.getShips().size()) {
            retVals = bot.runMove(grid.getGrid());
            botMoves[movesIndex][0] = retVals[0];
            botMoves[movesIndex][1] = retVals[1];
            botMoves[movesIndex][2] = grid.getGrid()[retVals[0]][retVals[1]].getSectionStatus();
            botMoves[movesIndex][3] = grid.playMove(retVals[0], retVals[1]);
            if (botMoves[movesIndex][3] != 0) {
                deadShips++;
            }
            movesIndex++;
        }
        if (movesIndex < 99) {
            botMoves[movesIndex][0] = -1;
            botMoves[movesIndex][1] = -1;
        }
        return botMoves;
    }
%>

<%
    bot1Grid = new Grid(SIZE);
    bot1Grid.generateShips();
    bot1Grid.loadGrid();
    bot2Grid = new Grid(SIZE);
    bot2Grid.generateShips();
    bot2Grid.loadGrid();
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" >
        <title>  </title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0" />
        <meta name="description" content="">
        <meta name="author" content="">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="static/js/jquery-1.7.2.min.js"></script>
        <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
        <script src="static/js/gameState.js"></script>
        <link rel="stylesheet" type="text/css" href="static/css/style.css">
        <link rel="stylesheet" type="text/css" href="static/css/grid.css">


        <script lang="JavaScript">
            var bot1MoveCount = 0;
            var bot2MoveCount = 0;
            var player1Move = true;
            var gameEnded = false;
            var gameStart = true;
            var gameSpeed = 500;
            var playGame = false;
            var myGame;
            function nextMove() {
                if (bot1MoveCount === 0) {
                    gameStart = !gameStart;
                }
                if (player1Move) {
                    var yCoordA = bot1Moves[bot1MoveCount][0];
                    var xCoordA = bot1Moves[bot1MoveCount][1];
                    var posA = "a" + (yCoordA * 10 + xCoordA);
                    document.getElementById(posA).style.backgroundColor = retHitOrMiss(gameArrayA[yCoordA][xCoordA]);
                    if (bot1Moves[bot1MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot1Moves[bot1MoveCount][3], "p1");
                    }
                    bot1MoveCount++;
                }
                else {
                    var yCoordB = bot2Moves[bot2MoveCount][0];
                    var xCoordB = bot2Moves[bot2MoveCount][1];
                    var posB = "b" + (yCoordB * 10 + xCoordB);
                    document.getElementById(posB).style.backgroundColor = retHitOrMiss(gameArrayB[yCoordB][xCoordB]);
                    if (bot2Moves[bot2MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot2Moves[bot2MoveCount][3], "p2");
                    }
                    bot2MoveCount++;
                }
                if (bot2Moves[bot2MoveCount][0] === -1) {
                    alert("bot2 wins");
                    alterBoatLiveGUI(bot2Moves[bot2MoveCount][3], "p2", "dec");
                    gameEnded = true;
                }
                if (bot1Moves[bot1MoveCount][0] === -1 || bot1MoveCount === 99) {
                    alert("bot1 wins");
                    alterBoatLiveGUI(bot1Moves[bot1MoveCount][3], "p1", "dec");
                    gameEnded = true;
                }
                player1Move = !player1Move;
            }
            function prevMove() {
                player1Move = !player1Move;
                if (player1Move) {
                    bot1MoveCount--;
                    var yCoordA = bot1Moves[bot1MoveCount][0];
                    var xCoordA = bot1Moves[bot1MoveCount][1];
                    var posA = "a" + (yCoordA * 10 + xCoordA);
                    document.getElementById(posA).style.backgroundColor = "#A6B8ED";
                    if (bot1Moves[bot1MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot1Moves[bot1MoveCount][2], "p1", "inc");
                    }
                } else if (bot2MoveCount !== 0) {
                    bot2MoveCount--;
                    var yCoordB = bot2Moves[bot2MoveCount][0];
                    var xCoordB = bot2Moves[bot2MoveCount][1];
                    var posB = "b" + (yCoordB * 10 + xCoordB);
                    document.getElementById(posB).style.backgroundColor = "#A6B8ED";
                    if (bot2Moves[bot2MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot2Moves[bot2MoveCount][2], "p2", "inc");
                    }
                }
                if (bot1MoveCount === 0 && player1Move) {
                    bot2MoveCount = 0;
                    var yCoordB = bot2Moves[bot2MoveCount][0];
                    var xCoordB = bot2Moves[bot2MoveCount][1];
                    var posB = "b" + (yCoordB * 10 + xCoordB);
                    document.getElementById(posB).style.backgroundColor = "#A6B8ED";
                    gameStart = true;
                }
            }
            function endGame() {
                while (!gameEnded) {
                    nextMove();
                }
            }
            function startOfGame() {
                while (!gameStart) {
                    prevMove();
                }
            }
            function playPause() {
                playGame = !playGame;
                if (playGame) {
                    myGame = setInterval(function () {
                        nextMove();
                    }, gameSpeed);
                } else {
                    clearInterval(myGame);
                }

            }
        </script>
    </head>
    <body>
        <div class="container_12">
            <div id="header">
                <div id="nav_container">
                    <div id="nav_menu" class="left">
                        <div id="logo" class="left">
                            <a href="index.html"> Battleship </a>
                        </div>
                        <nav class="menu">
                            <a class="toggle-nav" href="#">&#9776;</a>
                            <ul class="list_inline active">
                                <li> <a href="easyJ.html"> Editor </a> </li>
                                <li> <a href=""> Help </a> </li>
                                <li> <a href=""> Community </a> </li>
                                <li> <a href=""> Survey </a> </li>
                                <li> <a href=""> About </a> </li>

                                <li> <div class="menu-on"> <a href=""> My Bots </a> </div> </li>
                                <li> <div class="menu-on">  <a href=""> Built-in Bots </a> </div> </li>
                                <li> <div class="menu-on">  <a href=""> Shared Bots </a> </div> </li>
                            </ul>

                        </nav>
                    </div>


                    <div id="user" class="right">
                        <ul class="list_inline">
                            <li> <a class="username" href=""> Ashraf, Alharbi </a> </li>
                            <li> <a class="logout" href=""> Logout </a> </li>
                        </ul>
                    </div>

                    <div class="clear"> </div>
                </div>
            </div>

            <div id="content">
                <div id="sidebar_left" class="sidebar left">
                    <div id="my_bots" class="sidebar_box">
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

                    <div id="built-in_bots" class="sidebar_box">
                        <div class="sidebar_head">
                            Built-in Bots
                        </div>

                        <div class="sidebar_content">
                            <ul class="list_block">
                                <li class="bot"> <a href=""> Cautious Built-in Bot </a> </li>
                                <li class="bot"> <a href=""> Greedy Built-in Bot </a> </li>
                                <li class="bot"> <a href=""> Smarter Bot </a> </li>
                                <li class="bot"> <a href=""> Black Mamba </a> </li>
                                <li class="bot more"> <a href=""> more... </a> </li>
                            </ul>
                        </div>
                    </div>

                    <div id="shared_bots" class="sidebar_box">
                        <div class="sidebar_head">
                            Shared Bots
                        </div>

                        <div class="sidebar_content">
                            <div id="search">
                                <form method="">
                                    <input class="search_box" type="text" value="Search..." />
                                    <input class="search_btn" type="submit" value="" />
                                </form>
                            </div>

                            <br />


                            <!-- Search Results in list here
                            <ul class="list_block">
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
                                    <li class="bot more"> <a href=""> more... </a> </li>
                            </ul>
                            -->
                            <br />
                        </div>
                    </div>
                </div>

                <div id="main_content">
                    <div id="player_one" class="left">
                        <ul>
                            <li> <b> Player 1 </b> </li>
                        </ul>

                        <ul class="grid_box">
                            <%for (int i = 0; i < SIZE; i++) {%>
                            <%for (int j = 0; j < SIZE; j++) {%>
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
                            <%for (int i = 0; i < SIZE; i++) {%>
                            <%for (int j = 0; j < SIZE; j++) {%>
                            <li id="b<%=i * 10 + j%>"></li>
                                <%}%>
                            </br>
                            <%}%>

                        </ul>

                        <script>
                            var gameArrayA = new Array(10);
                            var gameArrayB = new Array(10);
                            for (var z = 0; z < 10; z++) {
                                gameArrayA[z] = new Array(10);
                                gameArrayB[z] = new Array(10);
                            }
                            <%
                                for (int i = 0; i < SIZE; i++) {
                                    for (int j = 0; j < SIZE; j++) {
                            %>
                            gameArrayA[<%= i%>][<%= j%>] = <%= bot1Grid.getGrid()[i][j].getSectionStatus()%>;
                            gameArrayB[<%= i%>][<%= j%>] = <%= bot2Grid.getGrid()[i][j].getSectionStatus()%>;
                            <%
                                    }
                                }
                            %>
                            for (var i = 0; i < 10; i++) {
                                for (var j = 0; j < 10; j++) {
                                    document.getElementById("a" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(0);
                                    document.getElementById("b" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(0);
                                }
                            }
                            var bot1Moves = new Array(100);
                            var bot2Moves = new Array(100);
                            for (var k = 0; k < 100; k++) {
                                bot1Moves[k] = new Array(4);
                                bot2Moves[k] = new Array(4);
                            }
                            <%
                                bot1Moves = gameRun(bot1Grid, bot1);
                                bot2Moves = gameRun(bot2Grid, bot1);
                                for (int yCoord = 0; yCoord < bot1Moves.length; yCoord++) {
                            %>
                            bot1Moves[<%=yCoord%>][0] = <%= bot1Moves[yCoord][0]%>
                            bot1Moves[<%=yCoord%>][1] = <%= bot1Moves[yCoord][1]%>
                            bot1Moves[<%=yCoord%>][2] = <%= bot1Moves[yCoord][2]%>
                            bot1Moves[<%=yCoord%>][3] = <%= bot1Moves[yCoord][3]%>
                            bot2Moves[<%=yCoord%>][0] = <%= bot2Moves[yCoord][0]%>
                            bot2Moves[<%=yCoord%>][1] = <%= bot2Moves[yCoord][1]%>
                            bot2Moves[<%=yCoord%>][2] = <%= bot2Moves[yCoord][2]%>
                            bot2Moves[<%=yCoord%>][3] = <%= bot2Moves[yCoord][3]%>
                            <%}%>
                        </script>
                    </div>


                </div>

                <div id="sidebar_right" class="sidebar right">
                    <div id="game_controls" class="sidebar_box">
                        <div class="sidebar_head">
                            Game Controls
                        </div>

                        <div class="sidebar_content">
                            <div id="controls">
                                <div id="set_one">
                                    <ul class="list_inline">
                                        <li> <a class="fast_prev" onClick="startOfGame()"> </a> </li>
                                        <li> <a class="prev" onClick="prevMove()"> </a> </li>
                                        <li> <a class="pause" onClick="playPause()"> </a> </li>
                                        <li> <a class="forward" onClick="nextMove()"> </a>  </li>
                                        <li> <a class="fast_forward" onClick="endGame()"> </a> </li>
                                    </ul>
                                </div>

                                <div id="set_two" class="slow_fast">
                                    <div class="left"> Slow </div>
                                    <section> <div id="slider"> </div>  </section>
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
                                <div id="results_head">
                                    <ul class="list_inline">
                                        <li style="width: 100px;"> Boats Left </li>
                                        <li style="width: 40px;"> P1 <div class="color_red right"> </div> </li>
                                        <li style="width: 40px;"> P2 <div class="color_purple right"> </div> </li>
                                        <div class="clear"> </div>
                                    </ul>

                                    <div class="clear"> </div>
                                </div>

                                <div id="results_list">

                                    <ul class="list_inline">
                                        <li style = "width: 100px;"> Aircraft Carrier  </li>
                                        <li id = "p1AirCarry" style="width: 40px;"> 1 </li>
                                        <li id = "p2AirCarry" style="width: 40px;"> 1 </li>
                                    </ul>

                                    <ul class="list_inline">
                                        <li style = "width: 100px;"> Battleship </li>
                                        <li id = "p1BattleShip"  style="width: 40px;"> 2 </li>
                                        <li id = "p2BattleShip" style="width: 40px;"> 2 </li>
                                    </ul>

                                    <ul class="list_inline">
                                        <li style = "width: 100px;"> Destroyer </li>
                                        <li id = "p1Destroyer" style="width: 40px;"> 1 </li>
                                        <li id = "p2Destroyer" style="width: 40px;"> 1 </li>
                                    </ul>

                                    <ul class = "list_inline">
                                        <li style = "width: 100px;"> Patrol Boat </li>
                                        <li id = "p1Patrol" style="width: 40px;"> 2 </li>
                                        <li id = "p2Patrol" style="width: 40px;"> 2 </li>
                                    </ul>


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
