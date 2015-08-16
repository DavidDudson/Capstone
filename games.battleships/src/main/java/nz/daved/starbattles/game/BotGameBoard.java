package nz.daved.starbattles.game;

import com.google.common.primitives.Ints;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A grid that the bot fills out to kill all ships before winning.
 */
public class BotGameBoard extends GameBoard {

    /**
     * Generate a botMap
     *
     * @param shipGameBoard The shipMap to use as a schema
     */
    public BotGameBoard(ShipGameBoard shipGameBoard) {
        super(shipGameBoard.getShipSizes(), shipGameBoard.width, shipGameBoard.height);
        this.ships = shipGameBoard.getShips();
        this.shipMap = shipGameBoard.getShipMap();
    }

    /**
     * Returns true if the value is 1, 2 or 3
     *
     * @param val The value to check
     * @return Whether or not the grid value is valid
     */
    @Override
    protected boolean isValidGridValue(int val) {
        int[] possibleValues = {1, 2, 3};
        return Ints.contains(possibleValues, val);
    }

    /**
     * Changes the ships state to sunk
     *
     * @param ship The ship to kill
     */
    protected void killShip(Ship ship) {
        ship.getCoordinates().forEach(x -> setCellTo(x, 3));
        decrementRemainingShips();
    }

    /**
     * Reduces a ships health and can kill it
     *
     * @param coord The coordinate to attack
     */
    public void attackCoordinate(Coordinate coord, boolean wasShip) {
        setCellTo(coord, wasShip ? 2 : 1);
        if (wasShip) {
            Ship ship = shipMap.get(coord);
            ship.reduceHealth();
            if (!ship.isAlive()) killShip(ship);
        }
    }

    /**
     * Returns the next valid coordinate
     * This basically scans for the first 0 in the array
     *
     * @return The next valid coordinate
     */
    public Coordinate getNextValidCoordinate() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new Coordinate(i, j);
                }
            }
        }
        //Effectively crash the bot, this should never be called
        throw new IndexOutOfBoundsException("Game didnt terminate properly, bot attempted to got out of bounds");
    }


}
