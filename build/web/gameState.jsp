<%-- 
    Document   : gameState
    Created on : 16/07/2015, 10:26:48 AM
    Author     : adhoulih
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="battleShipGame.Grid"%>
<%! 
    int SIZE = 10;
    Grid botGrid1 = new Grid(10);   
    Grid botGrid2 = new Grid(10); 
    
    public void updateGrid(Grid grid, int position){
        //update the grid from a position
    }
    
    public void updateGUI(Grid grid){
        //update the GUI from the grid
    }
    public void gameRun(){
        //core game logic
    }





%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <div class="gameState">
           <table>

               <%
                   for(int i =0;i<SIZE;i++){

                %>
                    <tr>
                <%
                
                       for(int j=0; i<SIZE; i++){ 
                %>
                        <td>
                        </td>
                <%
                       }
                %>
                   
                   
                   
               %>
               <%
                }
               %>
           </table>
       
       </div>
    </body>
</html>
