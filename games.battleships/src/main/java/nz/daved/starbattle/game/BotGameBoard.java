package nz.daved.starbattle.game;

import com.google.common.primitives.Ints;
import nz.daved.starbattle.StarBattleGameMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * A grid that the bot fills out to kill all ships before winning.
 */
public class BotGameBoard extends GameBoard {

    private final int player;
    private final List<StarBattleGameMove> history;

    /**
     * Generate a botMap
     *
     * @param shipGameBoard The shipMap to use as a schema
     * @param player        Which player the boot is
     * @param history       The game history
     */
    public BotGameBoard(ShipGameBoard shipGameBoard, int player, List<StarBattleGameMove> history) {
        super(shipGameBoard.getShipSizes(), shipGameBoard.width, shipGameBoard.height);
        this.player = player;
        this.history = history;
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

    public boolean isValidMove(Coordinate coord) {
        return getState(coord) == 0;
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
        return new LinkedList<>();
    }

    public Coordinate getFirstValidCoordinate() {
        return getAllValidCoordinates().get(0);
    }

    public Coordinate getLastValidCoordinate() {
        List<Coordinate> arr = getAllValidCoordinates();
        return arr.get(arr.size() - 1);
    }

    public List<Coordinate> getAllValidCoordinates() {
        List<Coordinate> coords = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    coords.add(new Coordinate(i, j));
                }
            }
        }
        return coords;
    }

    public List<Coordinate> getCoordinatesWithState(int state){
        LinkedList<Coordinate> stateCoordinates = new LinkedList<>();
        for(int y = 0; y < height; y++){
            for(int x = 0 ;x < width; x++){
                if(grid[x][y] == state){
                    stateCoordinates.add(new Coordinate(x,y));
                }
            }


        }
        return stateCoordinates;
    }

    public List<Coordinate> getNeighbourValidCoordinates(Coordinate coordinate) {
       return coordinate.getNeighbours().stream()
               .filter(this::canAttackCoordinate)
               .collect(Collectors.toList());
    }

    public Coordinate getLastMove() {

        int length = this.getPlayerMoves().size() - 1;
        System.out.println(length);
        return this.getPlayerMoves().get(length);
    }

    public Boolean canAttackCoordinate(Coordinate coordinate) {
        return getAllValidCoordinates().contains(coordinate);
    }

    public List<StarBattleGameMove> getHistory() {
        if (player == 1) {
            return history.stream().filter(StarBattleGameMove::isPlayer1).collect(Collectors.toList());
        } else {
            return history.stream().filter(move -> !move.isPlayer1()).collect(Collectors.toList());
        }
    }
}
