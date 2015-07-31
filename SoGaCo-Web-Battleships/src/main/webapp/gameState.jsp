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
    int[][] bot1Moves = new int[100][2];

    testBot bot2 = new testBot();
    Grid bot2Grid;
    int[][] bot2Moves = new int[100][2];

    public int[][] gameRun(Grid grid, testBot bot) {
        int[][] botMoves = new int[100][2];
        int movesIndex = 0;
        int[] retVals = new int[2];
        while (true) {
            int deadShips = 0;
            retVals = bot.runMove(grid.getGrid());
            botMoves[movesIndex] = retVals;
            grid.playMove(retVals[0], retVals[1]);
            for (ShipLinkedList ship : grid.getShips()) {
                if (!ship.getShipStatus()) {
                    deadShips++;
                }
            }
            if (deadShips == grid.getShips().size()) {
                break;
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

    .node {
        width:10px;
        height:10px;
        background-color: red;
    }
</style>
<script type="text/javascript">
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

    function nextMove() {
        if (bot1Moves[bot1MoveCount][0] === -1) {
            document.getElementById("nextButton").disabled = true;
            alert("bot1 wins");
        }
        if (bot2Moves[bot2MoveCount][0] === -1) {
            document.getElementById("nextButton").disabled = true;
            alert("bot2 wins");
        }

        if (player1Move) {
            var yCoordA = bot1Moves[bot1MoveCount][0];
            var xCoordA = bot1Moves[bot1MoveCount][1];
            var posA = "a" + (yCoordA * 10 + xCoordA);
            document.getElementById(posA).style.backgroundColor = retHitOrMiss(gameArrayA[yCoordA][xCoordA]);
            bot1MoveCount++;

        } else {
            var yCoordB = bot2Moves[bot2MoveCount][0];
            var xCoordB = bot2Moves[bot2MoveCount][1];
            var posB = "b" + (yCoordB * 10 + xCoordB);
            document.getElementById(posB).style.backgroundColor = retHitOrMiss(gameArrayB[yCoordB][xCoordB]);
            bot2MoveCount++;
        }
        player1Move = !player1Move;



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
                <Button id="nextButton" onclick="nextMove()">nextMove</button>    

            </div>
        </div>
        <div class="currentShips">
            <table>
                <tr>
                    <td>boats type
                        <div class="node"></div>
                        <div class="node"></div>
                        <div class="node"></div>
                        <div class="node"></div>
                        <div class="node"></div>
                    </td>
                    <td>player 1</td>
                    <td>player 2</td> 
                </tr>
                <tr>
                    <td>Aircraft carrier</td>
                    <td>1</td> 
                    <td>1</td>
                </tr>
                                <tr>
                    <td>Battleship</td>
                    <td>2</td> 
                    <td>2</td>
                </tr>
                                <tr>
                    <td>Destroyer</td>
                    <td>2</td> 
                    <td>2</td>
                </tr>
                                <tr>
                    <td>Patrol boat</td>
                    <td>2</td> 
                    <td>2</td>
                </tr>
                
            </table>




        </div>

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
                    document.getElementById("a" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(gameArrayA[i][j]);
                    document.getElementById("b" + (i * 10 + j).toString()).style.backgroundColor = getSectColor(gameArrayB[i][j]);
                }
            }


            var bot1Moves = new Array(100);
            var bot2Moves = new Array(100);

            for (var k = 0; k < 100; k++) {
                bot1Moves[k] = new Array(2);
                bot2Moves[k] = new Array(2);
            }
            <%
                bot1Moves = gameRun(bot1Grid, bot1);
                System.out.println(printGrid(bot2Grid));
                bot2Moves = gameRun(bot2Grid, bot1);
                for (int yCoord = 0; yCoord < bot1Moves.length; yCoord++) {
            %>
            bot1Moves[<%=yCoord%>][0] = <%= bot1Moves[yCoord][0]%>
            bot1Moves[<%=yCoord%>][1] = <%= bot1Moves[yCoord][1]%>

            bot2Moves[<%=yCoord%>][0] = <%= bot2Moves[yCoord][0]%>
            bot2Moves[<%=yCoord%>][1] = <%= bot2Moves[yCoord][1]%>
            <%}%>

            var player1Move = true;

            var bot1MoveCount = 0;
            var bot2MoveCount = 0;



        </script>
    </body>
</html>