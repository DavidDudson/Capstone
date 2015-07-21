<%-- 
    Document   : gameState
    Created on : 16/07/2015, 10:26:48 AM
    Author     : adhoulih
--%>
<%@page import="java.util.Arrays"%>
<%@page import="battleShipGame.ShipLinkedList"%>
<%@page import="battleShipGame.testBot"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="battleShipGame.Grid"%>
<%!
    int SIZE = 10;

    testBot bot1 = new testBot();
    Grid bot1Grid = new Grid(10);
    int[][] bot1Moves = new int[100][2];

    public void updateGrid(Grid grid, int position) {
        //update the grid from a position
    }

    public void updateGUI(Grid grid) {
        //update the GUI from the grid
    }

    public void gameRun() {
        Boolean allDeadShips = false;
        int movesIndex = 0;
        while (!allDeadShips) {
            int deadShips = 0;
            int[] retVals = new int[2];
            retVals = bot1.runMove(bot1Grid.getGrid());
            bot1Moves[movesIndex][0] = retVals[0];
            bot1Moves[movesIndex][1] = retVals[1];
            bot1Grid.playMove(retVals[0], retVals[1]);
            for (ShipLinkedList ship : bot1Grid.getShips()) {
                if (!ship.getShipStatus()) {
                    deadShips++;
                }
            }
            if (deadShips == bot1Grid.getShips().size()) {
                allDeadShips = true;
            }

        }
    }


%>
<%bot1Grid.generateShips();
    bot1Grid.loadGrid();
%>
<style>
    .node {
        width:50px;
        height:50px;
        background-color: red;


    }
</style>

<!DOCTYPE html>
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
                    <%
                        }
                    %>
                </tr>



                <%
                    }
                %>
            </table>
            <script>
                document.getElementById(pos).style.backgroundColor = '#003F87';
            </script>
            <Button>prevMove</button>
            <Button onclick="nextMove()">nextMove</button>
        </div>
            
            
<script>
    
    var currentMoveCount = 0;
    var gameMovePositions
    new Array(10);
    for (var i = 0; i < 10; i++) {
        gameMovePositions[i] = new Array(10);

    }
    <% for (int i = 0; i < bot1Grid.getGrid().length; i++) {
            for (int j = 0; j < bot1Grid.getGrid().length; j++) {
    %>
        var sectionStatus = <%= bot1Grid.getGrid()[i][j].getSectionStatus()%>;
        var sectionColor = null;
        switch(sectionStaus){
            case 0: sectionColor = blue;
            case 1: sectionColor = Red;
            case 2: sectionColor = Yellow;
            case 3: sectionColor = organge;
            case 4: sectionColor = purple;
            case 5: sectionColor = green;
            case 6: sectionColor = black;
            case 7: sectionColor = white;
            
        }
        document.getElementById(<%=i+j%>).style.backgroundColor = '#003F87';
        
        gameMovePositions[<%= i%>][<%= j%>] = sectionStatus;
    <%      }
        }%>

    function nextMove() {
        alert(gameMovePosition[currentMoveCount][0]);
        var pos = gameMovePosition[currentMoveCount][0] + gameMovePosition[currentMoveCount][1];
        document.getElementById(pos).style.backgroundColor = '#003F87';
        currentMoveCount++;
    }
</script>
    </body>
</html>
