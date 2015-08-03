<%--
    /* global j */

/* global j */

Document   : gameState
    Created on : 16/07/2015, 10:26:48 AM
    Author     : adhoulih
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@page import="battleShipGame.test"%>
<%@page import="java.util.Arrays"%>
<%@page import="battleShipGame.ShipLinkedList"%>
<%@page import="battleShipGame.testBot"%>
<%@page import="battleShipGame.Grid"%>

<%!
    int SIZE = 10;
    testBot bot1 = new testBot();
    Grid bot1Grid = new Grid(10);
    int[][] bot1Moves = new int[100][2];

    public void gameRun() {
        int movesIndex = 0;
        int[] retVals = new int[2];

        while (true) {
            int deadShips = 0;

            retVals = bot1.runMove(bot1Grid.getGrid());
            bot1Moves[movesIndex] = retVals;
            bot1Grid.playMove(retVals[0], retVals[1]);
            for (ShipLinkedList ship : bot1Grid.getShips()) {
                if (!ship.getShipStatus()) {
                    deadShips++;
                }
            }
            if (deadShips == bot1Grid.getShips().size()) {
                break;
            }


           movesIndex++;
        }
    }


%>

<%
    bot1Grid.generateShips();
    bot1Grid.loadGrid();
%>
<style>
    .node {
        width:50px;
        height:50px;
        background-color: red;


    }
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p id="demo"></p>
        <div class="gameState">
            <table class="node" border="1">
                <%
                    int count = 0;
                    for (int i = 0; i < SIZE; i++) {

                %>
                <tr>
                    <%
                        for (int j = 0; j < SIZE; j++) {
                    %>
                    <td id="<%=count%>">
                        <%= count%>
                        <%count++; %>
                    </td>
                    <%}%>
                </tr>
                <%}%>
            </table>

            <Button onclick="prevMove()">prevMove</button>
            <Button onclick="nextMove()">nextMove</button>
        </div>
        <script>
            var gameArray = new Array(10);
            for (var z = 0; z < 10; z++) {
                gameArray[z] = new Array(10);
            }
            <%
                for (int i = 0; i < bot1Grid.getGrid().length; i++) {
                    for (int j = 0; j < bot1Grid.getGrid().length; j++) {
            %>

            gameArray[<%= i%>][<%= j%>] = <%= bot1Grid.getGrid()[i][j].getSectionStatus()%>;

            <%
                    }
                }
            %>


            for (var i = 0; i < 10; i++) {
                for (var j = 0; j < 10; j++) {

                        var sectionColor;
                        switch (gameArray[i][j]) {
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
                        default:
                            sectionColor = "#FFFFFF";
                }

            document.getElementById(i * 10 + j).style.backgroundColor = sectionColor;
            }
            }
            var bot1Moves = new Array(100);


            for (var k = 0; k < 100; k++) {
                bot1Moves[k] = new Array(2);
            }
            <%
                    gameRun();

                    for (int yCoord = 0; yCoord < bot1Moves.length; yCoord++) {

            %>
                    bot1Moves[<%=yCoord%>][0] = <%= bot1Moves[yCoord][0]%>
                    bot1Moves[<%=yCoord%>][1] = <%= bot1Moves[yCoord][1]%>

            <%}%>
            var currentMoveCount = 0;
            function nextMove() {
                    var upSectCol;
                    var yCoord = bot1Moves[currentMoveCount][0];
                    var xCoord = bot1Moves[currentMoveCount][1];
                    var pos = yCoord * 10 + xCoord;

                    switch (gameArray[yCoord][xCoord].toString()) {
                    case '0':
                        upSectCol = "#FFFFFF";
                        break;
                    case '1':
                        upSectCol = "#FF1919";
                        break;
                    case '2':
                        upSectCol = "#FF1919";
                        break;
                    case '3':
                        upSectCol = "#FF1919";
                        break;
                    case '4':
                        upSectCol = "#FF1919";
                        break;
                    case '5':
                        upSectCol = "#FF1919";
                        break;

            }
                    document.getElementById(pos).style.backgroundColor = upSectCol;
                    currentMoveCount++;


        }



        </script>
    </body>
</html>
