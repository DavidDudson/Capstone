package soGaCoGameTemplate.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by David J. Dudson on 6/08/15.
 * <p>
 * Creates a ship, only used by the server,
 * to give an accurate reading of how damaged
 * ships are and where they are located, also
 * tells us when they are sunk
 */
public class Ship {

    private List<Coordinate> coordinates = new ArrayList<>();

    private int shipSize;
    private int health;

    /**
     * Creates a ship
     *
     * @param row       The Starting Row
     * @param col       The Starting Column
     * @param _shipSize  The Length of the ship
     * @param direction The
     */
    public Ship(int row, int col, int _shipSize, int direction) {
        shipSize = _shipSize;
        health = shipSize;
        populateCoordinates(row, col, direction);
    }

    /**
     * Generate the ship's coordinates based on direction and starting point
     * @param row The row
     * @param col The Column
     * @param direction The direction
     */
    private void populateCoordinates(int row, int col, int direction) {
        switch (direction) {
            case 0:
                IntStream.range(0, shipSize).forEach(x -> coordinates.add(new Coordinate(row + x, col)));
                break;
            case 1:
                IntStream.range(0, shipSize).forEach(x -> coordinates.add(new Coordinate(row - x, col)));
                break;
            case 2:
                IntStream.range(0, shipSize).forEach(y -> coordinates.add(new Coordinate(row, col + y)));
                break;
            case 3:
                IntStream.range(0, shipSize).forEach(y -> coordinates.add(new Coordinate(row, col + y)));
        }
    }

    /**
     * Tell the Ship that part of it has been hit
     */
    public void reduceHealth(){
        health--;
    }

    /**
     * Check if the ship has been sunk
     * @return True if the ships health is 0
     */
    public boolean isAlive(){
        return health > 0;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates.stream().map(Coordinate::new).collect(Collectors.toList());
    }
}
