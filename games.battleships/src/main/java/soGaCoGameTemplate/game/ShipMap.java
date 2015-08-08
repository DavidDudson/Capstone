package soGaCoGameTemplate.game;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A Map of all known ship locations
 */
public class ShipMap extends GameBoard {

    private long seed;
    private Random rand;

    private Map<Coordinate, Ship> coordinateList = new HashMap<>();

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
     * Override default method so that we can check whether or not
     *
     * @param coord The Coordinate to get
     * @return The result
     */
    @Override
    public int getCell(Coordinate coord) {
        int value = super.getCell(coord);
        if (value == 1) {
            Ship attackedShip = coordinateList.get(coord).reduceHealth();
            return !attackedShip.isAlive() ? 2 : 1;
        } else {
            return value;
        }
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
        ship.getCoordinates().forEach(coord -> setCellTo(coord, 1));
        ship.getCoordinates().forEach(coord -> coordinateList.put(coord, ship));
    }

    /**
     * Get a new ship with valid coordinates
     *
     * @param shipSize The ship length
     * @return A ship instance
     */
    private Ship createNewShip(int shipSize) {
        int direction = -1;
        int timesTried = 0;
        int row = 0;
        int col = 0;
        while (direction == -1) {
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            Coordinate coord = new Coordinate(row,col);

            direction = chooseADirection(coord, shipSize);
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
     * @param coord    The location of the ship starting point
     * @param shipSize The length of the ship
     * @return the direction to go
     */
    private int chooseADirection(Coordinate coord, Integer shipSize) {
        Random directionRand = new Random(seed); //Make it easier to repeat the generation of ships
        int[] possibleDirections = IntStream.range(0, 3).filter(x -> isValidDirection(coord, shipSize, x)).toArray();
        return possibleDirections[directionRand.nextInt(possibleDirections.length)];
    }

    /**
     * Checks whether or not a ship can be placed in a certain direction
     *
     * @param coord     The location of the ship starting point
     * @param shipSize  The length of the ship
     * @param direction The direction in question
     * @return Whether a ship can be placed in that direction
     */
    private boolean isValidDirection(Coordinate coord, int shipSize, int direction) {
        boolean valid = false;
        switch (direction) {
            case 0:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCell(coord.addToX(x)) == 0);
                break;
            case 1:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCell(coord.takeFromX(x)) == 0);
                break;
            case 2:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCell(coord.addToY(x)) == 0);
                break;
            case 3:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCell(coord.takeFromY(x)) == 0);
        }
        return valid;
    }

    /**
     * Generate a botmap based on this shipMap
     * @return the new botmap
     */
    public BotMap generateBotMap(){
        return new BotMap(this);
    }

    /**
     * The seed used to generate the ship positions
     *
     * @return The Random Seed
     */
    public long getSeed() {
        return seed;
    }


    /**
     * Get the coords of a ship that is at a specific location
     * @param coord The Coordinate
     * @return the ship coordinates
     */
    public List<Coordinate> getShipCoordinates(Coordinate coord){
        return this.coordinateList.get(coord).getCoordinates();
    }

    public boolean isShip(Coordinate coord) {
        return grid[coord.getX()][coord.getY()] == 1;
    }
}
