package nz.daved.starbattle.game;

import nz.daved.starbattle.StarBattleGame;
import nz.daved.starbattle.StarBattleGameMove;
import nz.daved.starbattle.bots.FirstSquareBot;
import nz.daved.starbattle.bots.LastSquareBot;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BotGameBoardTest {

    @Test
    public void testIsValidGridValue() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());

        assertFalse(bgb.isValidGridValue(0));
        assertTrue(bgb.isValidGridValue(1));
        assertTrue(bgb.isValidGridValue(2));
        assertTrue(bgb.isValidGridValue(3));
        assertFalse(bgb.isValidGridValue(4));
        assertFalse(bgb.isValidGridValue(3213213));
        assertFalse(bgb.isValidGridValue(54378534));
    }

    @Test
    public void testGetGrid() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());

        assertFalse(bgb.getGrid() == bgb.getGrid());
        assertTrue(bgb.getGrid().length == 10);
        Arrays.stream(bgb.getGrid()).forEach(col -> assertTrue(col.length == 10));
    }

    @Test
    public void testKillShip() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        Ship ship = bgb.getShips().get(0);
        List<Coordinate> coords = ship.getCoordinates();
        bgb.killShip(ship);
        assertTrue(coords.stream().allMatch(coord -> bgb.getStateOfCoordinate(coord) == 3));
    }

    @Test
    public void testGetAllCoordinates() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());

        assertTrue(bgb.getAllCoordinates().size() == 100);
    }

    @Test
    public void testAttackCoordinate() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        Ship ship = bgb.getShips().get(0);
        Coordinate coord = ship.getCoordinates().get(0);
        assertTrue(bgb.getStateOfCoordinate(coord) == 0);
        bgb.attackCoordinate(coord, false);
        assertTrue(bgb.getStateOfCoordinate(coord) == 1);
        bgb.attackCoordinate(coord, true);
        assertTrue(bgb.getStateOfCoordinate(coord) == 2);
    }

    @Test
    public void testAttackCoordinateTillSunk() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        Ship ship = bgb.ships.get(0);
        ship.getCoordinates().forEach(x -> {
            assertTrue(bgb.getShipAtCoordinate(x).equals(ship));
            bgb.attackCoordinate(x, true);
        });
        assertTrue(ship.getHealth() == 0);
        assertFalse(ship.isAlive());
    }

    @Test
    public void testGetGameBoard(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());

        LinkedList<Integer> gameBoard = bgb.getGameBoard();
        int[][] grid = bgb.getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                assertTrue(gameBoard.get(i * 10 + j) == grid[i][j]);
            }
        }
    }

    @Test
    public void testGetValidNeighbours() {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        assertTrue(bgb.getAllValidCoordinates().containsAll(bgb.getNeighbourValidCoordinates(new Coordinate(0,0))));
        assertTrue(bgb.getAllValidCoordinates().containsAll(bgb.getNeighbourValidCoordinates(new Coordinate(-10, -10))));
        assertFalse(bgb.getNeighbourValidCoordinates(new Coordinate(-10, -10)) == null);
        assertFalse(bgb.getNeighbourValidCoordinates(new Coordinate(0, 0)).isEmpty());
        assertTrue(bgb.getNeighbourValidCoordinates(new Coordinate(-10, -10)).isEmpty());
    }

    @Test
    public void testCoordinatesWithState(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        ShipGameBoard sgb = new ShipGameBoard();
        BotGameBoard bgb = sgb.generateBotMap(1, sbg.getHistory());
        bgb.getCoordinatesWithState(0).forEach(coord -> bgb.attackCoordinate(coord, sgb.isShip(coord)));
        assertTrue(bgb.getCoordinatesWithState(3).size() == sgb.getShipSizes().stream().mapToInt(Integer::intValue).sum());
        bgb.fillGrid(0);
        assertTrue(bgb.getCoordinatesWithState(1).size() == 0);
        assertTrue(bgb.getCoordinatesWithState(2).size() == 0);
        assertTrue(bgb.getCoordinatesWithState(3).size() == 0);
    }

    @Test
    public void testGetLastHitCoord() {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        ShipGameBoard sgb = new ShipGameBoard();
        BotGameBoard bgb = sgb.generateBotMap(1, sbg.getHistory());

        assertFalse(bgb.getLastHitMove() == null);
    }

    @Test
    public void testGetFirstValidCoordinate() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        assertTrue(bgb.getFirstValidCoordinate().equals(new Coordinate(0, 0)));
    }

    @Test
    public void testGetLastValidCoordinate() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        assertTrue(bgb.getLastValidCoordinate().equals(new Coordinate(9, 9)));
    }

    @Test
    public void testGetAllValidCoordinates() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        assertTrue(bgb.getAllValidCoordinates().size() == 100);
        bgb.fillGrid(2);
        assertTrue(bgb.getAllValidCoordinates().size() == 0);
        bgb.fillGrid(1);
        assertTrue(bgb.getAllValidCoordinates().size() == 0);
    }

    @Test
    public void testIsValidMove() throws Exception {
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        assertTrue(bgb.isValidMove(new Coordinate(0, 0)));
        bgb.fillGrid(1);
        assertFalse(bgb.isValidMove(new Coordinate(0, 0)));
    }

    @Test
    public void testCanAttackCoordinate(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        assertTrue(bgb.canAttackCoordinate(new Coordinate(0, 0)));
        bgb.fillGrid(1);
        assertFalse(bgb.canAttackCoordinate(new Coordinate(0, 0)));

    }

    @Test
    public void testGetLastMove(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        Coordinate rootCoord = new Coordinate(0,0);
        sbg.testDoMove(rootCoord, sbg.getPlayer1());
        StarBattleGameMove historyMove = sbg.getHistory().get(0);
        assertTrue((rootCoord.getX() == historyMove.getCoord().getX()) && (rootCoord.getY() == historyMove.getCoord().getY()));
    }

    @Test
    public void testCheckStateOfCoordinate(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.grid[0][0] = 0;
        assertTrue(bgb.checkStateOfCoordinate(new Coordinate(0, 0), 0));
        bgb.grid[0][0] = 1;
        assertTrue(bgb.checkStateOfCoordinate(new Coordinate(0, 0), 1));

    }
    @Test
    public void testGetLastHitMove(){
        assertTrue(true);
    }
    @Test
    public void testLastMoveSunkBot(){
        assertTrue(true);

    }

    @Test
    public void testGetStateOfCoordinateAtPosition(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        bgb.grid[1][1] = 1;
        Coordinate bottomCoord = new Coordinate(1,2);
        assertTrue(bgb.getStateOfCoordinateAtPosition(bottomCoord,"up") == 1);
        Coordinate leftCoord = new Coordinate(0,1);
        assertTrue(bgb.getStateOfCoordinateAtPosition(leftCoord,"right") == 1);
        Coordinate rightCoord = new Coordinate(2,1);
        assertTrue(bgb.getStateOfCoordinateAtPosition(rightCoord,"left") == 1);
        Coordinate topCoord = new Coordinate(1,0);
        assertTrue(bgb.getStateOfCoordinateAtPosition(topCoord, "down") == 1);
    }

    @Test
    public void testGetNeighbourStates(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        LinkedList<Integer> neigbourArray = new LinkedList<>();
        neigbourArray.addAll(Arrays.asList(0, 1, 2, 3));
        bgb.grid[1][0] = 0;
        bgb.grid[2][1] = 1;
        bgb.grid[1][2] = 2;
        bgb.grid[0][1] = 3;
        LinkedList<Integer> coordNeigbourStates = bgb.getNeighbourStates(new Coordinate(1, 1));
        assertArrayEquals(neigbourArray.toArray(), coordNeigbourStates.toArray());
    }

    @Test
    public void testFindAndHitNeighbour(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        bgb.grid[1][0] = 2;
        bgb.grid[2][1] = 0;
        bgb.grid[1][2] = 0;
        bgb.grid[0][1] = 0;
        Coordinate selectedCoord = bgb.findAndHitNeighbour(new Coordinate(1, 1));
        assertTrue(selectedCoord.getX() == 1 && selectedCoord.getY() == 2);
    }
    @Test

    public void testFindNeigbourErrorPrevention(){
        StarBattleGame sbg = new StarBattleGame("1", new FirstSquareBot("1"), new LastSquareBot("2"));
        BotGameBoard bgb = new ShipGameBoard().generateBotMap(1, sbg.getHistory());
        bgb.fillGrid(0);
        bgb.grid[1][0] = 2;
        bgb.grid[2][1] = 2;
        bgb.grid[1][2] = 2;
        bgb.grid[0][1] = 2;
        Coordinate selectedCoord = bgb.findAndHitNeighbour(new Coordinate(1, 1));
        assertTrue(selectedCoord.getX() == 0 && selectedCoord.getY() == 0);

    }
}