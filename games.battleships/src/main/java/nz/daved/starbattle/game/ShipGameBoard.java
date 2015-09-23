package nz.daved.starbattle.game;

import nz.daved.starbattle.StarBattleGameSchematic;

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
        ships = new LinkedList<>();
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
     * Uses the ship count to place ships on the grid randomly
     */
    protected void placeShipsOnGrid() {
        List<Integer> orderedShipList = getShipSizes();
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
        ships.add(ship);
        ship.getCoordinates().forEach(coord -> setCellTo(coord, 1));
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
        System.out.println("Ship generated at: " + coord + ":" + shipSize + ":" + direction);
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
                valid = IntStream.range(0, shipSize).allMatch(x -> getState(coord.addToX(x)) == 0);
                break;
            case 1:
                valid = IntStream.range(0, shipSize).allMatch(x -> getState(coord.takeFromX(x)) == 0);
                break;
            case 2:
                valid = IntStream.range(0, shipSize).allMatch(x -> getState(coord.addToY(x)) == 0);
                break;
            case 3:
                valid = IntStream.range(0, shipSize).allMatch(x -> getState(coord.takeFromY(x)) == 0);
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

    public boolean isShip(Coordinate coord) {
        return grid[coord.getX()][coord.getY()] == 1;
    }
}
