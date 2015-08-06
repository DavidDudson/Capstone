package soGaCoGameTemplate.game;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A Map of all known ship locations
 */
public class ShipMap extends GameBoard {

    private int patrolBoatCount;
    private int destroyerCount; //Also counts as submarine.
    private int battleshipCount;
    private int aircraftCarrierCount;

    private int remainingShips;

    /**
     * Creates a map of all ship locations based off
     * specific ship counts and grid sizes.
     *
     * @param _patrolBoatCount      The number of PatrolBoats (Size 2)
     * @param _destroyerCount       The number of Destroyers or Submarines (Size 3)
     * @param _battleshipCount      The number of Battleships (Size 4)
     * @param _aircraftCarrierCount The number of Aircraft Carriers (Size 5)
     * @param _width                The width of the grid
     * @param _height               The height of the grid
     */
    public ShipMap(int _patrolBoatCount, int _destroyerCount, int _battleshipCount, int _aircraftCarrierCount, int _width, int _height) {

        super(_patrolBoatCount, _destroyerCount, _battleshipCount, _aircraftCarrierCount, _width, _height);

        placeShipsOnGrid();
    }


    /**
     * Plug in default values from the popular hasbro board game
     * 10x10 grid with 2 battleships/submarines (3 long) and 1 of everything else
     */
    public ShipMap() {
        this(1, 2, 1, 1, 10, 10);
    }

    /**
     * Returns true for 1, otherwise everything else is Invalid
     *
     * @param val the value to check
     * @return Whether or not the grid value should be set to that value
     */
    @Override
    protected boolean isValidGridValue(int val) {
        return val == 1;
    }

    /**
     * Check if a specic grid square contains a shi[
     *
     * @param row The Row
     * @param col the Column
     * @return If the grid square does contain a ship
     */
    public boolean squareContainsShip(int row, int col) {
        return grid[row][col] == 1;
    }

    /**
     * Uses the ship count to place ships on the grid randomly
     */
    protected void placeShipsOnGrid() {

    }
}
