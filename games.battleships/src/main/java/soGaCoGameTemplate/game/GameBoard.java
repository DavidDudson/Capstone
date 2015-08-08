package soGaCoGameTemplate.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The abstract class GameBoards are based off
 */
public abstract class GameBoard {

    protected int[][] grid;
    protected int width;
    protected int height;
    protected int patrolBoatCount;
    protected int destroyerCount;
    protected int battleshipCount;
    protected int aircraftCarrierCount;
    protected int remainingShips;

    /**
     * Creates a map initally set to all 0's
     *
     * @param _patrolBoatCount      The number of PatrolBoats (Size 2)
     * @param _destroyerCount       The number of Destroyers or Submarines (Size 3)
     * @param _battleshipCount      The number of Battleships (Size 4)
     * @param _aircraftCarrierCount The number of Aircraft Carriers (Size 5)
     * @param _width                The width of the grid
     * @param _height               The height of the grid
     */
    GameBoard(int _patrolBoatCount, int _destroyerCount, int _battleshipCount,
              int _aircraftCarrierCount, int _width, int _height) {
        this.width = _width;
        this.height = _height;
        this.patrolBoatCount = _patrolBoatCount;
        this.destroyerCount = _destroyerCount;
        this.battleshipCount = _battleshipCount;
        this.aircraftCarrierCount = _aircraftCarrierCount;
        this.remainingShips = getTotalShips();

        fillGrid();
    }

    /**
     * Default Constructor, just calls main constructor with a default grid of 10*10 and the hasbro ships values
     */
    GameBoard() {
        this(1, 2, 1, 1, 10, 10);
    }

    /**
     * Returns whether or not the value is a valid placement
     * This should not be used during the generation of the initial array
     *
     * @param val The vale to check
     * @return Whether or not the grid value would be valid
     */
    protected abstract boolean isValidGridValue(int val);


    /**
     * Returns a copy of the array
     *
     * @return The duplicate grid instance
     */
    public int[][] getGrid() {
        return Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    /**
     * Returns the value inside the array
     *
     * @param coord The Coordinate to get
     * @return The Value
     */
    public int getCell(Coordinate coord) {
        return grid[coord.getX()][coord.getY()];
    }

    /**
     * Sets a grid square to a specific value
     *
     * @param coord The Coordinate to set
     * @param val The Value
     */
    public void setCellTo(Coordinate coord, int val) {
        if (isValidGridValue(val)) {
            grid[coord.getX()][coord.getY()] = val;
        } else {
            throw new IllegalArgumentException("Tried to set an invalid grid value " + val);
        }
    }

    /**
     * Fills the grid with 0's
     */
    public void fillGrid() {
        Arrays.stream(grid).forEach(x -> Arrays.fill(x, 0));
    }

    /**
     * Returns the remaining alive ships
     * Includes partially destroyed ships
     *
     * @return The number of remaining ships
     */
    public int getRemainingShips() {
        return remainingShips;
    }

    protected ArrayList<Integer> getShipList() {
        ArrayList<Integer> shipList = new ArrayList<>(getTotalShips());
        IntStream.range(0, patrolBoatCount).forEach(shipList::add);
        IntStream.range(0, destroyerCount).forEach(shipList::add);
        IntStream.range(0, battleshipCount).forEach(shipList::add);
        IntStream.range(0, aircraftCarrierCount).forEach(shipList::add);
        return shipList;
    }

    /**
     * Returns the total number of ships including all ship types
     *
     * @return The total number of ships
     */
    protected int getTotalShips() {
        return patrolBoatCount + destroyerCount + battleshipCount + aircraftCarrierCount;
    }
}
