package soGaCoGameTemplate.game;

import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A grid that the bot fills out to kill all ships before winning.
 */
public class BotMap extends GameBoard {

    /**
     * Generate a botMap
     * @param shipMap The shipMap to use as a schema
     */
    public BotMap(ShipMap shipMap){
        super(shipMap.patrolBoatCount,shipMap.destroyerCount,shipMap.battleshipCount,
                shipMap.aircraftCarrierCount,shipMap.width,shipMap.height);
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
