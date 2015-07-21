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
    int SIZE = 10;

    static testBot bot1 = new testBot();
    static Grid bot1Grid = new Grid(10);
    static int[][] bot1Moves = new int[100][2];    
    public static void main(String [] args){
        bot1Grid.generateShips();
        gameRun();
    
    }
    

    public void updateGrid(Grid grid, int position) {
        //update the grid from a position
    }

    public void updateGUI(Grid grid) {
        //update the GUI from the grid
    }

    public static void gameRun() {
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
    
}
