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
    private final int WATER = 0;
    private final int PATROLBOAT = 2;
    private final int DESTROYER = 3;
    private final int BATTLESHIP = 4;
    private final int AIRCRAFTCARRIER = 5;
    private final int HIT = 6;
    private final int MISS = 7;
    private final Section grid[][];

    //Ship objects implemented in the grid
    private ArrayList<ShipLinkedList> Ships;

    public Grid(int size) {
        this.SIZE = size;
        this.grid = new Section[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Section(WATER, 0);
            }
        }

    }

    /**
     * Generate ships. ship numbers are hard-coded.
     */
    public void generateShips() {
        // Generate a fixed amount of ships and placed in a list
        Ships = new ArrayList();
        Ships.add(new ShipLinkedList(DESTROYER, 1));
        Ships.add(new ShipLinkedList(BATTLESHIP, 2));
        Ships.add(new ShipLinkedList(BATTLESHIP, 3));
        Ships.add(new ShipLinkedList(AIRCRAFTCARRIER, 4));
        Ships.add(new ShipLinkedList(PATROLBOAT, 5));
        Ships.add(new ShipLinkedList(PATROLBOAT, 6));

    }

    /**
     * loads grid from generated ships and computes the positions
     */
    public void loadGrid() {
        // Load ships into grid computing randomly their positions
        Random rn = new Random();
        for (int indx = 0; indx < Ships.size(); indx++) {
            int randomNum = rn.nextInt(9);
            Boolean horizontal = rn.nextBoolean();

            int countInc = 0;
            int countDec = 0;
            int rootCount = 0;

            //preform check on weather positions to the left/right up/down were 
            //free to place a ship
            while (countInc < Ships.get(indx).getShipType()
                    && countDec < Ships.get(indx).getShipType()
                    && rootCount < Ships.get(indx).getShipType()) {

                int upperBound = randomNum + countInc;
                int lowerBound = randomNum - countDec;
                if (horizontal) {
                    if ((upperBound >= 0) && (upperBound < SIZE) && grid[randomNum][upperBound]
                            .getSectionStatus()== WATER) {
                        countInc++;
                    }
                    if ((lowerBound >= 0) && (lowerBound < SIZE) && grid[randomNum][lowerBound]
                            .getSectionStatus() == WATER) {
                        countDec++;
                    }

                } else {

                    if ((upperBound >= 0) && (upperBound < SIZE) && grid[upperBound][randomNum]
                            .getSectionStatus() == WATER) {
                        countInc++;
                    }
                    if ((lowerBound >= 0) && (lowerBound < SIZE) && grid[lowerBound][randomNum]
                            .getSectionStatus() == WATER) {
                        countDec++;
                    }
                }
                rootCount++;

            }
            //if free places ship sections on grid else decrements 
            //the loop and tries again in a different position
            if (horizontal) {
                if (countInc == Ships.get(indx).getShipType()
                        && countDec == Ships.get(indx).getShipType()) {
                    if (rn.nextBoolean()) {
                        for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                            grid[randomNum][randomNum + j] = Ships.get(indx)
                                    .getShipSection(j);
                        }
                    } else {
                        for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                            grid[randomNum][randomNum - j] = Ships.get(indx)
                                    .getShipSection(j);
                        }
                    }
                } else if (countInc == Ships.get(indx).getShipType()) {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum][randomNum + j] = Ships.get(indx)
                                .getShipSection(j);
                    }
                } else if (countDec == Ships.get(indx).getShipType()) {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum][randomNum - j] = Ships.get(indx)
                                .getShipSection(j);
                    }
                } else {
                    indx--;
                }
            } else {
                if (countInc == Ships.get(indx).getShipType()
                        && countDec == Ships.get(indx).getShipType()) {
                    if (rn.nextBoolean()) {
                        for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                            grid[randomNum + j][randomNum] = Ships.get(indx)
                                    .getShipSection(j);
                        }
                    } else {
                        for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                            grid[randomNum - j][randomNum] = Ships.get(indx)
                                    .getShipSection(j);
                        }
                    }
                } else if (countInc == Ships.get(indx).getShipType()) {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum + j][randomNum] = Ships.get(indx)
                                .getShipSection(j);
                    }
                } else if (countDec == Ships.get(indx).getShipType()) {
                    for (int j = 0; j < Ships.get(indx).getShipType(); j++) {
                        grid[randomNum - j][randomNum] = Ships.get(indx)
                                .getShipSection(j);
                    }
                } else {
                    indx--;
                }
            }

        }
    }

    /**
     * get current grid state
     * @return current grid
     */
    public Section[][] getGrid() {
        // Get current grid state
        return this.grid;

    }

    /**
     * get array of ships
     * @return current array of ships
     */
    public ArrayList<ShipLinkedList> getShips() {
        // return array of ships
        return this.Ships;
    }

    /**
     * play a move computed by the bot
     * @param positionY y-coord grid index value
     * @param positionX x-coord grid index value
     */
    public void playMove(int positionY, int positionX) {
        //play move based on postion choosen by the bot\
        
        switch (grid[positionY][positionX].getSectionStatus()) {
            case 0:
                grid[positionY][positionX].setSectionStatus(MISS);
                break;
            case 2:
                grid[positionY][positionX].setSectionStatus(HIT);
                break;
            case 3:
                grid[positionY][positionX].setSectionStatus(HIT);
                break;
            case 4:
                grid[positionY][positionX].setSectionStatus(HIT);
                break;
            case 5:
                grid[positionY][positionX].setSectionStatus(HIT);
                break;
        }
        //check to see if the ship is still alive updates bool if not
        if (grid[positionY][positionX].getShipID()!= WATER) {
            ShipLinkedList hitShip = Ships.get(grid[positionY][positionX].getShipID() - 1);
            for (int i = 0; i<hitShip.getShipType(); i++) {
                if (hitShip.getShipSection(i).getSectionStatus() != HIT) {
                    return;
                }

            }
            hitShip.setShipStatus(false);

        }
    }
}
