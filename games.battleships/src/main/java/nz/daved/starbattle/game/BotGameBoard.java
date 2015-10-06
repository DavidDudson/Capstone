package nz.daved.starbattle.game;

import com.google.common.primitives.Ints;
import nz.daved.starbattle.StarBattleGameMove;

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

    /**
     * ge the first coordinate not already chosen
     * @return first coodinate that is valid
     */
    public Coordinate getFirstValidCoordinate() {
        return getAllValidCoordinates().get(0);
    }

    /**
     *  get the last valid coordinate not already chosen
     * @return last coordinate that is valid
     */
    public Coordinate getLastValidCoordinate() {
        List<Coordinate> arr = getAllValidCoordinates();
        return arr.get(arr.size() - 1);
    }

    /**
     * get a list of all the coordinate not already chosen
     * @return list of all possible coordinates
     */
    public LinkedList<Coordinate> getAllValidCoordinates() {
        LinkedList<Coordinate> coords = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    coords.add(new Coordinate(i, j));
                }
            }
        }
        return coords;
    }

    /**
     * get the all positions and states of the gameBoard
     * @return list of all current states and positions of the board
     */
    public LinkedList<Integer> getGameBoard(){
        LinkedList<Integer> coords = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                    coords.add(grid[i][j]);
            }
        
        }
        return coords;
    }

    /**
     * get the state of a specified coordinate
     * @param state the bot selected coordinate to be evaluated
     * @return state of coordinate
     */
    public LinkedList<Coordinate> getCoordinatesWithState(int state){
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

    /**
     * return a list of all neighbours that havn't been hit
     * @param coordinate coordinate to be evaluated
     * @return list of avaliable coordiantes
     */
    public LinkedList<Coordinate> getNeighbourValidCoordinates(Coordinate coordinate) {
       return coordinate.getNeighbours().stream()
               .filter(this::canAttackCoordinate)
               .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * check wheather coordinate can be attacked
     * @param coordinate coordinate to be evaluated
     * @return weather coordinate is free to be attacked
     */
    public Boolean canAttackCoordinate(Coordinate coordinate) {
        return getAllValidCoordinates().contains(coordinate);
    }

    /**
     * get bot history of hit moves
     * @return list of coordinates containing already hit moves
     */
    public LinkedList<Coordinate> getHistory() {
        LinkedList<Coordinate> historyCoords = new LinkedList<>();

        for(StarBattleGameMove move: history){
            if((player == 1) && (move.isPlayer1())) {
                historyCoords.add(move.getCoord());
            }else {
                if((player == 2)&& (!move.isPlayer1()) ){
                    historyCoords.add(move.getCoord());

                    }
                }
            }
        return  historyCoords;

    }

    /**
     * get the last move played by the bot
     * @return coordinate of last move played
     */
    public Coordinate getLastMove(){
        return getHistory().get(getHistory().size());

    }

    /**
     * check to see if coordinate containes defined state
     * @param coords coordinate to be evaluated
     * @param state value to be evaluated against
     * @return weather the coordinate contains the expected coordinate
     */
    public Boolean checkStateOfCoordinate(Coordinate coords, String state){
        return grid[coords.getY()][coords.getY()] == Integer.parseInt(state);


    }

    /**
     * get the state of the last movr
     * @param state value to be evaluated against
     * @return weather the last coordinate contains the expected state
     */
    public Boolean getLastMoveState(int state){
        return grid[getLastMove().getY()][getLastMove().getY()] == state;

    }

    /**
     * get the last move of bot history that was a hit
     * @return coordinate of the last hit move
     */
    public Coordinate getLastHitMove(){
        for(int i = getHistory().size(); i > 0; i--){
            Coordinate historyCoord = getHistory().get(i);
            if(grid[historyCoord.getX()][historyCoord.getY()] == 1){
                return historyCoord;
            }

        }
        return null;

    }

    /**
     * get the state of a coordinate at a defined by the position
     * @param coord coordinate to be evaluated
     * @param position position relative to the coordinate
     * @return state of coordinate with the position
     */
    public Integer getStateOfCoordinateAtPosition(Coordinate coord, String position){
        switch (position){
            case "up" :   return grid[coord.getX()][coord.getY() - 1];
            case "right": return grid[coord.getX() + 1][coord.getY()];
            case "down" : return grid[coord.getX()][coord.getY() -1];
            case "left" : return grid[coord.getX() - 1][coord.getY()];


        }
        return  -1;


    }

    /**
     *  check if the last move sunk a bot
     * @return boolean value is last his sunk a bot
     */
    public Boolean lastMoveSinkBot(){
        Coordinate lastMove = getLastMove();
        return grid[lastMove.getX()][lastMove.getY()] == 3;
    }


    public LinkedList<Integer> getNeightbourStates(Coordinate coord){
        LinkedList<Integer> neightbourStates = new LinkedList<Integer>();


        if(coord.getY() < 0){
            neightbourStates.add(0, -1);
        }
        if(coord.getX() > 9){
            neightbourStates.add(1, -1);
        }
        if (coord.getY() < 9){
            neightbourStates.add(2, -1);
        }
        if(coord.getX() < 0){
            neightbourStates.add(3, -1);
        }

        for (int i = 0; i < neightbourStates.size(); i++) {
            if(neightbourStates.get(i) != -1){
                switch (i){
                    case 0 : neightbourStates.add(i, getStateOfCoordinateAtPosition(coord, "up"));
                            break;
                    case 1 : neightbourStates.add(i, getStateOfCoordinateAtPosition(coord, "right"));
                            break;
                    case 2 : neightbourStates.add(i, getStateOfCoordinateAtPosition(coord, "down"));
                            break;
                    case 3 : neightbourStates.add(i, getStateOfCoordinateAtPosition(coord, "left"));
                            break;

                }

            }
        }

    return neightbourStates;

    }

    /**
     * provide hit coordinate and hit appropriate neighbour based on other neighbours if none found hit first available
     * @param coord coordinate to attempt to strike
     * @return computed selected coordinate
     */
    public Coordinate findAndHitNeighbour(Coordinate coord){

        LinkedList<Integer> neighbourCoords = getNeightbourStates(coord);

        Coordinate nextMove;
        for (int i = 0; i < neighbourCoords.size(); i++) {
            if(neighbourCoords.get(i) != -1 & neighbourCoords.get(i) == 1){
                    switch (i){
                        case 0 :
                            nextMove = new Coordinate(coord.getX(), coord.getY() - 1);
                            if(checkStateOfCoordinate(nextMove, "0")){
                                return nextMove;
                            }
                            break;
                        case 1 :
                            nextMove = new Coordinate(coord.getX() + 1, coord.getY());
                            if(checkStateOfCoordinate(nextMove, "1")){
                                return nextMove;
                            }
                            break;
                        case 2 :
                            nextMove = new Coordinate(coord.getX(), coord.getY() + 1);
                            if(checkStateOfCoordinate(nextMove, "2")){
                                return nextMove;
                            }
                            break;
                        case 3 :
                            nextMove = new Coordinate(coord.getX() - 1, coord.getY());
                            if(checkStateOfCoordinate(nextMove, "3")){
                                return nextMove;
                            }
                            break;
                    }
            }
        }
        return getNeighbourValidCoordinates(coord).getFirst();



    }
}
