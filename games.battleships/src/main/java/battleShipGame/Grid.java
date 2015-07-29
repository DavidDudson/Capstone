package battleShipGame;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author adhoulih
 */
public class Grid implements Cloneable{

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
        Ships = new ArrayList();
        Ships.add(new ShipLinkedList(DESTROYER, 1));
        Ships.add(new ShipLinkedList(BATTLESHIP, 2));
        Ships.add(new ShipLinkedList(BATTLESHIP, 3));
        Ships.add(new ShipLinkedList(AIRCRAFTCARRIER, 4));
        Ships.add(new ShipLinkedList(PATROLBOAT, 5));
        Ships.add(new ShipLinkedList(PATROLBOAT, 6));

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Grid cloned  = (Grid) super.clone();
        return super.clone();
    }

    /**
     * loads grid from generated ships and computes the positions
     * @param curShip current ship
     * @param yCoord current grid y coordinate
     * @param xCoord current grid x coordinate
     */
    public int viableDirection(ShipLinkedList curShip, int yCoord, int xCoord) {
        int[] viableDirections = {0, 1, 2, 3};

        for (int j = 0; j < curShip.getShipType(); j++) {
            if (xCoord + j > curShip.getShipType()
                    || grid[yCoord][xCoord + j].getSectionStatus() != WATER) {
                viableDirections[0] = -1;
            }
            if (xCoord - j < curShip.getShipType()
                    || grid[yCoord][xCoord - j].getSectionStatus() != WATER) {
                viableDirections[1] = -1;
            }
            if (yCoord + j > curShip.getShipType()
                    || grid[yCoord + j][xCoord].getSectionStatus() != WATER) {
                viableDirections[2] = -1;
            }  
            if (yCoord - j < curShip.getShipType()
                    || grid[yCoord - j][xCoord].getSectionStatus() != WATER) {
                viableDirections[3] = -1;
            }

        }
        int chosenDirection = -1;

        for (int k = 0; k < viableDirections.length; k++) {
            if (viableDirections[k] != -1) {
                chosenDirection = viableDirections[k];
            }

        }
        return chosenDirection;
    }
    /**
     * Load ships into grid computing randomly their positions
     */
    public void loadGrid() {
        Random rand = new Random();
        for (int i = 0; i < Ships.size(); i++) {

            int randX = rand.nextInt(9);
            int randY = rand.nextInt(9);

            //preform check on weather positions to the left/right up/down were 
            //free to place a ship
            ShipLinkedList curShip = Ships.get(i);

            int chosenDirection = viableDirection(curShip, randY, randX);
            if (chosenDirection == -1 || grid[randY][randX].getSectionStatus() != WATER) {
                --i;
                continue;
            }

            //if free places ship sections on grid else decrements 
            //the loop and tries again in a different position
            for (int l = 0; l < curShip.getShipType(); l++) {
                if (chosenDirection == 0) {
                    grid[randY][randX + l] = curShip.getShipSection(l);
                } else if (chosenDirection == 1) {
                    grid[randY][randX - l] = curShip.getShipSection(l);
                } else if (chosenDirection == 2) {
                    grid[randY + l][randX] = curShip.getShipSection(l);
                } else if (chosenDirection == 3) {
                    grid[randY - l][randX] = curShip.getShipSection(l);
                }
            }
        }
    }

    /**
     * get current grid state
     *
     * @return current grid
     */
    public Section[][] getGrid() {
        // Get current grid state
        return this.grid;

    }

    /**
     * get array of ships
     *
     * @return current array of ships
     */
    public ArrayList<ShipLinkedList> getShips() {
        // return array of ships
        return this.Ships;
    }

    /**
     * play a move computed by the bot
     *
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
        if (grid[positionY][positionX].getShipID() != WATER) {
            ShipLinkedList hitShip = Ships.get(grid[positionY][positionX].getShipID() - 1);
            for (int i = 0; i < hitShip.getShipType(); i++) {
                if (hitShip.getShipSection(i).getSectionStatus() != HIT) {
                    return;
                }

            }
            hitShip.setShipStatus(false);

        }
    }
}
