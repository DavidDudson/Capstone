package soGaCoGameTemplate.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A Map of all known ship locations
 */
public class ShipMap extends GameBoard {

    private long seed;
    private Random rand;

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
        seed = new Random().nextLong();
        rand = new Random(seed);
        placeShipsOnGrid();
    }

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
    public ShipMap(int _patrolBoatCount, int _destroyerCount, int _battleshipCount, int _aircraftCarrierCount, int _width, int _height, int _seed) {

        this(_patrolBoatCount, _destroyerCount, _battleshipCount, _aircraftCarrierCount, _width, _height);
        this.seed = _seed;
        this.rand = new Random(seed);
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
     * Check if a specific grid square contains a ship
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
        List<Integer> orderedShipList = getShipList();
        orderedShipList.sort(Comparator.comparing(Integer::intValue)); //This line is probably irrelevant but i will leave it
        Collections.reverse(orderedShipList); //Reverse so largest ship gets place first
        orderedShipList.forEach(this::placeShip);
    }

    /**
     * Place a single ship
     *
     * @param shipSize The length of the ship
     */
    private void placeShip(Integer shipSize) {
        Ship ship = createNewShip(shipSize);
        ship.getCoordinates().forEach(coord -> setSquareTo(coord.getRow(),coord.getCol(),1));
    }

    /**
     * Get a new ship with valid coordinates
     * @param shipSize The ship length
     * @return A ship instance
     */
    private Ship createNewShip(int shipSize){
        int direction = -1;
        int timesTried = 0;
        int row = 0;
        int col = 0;
        while (direction == -1) {
            row = rand.nextInt(9);
            col = rand.nextInt(9);

            direction = chooseADirection(row, col, shipSize);
            if (timesTried < 100) {
                timesTried++;
            } else {
                throw new IllegalArgumentException("Couldn't place ship of size: " + shipSize);
            }
        }
        return new Ship(row, col, shipSize, direction);
    }

    /**
     * Chooses which direction at random to place the ship
     *
     * @param row      The Row
     * @param col      The Column
     * @param shipSize The length of the ship
     * @return the direction to go
     */
    private int chooseADirection(int row, int col, Integer shipSize) {
        Random directionRand = new Random(seed); //Make it easier to repeat the generation of ships
        int[] possibleDirections = IntStream.range(0, 3).filter(x -> isValidDirection(row, col, shipSize, x)).toArray();
        return possibleDirections[directionRand.nextInt(3)];
    }

    /**
     * Checks whether or not a ship can be placed in a certain direction
     *
     * @param row       The Row
     * @param col       The Column
     * @param shipSize  The length of the ship
     * @param direction The direction in question
     * @return Whether a ship can be placed in that direction
     */
    private boolean isValidDirection(int row, int col, int shipSize, int direction) {
        boolean valid = false;
        switch (direction) {
            case 0:
                valid = IntStream.range(0, shipSize).allMatch(x -> getSquare(row + x, col) == 0);
                break;
            case 1:
                valid = IntStream.range(0, shipSize).allMatch(x -> getSquare(row - x, col) == 0);
                break;
            case 2:
                valid = IntStream.range(0, shipSize).allMatch(x -> getSquare(row, col + x) == 0);
                break;
            case 3:
                valid = IntStream.range(0, shipSize).allMatch(x -> getSquare(row, col - x) == 0);
        }
        return valid;
    }

    /**
     * The seed used to generate the ship positions
     *
     * @return The Random Seed
     */
    public long getSeed() {
        return seed;
    }
}
