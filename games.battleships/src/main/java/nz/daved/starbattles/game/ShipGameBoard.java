package nz.daved.starbattles.game;

import nz.daved.starbattles.StarBattleGameSchematic;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A Map of all known ship locations
 */
public class ShipGameBoard extends GameBoard {

    private long seed;
    private Random rand;

    private Map<Coordinate, Ship> coordinateList = new HashMap<>();

    /**
     * Creates a map of all ship locations based off
     * specific ship counts and grid sizes.
     *
     * @param sbgc the schematic to base the game off
     */
    public ShipGameBoard(StarBattleGameSchematic sbgc) {
        super(sbgc);
        seed = new Random().nextLong();
        rand = new Random(seed);
        placeShipsOnGrid();
    }

    /**
     * Plug in default values from the popular hasbro board game
     * 10x10 grid with 2 battleships/submarines (3 long) and 1 of everything else
     */
    public ShipGameBoard() {
        this(new StarBattleGameSchematic());
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
    public int getCellState(Coordinate coord) {
        int value = super.getCellState(coord);
        if (value == 1) {
            Ship attackedShip = coordinateList.get(coord).reduceHealth();
            System.out.println(coord);
            System.out.println(coordinateList.get(coord));
            System.out.println(attackedShip.getHealth());
            return !attackedShip.isAlive() ? 2 : 1;
        } else {
            return value;
        }
    }

    /**
     * Uses the ship count to place ships on the grid randomly
     */
    protected void placeShipsOnGrid() {
        List<Integer> orderedShipList = getShips();
        orderedShipList.sort(Comparator.comparing(Integer::intValue)); //This line is probably irrelevant but i will leave it
        Collections.reverse(orderedShipList); //Reverse so largest ship gets place first
        orderedShipList.forEach(this::placeShip);
    }

    /**
     * Place a single ship
     *
     * @param shipSize The length of the ship
     */
    private void placeShip(int shipSize) {
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
        int row;
        int col;
        Coordinate coord = new Coordinate(0, 0);
        while (direction == -1) {
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            coord = new Coordinate(row, col);

            direction = chooseADirection(coord, shipSize);
            if (timesTried < 100) {
                timesTried++;
            } else {
                throw new IllegalArgumentException("Couldn't place ship of size: " + shipSize);
            }
        }
        System.out.println(coord + ":" + shipSize + ":" + direction);
        return new Ship(coord, shipSize, direction);
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
        if (possibleDirections.length == 0) {
            return -1;
        } else {
            return possibleDirections[directionRand.nextInt(possibleDirections.length)];
        }
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
                valid = IntStream.range(0, shipSize).allMatch(x -> getCellState(coord.addToX(x)) == 0);
                break;
            case 1:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCellState(coord.takeFromX(x)) == 0);
                break;
            case 2:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCellState(coord.addToY(x)) == 0);
                break;
            case 3:
                valid = IntStream.range(0, shipSize).allMatch(x -> getCellState(coord.takeFromY(x)) == 0);
        }
        return valid;
    }

    /**
     * Generate a botmap based on this shipMap
     *
     * @return the new botmap
     */
    public BotGameBoard generateBotMap() {
        return new BotGameBoard(this);
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
     *
     * @param coord The Coordinate
     * @return the ship coordinates
     */
    public List<Coordinate> getShipCoordinates(Coordinate coord) {
        return this.coordinateList.get(coord).getCoordinates();
    }

    public boolean isShip(Coordinate coord) {
        return grid[coord.getX()][coord.getY()] == 1;
    }
}
