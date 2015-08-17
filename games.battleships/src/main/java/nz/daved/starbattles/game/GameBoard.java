package nz.daved.starbattles.game;

import nz.daved.starbattles.StarBattleGameSchematic;

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

    protected List<Ship> ships;

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

        fillGrid();
    }

    /**
     * Default Constructor, just calls main constructor with a default grid of 10*10 and the hasbro ships values
     */
    public GameBoard() {
        this(new StarBattleGameSchematic());
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
     * Returns the value inside the array
     *
     * @param coord The Coordinate to get
     * @return The Value
     */
    public int getCellState(Coordinate coord) {
        int state = -1;
        try {
            state = grid[coord.getX()][coord.getY()];
        } catch (Exception e) {
            //Do nothing as if index goes out of bound we just dont care
        }
        return state;
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
    protected void fillGrid() {
        Arrays.stream(grid).forEach(x -> Arrays.fill(x, 0));
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
        return shipSizes;
    }
}
