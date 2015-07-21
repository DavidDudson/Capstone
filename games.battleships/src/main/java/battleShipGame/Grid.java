package battleShipGame;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author adhoulih
 */
public class Grid {

    // Grid matrix size
    private int SIZE;

    // Default ship values
    private int EMPTY = 0;
    private int PATROLBOAT = 2;
    private int DESTROYER = 3;
    private int BATTLESHIP = 4;
    private int AIRCRAFTCARRIER = 5;
    private int HIT = 6;
    private int MISS = 7;
    private Section grid[][];

    //Ship objects implemented in the grid
    private ArrayList<Ship> Ships;

    public Grid(int size) {
        this.SIZE = size;
        this.grid = new Section[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Section(0);
            }
        }

    }

    public void generateShips() {
        // Generate a fixed amount of ships and placed in a list

        Ships.add(new Ship(DESTROYER, 0));
        Ships.add(new Ship(BATTLESHIP, 1));
        Ships.add(new Ship(BATTLESHIP, 2));
        Ships.add(new Ship(AIRCRAFTCARRIER, 3));
        Ships.add(new Ship(PATROLBOAT, 4));
        Ships.add(new Ship(PATROLBOAT, 5));

    }

    public void loadGrid() {
        // Load ships into grid computing randomly their positions
        Random rn = new Random();
        for (int indx = 0; indx < Ships.size(); indx++) {
            int randomNum = rn.nextInt((100 - 0));
            Boolean horizontal = rn.nextBoolean();

            int countInc = 0;
            int countDec = 0;
            int rootCount = 0;

            while (countInc < Ships.get(indx).getShipType() || countDec < Ships.get(indx).getShipType() || rootCount < Ships.get(indx).getShipType()) {
                if (horizontal) {
                    if (grid[randomNum][randomNum + countInc].getSectionNum() == EMPTY) {
                        countInc++;
                    }
                    if (grid[randomNum][randomNum - countDec].getSectionNum() == EMPTY) {
                        countDec++;
                    }

                } else {
                    if (grid[randomNum + countInc][randomNum].getSectionNum() == EMPTY) {
                        countInc++;
                    }
                    if (grid[randomNum - countDec][randomNum].getSectionNum() == EMPTY) {
                        countDec++;
                    }
                }
                rootCount++;

            }
            if (countInc == Ships.get(indx).getShipType() && countDec == Ships.get(indx).getShipType()) {
                if (rn.nextBoolean()) {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum][randomNum + j] = Ships.get(indx).getShipSections()[j];
                    }
                } else {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum][randomNum - j] = Ships.get(indx).getShipSections()[j];
                    }
                }
            } else if (countInc == Ships.get(indx).getShipType()) {
                for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                    grid[randomNum][randomNum + j] = Ships.get(indx).getShipSections()[j];
                }
            } else if (countDec == Ships.get(indx).getShipType()) {
                for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                    grid[randomNum][randomNum - j] = Ships.get(indx).getShipSections()[j];
                }
            } else {
                indx--;
            }

        }
    }

    public Section[][] getGrid() {
        // Get current grid state
        return grid;

    }

    public void playMove(int positionX, int positionY) {
        //play move based on postion choosen by the bot
        switch(grid[positionY][positionX].getSectionNum()){
            case 0: grid[positionY][positionX].setSeciontNum(MISS);
            case 2: grid[positionY][positionX].setDeadSection();
            case 3: grid[positionY][positionX].setDeadSection();
            case 4: grid[positionY][positionX].setDeadSection();
            case 5: grid[positionY][positionX].setDeadSection();
        }

    }
}
