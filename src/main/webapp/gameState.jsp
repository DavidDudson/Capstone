<%-- 
    /* global j */
/* global j */
Document   : gameState
    Created on : 16/07/2015, 10:26:48 AM
    Author     : adhoulih
--%>
<%@page import="battleShipGame.Section"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Arrays"%>
<%@page import="battleShipGame.ShipLinkedList"%>
<%@page import="battleShipGame.testBot"%>
<%@page import="battleShipGame.Grid"%>

<%!
    int SIZE = 10;
    testBot bot1 = new testBot();
    Grid bot1Grid;
    int[][] bot1Moves = new int[100][3];

    testBot bot2 = new testBot();
    Grid bot2Grid;
    int[][] bot2Moves = new int[100][3];

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
<style>

    .grids {
        float: left;
        width: 500px;
        height: 245px;

    }
    .currentShips {
        float: right
    }
    .gameState1 {
        float: left;
        margin-right: 20px;
    }
    .gameState2 {
        float: right;
        margin-left: 20px;
    }

    .nodeSection {
        width:20px;
        height:20px;
        border-color: blue;
        border-style: solid;
        border-left-width:   5px;
        float: left;;
    }
</style>
<script type="text/javascript">
    var player1Move = true;
    var bot1MoveCount = 0;
    var bot2MoveCount = 0;



    function getSectColor(gameArrIndex) {
        var sectionColor;
        switch (gameArrIndex) {
            case 0:
                sectionColor = "#A6B8ED";
                break;
            case 1:
                sectionColor = "#FFA3A3";
                break;
            case 2:
                sectionColor = "#FFFF4D";
                break;
            case 3:
                sectionColor = "#FF9900";
                break;
            case 4:
                sectionColor = "#944DFF";
                break;
            case 5:
                sectionColor = "#94FF70";
                break;
            case 6:
                sectionColor = "#000000";
                break;
            case 7:
                sectionColor = "#FFFFFF";
                break;
        }
        return sectionColor;

    }

    function retHitOrMiss(gameArrIndex) {
        var newSectCol;
        switch (gameArrIndex) {
            case 0:
                newSectCol = "#FFFFFF";
                break;
            case 1:
                newSectCol = "#FF0000";
                break;
            case 2:
                newSectCol = "#FF0000";
                break;
            case 3:
                newSectCol = "#FF0000";
                break;
            case 4:
                newSectCol = "#FF0000";
                break;
            case 5:
                newSectCol = "#FF0000";
                break;
        }
        return newSectCol;

    }

    function alterBoatLiveGUI(sunkBoat, player) {
        var playerBoat;
        switch (sunkBoat) {
            case 2:
                playerBoat = document.getElementById(player + "Patrol");
                break;
            case 3:
                playerBoat = document.getElementById(player + "Destroyer");
                break;
            case 4:
                playerBoat = document.getElementById(player + "Battle");
                break;
            case 5:
                playerBoat = document.getElementById(player + "AirCarry");
                break;

        }
        var num = playerBoat.innerHTML;
        num--;
        playerBoat.innerHTML = num;

    }

    function nextMove() {
        if (player1Move) {
            var yCoordA = bot1Moves[bot1MoveCount][0];
            var xCoordA = bot1Moves[bot1MoveCount][1];
            var posA = "a" + (yCoordA * 10 + xCoordA);
            document.getElementById(posA).style.backgroundColor = retHitOrMiss(gameArrayA[yCoordA][xCoordA]);

            if (bot1Moves[bot1MoveCount][3] !== 0) {
                alterBoatLiveGUI(bot1Moves[bot1MoveCount][3], "p1");
            }
            bot1MoveCount++;

        } else {
            var yCoordB = bot2Moves[bot2MoveCount][0];
            var xCoordB = bot2Moves[bot2MoveCount][1];
            var posB = "b" + (yCoordB * 10 + xCoordB);
            document.getElementById(posB).style.backgroundColor = retHitOrMiss(gameArrayB[yCoordB][xCoordB]);

            if (bot2Moves[bot2MoveCount][3] !== 0) {
                alterBoatLiveGUI(bot2Moves[bot2MoveCount][3], "p2");
            }
            bot2MoveCount++;
        }
        if (bot1Moves[bot1MoveCount][0] === -1) {
            document.getElementById("nextButton").disabled = true;
            alert("bot1 wins");
        }
        if (bot2Moves[bot2MoveCount][0] === -1) {
            document.getElementById("nextButton").disabled = true;
            alert("bot2 wins");
        }
        if (bot1MoveCount !== 0) {
            document.getElementById("prevMove").disabled = false;
        }

        player1Move = !player1Move;



    }
    function prevMove() {
        player1Move = !player1Move;

        if (bot1Moves[bot1MoveCount][0] !== -1) {
            document.getElementById("nextButton").disabled = false;
        }
        if (bot1MoveCount === 0){
            document.getElementById("prevButton").disabled = true;
        }
        else if (player1Move) {
            bot1MoveCount--;
            var yCoordA = bot1Moves[bot1MoveCount][0];
            var xCoordA = bot1Moves[bot1MoveCount][1];
            var posA = "a" + (yCoordA * 10 + xCoordA);

            document.getElementById(posA).style.backgroundColor = getSectColor(bot1Moves[bot1MoveCount][2]);

            if (bot1Moves[bot1MoveCount][2] !== 0) {
                alterBoatLiveGUI(bot1Moves[bot1MoveCount][2], "p1");
            }


        } else if (bot2MoveCount !== 0) {
            bot2MoveCount--;

            var yCoordB = bot2Moves[bot2MoveCount][0];
            var xCoordB = bot2Moves[bot2MoveCount][1];
            var posB = "b" + (yCoordB * 10 + xCoordB);
            document.getElementById(posB).style.backgroundColor = getSectColor(bot2Moves[bot2MoveCount][2]);

            if (bot2Moves[bot2MoveCount][2] !== 0) {
                alterBoatLiveGUI(bot2Moves[bot2MoveCount][2], "p2");
            }

        }



    }
    function endGame() {

        while (!document.getElementById("nextButton").disabled) {
            nextMove();
        }

    }
    function startOfGame() {
        while (!document.getElementById("prevMove").disabled) {
            prevMove();
        }
    }



</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-store" />
        <title>JSP Page</title>
    </head>
    <body>
        <div class="grids">
            <div class="gameState1">
                <table class="node" border="1">
                    <%                    int countA = 0;
                        for (int i = 0; i < SIZE; i++) {
                    %>
                    <tr>
                        <%
                            for (int j = 0; j < SIZE; j++) {
                        %>
                        <td id="a<%=countA%>">
                            <%= countA%>
                            <%countA++; %>
                        </td>
                        <%}%>
                    </tr>
                    <%}%>
                </table>

            </div> 
            <div class="gameState2">
                <table class="node" border="1">
                    <%                    int countB = 0;
                        for (int i = 0; i < SIZE; i++) {
                    %>
                    <tr>
                        <%
                            for (int j = 0; j < SIZE; j++) {
                        %>
                        <td id="b<%=countB%>">
                            <%= countB%>
                            <%countB++; %>
                        </td>
                        <%}%>
                    </tr>
                    <%}%>
                </table>


            </div>
        </div>
        <div class="currentShips">
            <table>
                </tr>
                <Button id="startOfGame" onclick="startOfGame()">start Of Game</button> 
                <Button id="prevMove" onclick="prevMove()">previous move</button> 
                <Button id="pausePlay" onclick="pausePlay()">pause/play</button> 
                <Button id="nextButton" onclick="nextMove()">next move</button>    
                <Button id="endGame" onclick="endGame()">end game</button> 
                </tr>
                <tr>
                    <td>boats type
                    </td>
                    <td>player 1</td>
                    <td>player 2</td> 
                </tr>
                <tr>
                    <td>
                        <p>Aircraft carrier</p>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                    </td>
                    <td><p id="p1AirCarry">1</p></td> 
                    <td><p id="p2AirCarry">1</p></td>
                </tr>
                <tr>
                    <td>
                        <p>Battleship</p>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                    </td>
                    <td><p id="p1Battle">2</p></td> 
                    <td><p id="p2Battle">2</p></td>
                </tr>
                <tr>
                    <td>
                        <p>Destroyer</p>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                    </td>
                    <td><p id="p1Destroyer">2</p></td> 
                    <td><p id="p2Destroyer">2</p></td>
                </tr>
                <tr>
                    <td>
                        <p>Patrol boat</p>
                        <div class="nodeSection"></div>
                        <div class="nodeSection"></div>
                    </td>
                    <td><p id="p1Patrol">2</p></td> 
                    <td><p id="p2Patrol" >2</p></td>
                </tr>

            </table>




        </div>

        <script>
            document.getElementById("prevMove").disabled = true;

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
                    document.getElementById("a" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(gameArrayA[i][j]);
                    document.getElementById("b" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(gameArrayB[i][j]);
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
    </body>
</html>