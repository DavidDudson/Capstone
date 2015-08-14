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
        int[][] moves = new int[100][2];
        grid.generateShips();
        grid.loadGrid();
        int[][] testGrid = new int[10][10]; 
        testGrid = convertGrid(grid.getGrid());
        Boolean allDeadShips = false;
        int iters = 0;
        while(!allDeadShips){

            int deadShips = 0;
            int[] retVals= bot1.runMove(grid.getGrid());
            moves[iters] = retVals;
            System.out.println("coord is: " + retVals[0] + " " + retVals[1]);
            System.out.println(printGrid(grid));
            grid.playMove(retVals[0], retVals[1]);
            for(ShipLinkedList ship: grid.getShips()){
                if(!ship.getShipStatus()){
                    System.out.println("wjaoidjasdpoiaojid");
                    deadShips++;
                }
            }
            if(deadShips == grid.getShips().size()){
                allDeadShips = true;
            }
            iters++;
        }
        for(int[] move:moves){
            testGrid[move[0]][move[1]] = 6;
        }
        
        System.out.println(iters);
        System.out.println("gameOver");
        System.out.println(printGrid(grid));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(testGrid[i][j] + " ");
            }
            System.out.print("\n");
}

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
    
    public static int[][] convertGrid(Section[][] grid){
        int[][] retGrid = new int[10][10];
            for(int i =0; i< 10; i++){
                for(int j =0; j< 10; j++){
                    retGrid[i][j] = grid[i][j].getSectionStatus();
                }
            }
        return retGrid;
    }
    
}