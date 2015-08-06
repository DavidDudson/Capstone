package soGaCoGameTemplate.game;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * A grid that the bot fills out to kill all ships before winning.
 */
public class BotMap extends GameBoard {

    /**
     * Creates a map initally empty that the bot fills out
     *
     * @param _patrolBoatCount      The number of PatrolBoats (Size 2)
     * @param _destroyerCount       The number of Destroyers or Submarines (Size 3)
     * @param _battleshipCount      The number of Battleships (Size 4)
     * @param _aircraftCarrierCount The number of Aircraft Carriers (Size 5)
     * @param _width                The width of the grid
     * @param _height               The height of the grid
     */
    public BotMap(int _patrolBoatCount, int _destroyerCount, int _battleshipCount, int _aircraftCarrierCount, int _width, int _height) {

        super(_patrolBoatCount, _destroyerCount, _battleshipCount, _aircraftCarrierCount, _width, _height);
    }

    /**
     * Call the superclass contructor and return an empty
     * map with the default sizes
     */
    public BotMap(){
        super();
    }

    /**
     * Returns true if the value is 1 or 2
     * @param val The value to check
     * @return Whether or not the grid value is valid
     */
    @Override
    protected boolean isValidGridValue(int val) {
        return val == 1 || val == 2 || val == 3;
    }


}
