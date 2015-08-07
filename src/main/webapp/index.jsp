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
    int[][] bot1Moves = new int[100][2];

    testBot bot2 = new testBot();
    Grid bot2Grid;
    int[][] bot2Moves = new int[100][2];

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
            botMoves[movesIndex + 1][0] = -1;
            botMoves[movesIndex + 1][1] = -1;
        }
        return botMoves;
    }

    public static StringBuilder printGrid(Grid grid) {
        StringBuilder outGrid = new StringBuilder();
        for (Section[] rowSection : grid.getGrid()) {
            outGrid.append("---------------------\n");
            StringBuilder row = new StringBuilder();
            for (Section colSection : rowSection) {
                row.append("|").append(colSection.getSectionStatus());
            }
            row.append("|\n");

            outGrid.append(row);

        }
        return outGrid;
    }
%>

<%
    bot1Grid = new Grid(SIZE);
    bot1Grid.generateShips();
    bot1Grid.loadGrid();

    bot2Grid = bot1Grid.deepClone();


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
                                <li> <a href=""> Editor </a> </li>
                                <li> <a href=""> Test </a> </li>
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

                        <script>
                            
                            $(function () {
                                $('#player_one ul.grid_box').on('click', 'li', function () {

                                    $(this).css({
                                        'background-color': '#990000', 'border': '1px solid #dddddd'
                                    });
                                });
                            });
                        </script>

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

                            $(function () {
                                $('#player_two ul.grid_box').on('click', 'li', function () {

                                    $(this).css({
                                        'background-color': '#9900fe', 'border': '1px solid #dddddd'
                                    });
                                });
                            });
                        </script>
                    </div>

                    <div id="key" class="left">
                        <ul> 
                            <li> KEY </li>
                        </ul>

                        <ul class="grid_box">
                            <li style="width: 100px;"> Player 1 Ship </li> <li class="color_red"> </li> <br />
                            <li style="width: 100px;"> Player 2 Ship </li> <li class="color_purple"> </li> <br />
                            <li style="width: 100px;"> Hit </li> <li class="color_brown"> </li> <br />
                            <li style="width: 100px;"> Miss </li> <li class="color_black"> </li> <br />
                        </ul>

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
                                        <li> <a class="fast_prev" href=""> </a> </li> 
                                        <li> <a class="prev" href=""> </a> </li> 
                                        <li> <a class="pause" href=""> </a> </li> 
                                        <li> <a class="forward" onclick="nextMove()"> </a>  </li> 
                                        <li> <a class="fast_forward" href=""> </a> </li> 
                                    </ul>
                                </div>

                                <div id="set_two" class="slow_fast">
                                    <div class="left"> Slow </div>
                                    <section> <div id="slider"> </div>  </section>
                                    <div class="right"> Fast </div>
                                    <div class="clear"> </div>


                                    <script>
                                        $(function () {


                                            var slider = $('#slider'),
                                                    tooltip = $('.tooltip');


                                            tooltip.hide();


                                            slider.slider({
                                                range: "min",
                                                min: 1,
                                                value: 35,
                                                start: function (event, ui) {
                                                    tooltip.fadeIn('fast');
                                                },
                                                slide: function (event, ui) {

                                                    var value = slider.slider('value'),
                                                            volume = $('.volume');

                                                    tooltip.css('left', value).text(ui.value);



                                                },
                                                stop: function (event, ui) {
                                                    tooltip.fadeOut('fast');
                                                }
                                            });

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
                                        <li style="width: 100px;"> Aircraft Carrier  </li>
                                        <li style="width: 40px;"> 1 </li>
                                        <li style="width: 40px;"> 0 </li>
                                    </ul>

                                    <ul class="list_inline">
                                        <li style="width: 100px;"> Battleship </li>
                                        <li style="width: 40px;"> 2 </li>
                                        <li style="width: 40px;"> 2 </li>
                                    </ul>

                                    <ul class="list_inline">
                                        <li style="width: 100px;"> Destroyer </li>
                                        <li style="width: 40px;"> 2 </li>
                                        <li style="width: 40px;"> 2 </li>
                                    </ul>

                                    <ul class="list_inline">
                                        <li style="width: 100px;"> Patrol Boat </li>
                                        <li style="width: 40px;"> 1 </li>
                                        <li style="width: 40px;"> 2 </li>
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
        <script>
            jQuery(document).ready(function () {
                jQuery('.toggle-nav').click(function (e) {
                    jQuery(this).toggleClass('active');
                    jQuery('.menu ul').toggleClass('active');

                    e.preventDefault();
                });
            });
        </script>

    </body>
</html>