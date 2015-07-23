/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleShipGame;


/**
 *
 * @author adhoulih
 */
public class test {

    public static void main(String [] args){
        Grid grid = new Grid(10);
        testBot bot1 = new testBot();

        grid.generateShips();
        grid.loadGrid();
        Boolean allDeadShips = false;
        System.out.print(printGrid(grid));

        while(!allDeadShips){
            int deadShips = 0;
//            System.out.println(validMoves(grid));
            int[] retVals= new int[2];
            retVals = bot1.runMove(grid.getGrid());
            grid.playMove(retVals[0], retVals[1]);
            for(ShipLinkedList ship: grid.getShips()){
                if(!ship.getShipStatus()){
                    deadShips++;
                }
            }
            if(deadShips == grid.getShips().size()){
                allDeadShips = true;
            }

        }
        System.out.print(printGrid(grid));
        System.out.println("game over");


    }
    public static StringBuilder printGrid(Grid grid){
        StringBuilder outGrid = new StringBuilder();
        for(Section[] rowSection:grid.getGrid()){
            outGrid.append("---------------------\n");
            StringBuilder row = new StringBuilder();
            for(Section colSection:rowSection){
                row.append("|").append(colSection.getSectionStatus());
            }
            row.append("|\n");

            outGrid.append(row);


        }
        return outGrid;
    }
    public static int validMoves(Grid grid){
        int count = 0;
        for (Section[] rowSection: grid.getGrid()){
            for(Section colSection:rowSection){
                if(colSection.getSectionStatus() != 6){
                    count++;
                }
            }
        }
        return count;
    }

}
