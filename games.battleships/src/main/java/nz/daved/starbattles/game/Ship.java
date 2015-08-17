package nz.daved.starbattles.game;

import java.util.ArrayList;
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
     * @param coord The Starting point
     * @param _shipSize The Length of the ship
     * @param direction The direction
     */
    public Ship(Coordinate coord, int _shipSize, int direction) {
        shipSize = _shipSize;
        health = shipSize;
        populateCoordinates(coord, direction);
    }

    /**
     * Generate the ship's coordinates based on direction and starting point
     *
     * @param coord The Coordinate
     * @param direction The direction
     */
    private Ship populateCoordinates(Coordinate coord, int direction) {
        switch (direction) {
            case 0:
                IntStream.range(0, shipSize).forEach(x -> coordinates.add(coord.addToX(x)));
                break;
            case 1:
                IntStream.range(0, shipSize).forEach(x -> coordinates.add(coord.takeFromX(x)));
                break;
            case 2:
                IntStream.range(0, shipSize).forEach(y -> coordinates.add(coord.addToY(y)));
                break;
            case 3:
                IntStream.range(0, shipSize).forEach(y -> coordinates.add(coord.takeFromY(y)));
        }
        return this;
    }

    /**
     * Tell the Ship that part of it has been hit
     */
    public Ship reduceHealth() {
        health--;
        return this;
    }

    /**
     * Get the health of the ship
     * @return The Health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Check if the ship has been sunk
     *
     * @return True if the ships health is 0
     */
    public boolean isAlive() {
        return health > 0;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates.stream()
                .map(Coordinate::new)
                .collect(Collectors.toList());
    }
}
