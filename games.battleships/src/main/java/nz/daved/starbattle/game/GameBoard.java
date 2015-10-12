package nz.daved.starbattle.game;

import com.google.common.annotations.VisibleForTesting;
import nz.daved.starbattle.StarBattleGameSchematic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The abstract class GameBoards are based off
 */
public abstract class GameBoard {

    private final List<Integer> shipSizes;
    protected int[][] grid;
    protected int width;
    protected int height;
    protected int remainingShips;

    @VisibleForTesting
    List<Ship> ships;

    /**
     * Creates a map initally set to all 0's
     *
     * @param _width  The width of the grid
     * @param _height The height of the grid
     * @param _ships  The List of ships
     */
    public GameBoard(List<Integer> _ships, int _width, int _height) {
        this.shipSizes = _ships;
        this.width = _width;
        this.height = _height;
        this.grid = new int[_width][_height];
        this.remainingShips = _ships.size();

        fillGrid(0);
    }

    public GameBoard(StarBattleGameSchematic sbgc) {
        this(sbgc.getShips(), sbgc.getWidth(), sbgc.getHeight());
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
     * @return Either the state, or else -1 if it cant be accessed
     */
    public int getStateOfCoordinate(int x, int y) {
        if (x > -1 && 10 > x && y > -1 && 10 > y) {
            return grid[x][y];
        } else {
            return -1;
        }
    }

    /**
     * @return Either the state, or else -1 if it cant be accessed
     */
    public int getStateOfCoordinate(Coordinate coord) {
        return getStateOfCoordinate(coord.getX(), coord.getY());
    }


    /**
     * Sets a grid square to a specific value
     *
     * @param coord The Coordinate to set
     * @param val   The Value
     */
    protected void setCellTo(Coordinate coord, int val) {
        if (isValidGridValue(val)) {
            grid[coord.getX()][coord.getY()] = val;
        } else {
            throw new IllegalArgumentException("Tried to set an invalid grid value " + val);
        }
    }

    protected Ship getShipAtCoordinate(Coordinate coord) {
        return ships.stream()
                .filter(x -> x.getCoordinates().contains(coord))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Coordinate had no ship attached: " + coord.toString()));
    }

    /**
     * Fills the grid with 0's
     */
    protected void fillGrid(int number) {
        Arrays.stream(grid).forEach(x -> Arrays.fill(x, number));
    }

    protected void decrementRemainingShips() {
        remainingShips--;
    }

    protected List<Ship> getShips() {
        return ships.stream()
                .map(Ship::new)
                .collect(Collectors.toList());
    }

    public int getRemainingShips() {
        return remainingShips;
    }

    public List<Integer> getShipSizes() {
        return shipSizes.stream()
                .map(Integer::new)
                .collect(Collectors.toList());
    }
}
