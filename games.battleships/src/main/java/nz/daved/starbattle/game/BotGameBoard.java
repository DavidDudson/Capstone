package nz.daved.starbattle.game;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A grid that the bot fills out to kill all ships before winning.
 */
public class BotGameBoard extends GameBoard {

    /**
     * Generate a botMap
     *
     * @param shipGameBoard The shipMap to use as a schema
     */
    public BotGameBoard(ShipGameBoard shipGameBoard) {
        super(shipGameBoard.getShipSizes(), shipGameBoard.width, shipGameBoard.height);
        this.ships = shipGameBoard.getShips();
    }

    /**
     * Returns true if the value is 1, 2 or 3
     *
     * @param val The value to check
     * @return Whether or not the grid value is valid
     */
    @Override
    protected boolean isValidGridValue(int val) {
        int[] possibleValues = {1, 2, 3};
        return Ints.contains(possibleValues, val);
    }

    /**
     * Changes the ships state to sunk
     *
     * @param ship The ship to kill
     */
    protected void killShip(Ship ship) {
        ship.getCoordinates().forEach(x -> setCellTo(x, 3));
        decrementRemainingShips();
    }

    /**
     * Reduces a ships health and can kill it
     *
     * @param coord The coordinate to attack
     * @return whether or not a ship was sunK
     */
    public List<Coordinate> attackCoordinate(Coordinate coord, boolean wasShip) {
        setCellTo(coord, wasShip ? 2 : 1);
        if (wasShip) {
            Ship ship = getShipAtCoordinate(coord);
            ship.reduceHealth();
            if (!ship.isAlive()) {
                killShip(ship);
                return ship.getCoordinates();
            }
        }
        return new ArrayList<>();
    }

    public Coordinate getFirstValidCoordinate() {
        return getAllValidCoordinates().get(0);
    }

    public Coordinate getLastValidCoordinate() {
        List<Coordinate> arr = getAllValidCoordinates();
        return arr.get(arr.size() - 1);
    }

    public List<Coordinate> getAllValidCoordinates() {
        List<Coordinate> coords = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    coords.add(new Coordinate(i, j));
                }
            }
        }
        return coords;
    }

    public List<Coordinate> getNeighbourValidCoordinates(Coordinate coordinate){
        
    }
}
