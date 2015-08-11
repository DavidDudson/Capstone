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
        super(shipGameBoard.patrolBoatCount, shipGameBoard.destroyerCount, shipGameBoard.battleshipCount,
                shipGameBoard.aircraftCarrierCount, shipGameBoard.width, shipGameBoard.height);
    }

    /**
     * Returns true if the value is 1 or 2
     *
     * @param val The value to check
     * @return Whether or not the grid value is valid
     */
    @Override
    protected boolean isValidGridValue(int val) {
        int[] possibleValues = {1,2,3};
        return Ints.contains(possibleValues,val);

    }


}
