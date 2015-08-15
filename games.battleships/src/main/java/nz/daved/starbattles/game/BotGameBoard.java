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
     * @param shipGameBoard The shipMap to use as a schema
     */
    public BotGameBoard(ShipGameBoard shipGameBoard){
        super(shipGameBoard.ships,shipGameBoard.width, shipGameBoard.height);
    }

    /**
     * Returns true if the value is 1, 2 or 3
     *
     * @param val The value to check
     * @return Whether or not the grid value is valid
     */
    @Override
    protected boolean isValidGridValue(int val) {
        int[] possibleValues = {1,2,3};
        return Ints.contains(possibleValues,val);

    }

    /**
     * Returns the next valid coordinate
     * This basically scans for the first 0 in the array
     * @return The next valid coordinate
     */
    public Coordinate getNextValidCoordinate(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return new Coordinate(i,j);
                }
            }
        }
        //Effectively crash the bot, this should never be called
        return new Coordinate(-1,-1);
    }


}
