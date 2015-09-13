package nz.daved.starbattle.game;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BotGameBoardTest {

    @Test
    public void testIsValidGridValue() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        assertFalse(bgb.isValidGridValue(0));
        assertTrue(bgb.isValidGridValue(1));
        assertTrue(bgb.isValidGridValue(2));
        assertTrue(bgb.isValidGridValue(3));
        assertFalse(bgb.isValidGridValue(4));
        assertFalse(bgb.isValidGridValue(3213213));
        assertFalse(bgb.isValidGridValue(54378534));
    }

    @Test
    public void testKillShip() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        Ship ship = bgb.getShips().get(0);
        List<Coordinate> coords = ship.getCoordinates();
        bgb.killShip(ship);
        assertTrue(coords.stream().allMatch(coord -> bgb.getState(coord) == 3));
    }

    @Test
    public void testAttackCoordinate() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        Ship ship = bgb.getShips().get(0);
        Coordinate coord = ship.getCoordinates().get(0);
        assertTrue(bgb.getState(coord) == 0);
        bgb.attackCoordinate(coord,false);
        assertTrue(bgb.getState(coord) == 1);
        bgb.attackCoordinate(coord,true);
        assertTrue(bgb.getState(coord) == 2);
        ship.getCoordinates().forEach(x -> bgb.attackCoordinate(x,true));
        assertFalse(ship.isAlive());
        assertTrue(ship.getHealth() == 0);
    }

    @Test
    public void testGetFirstValidCoordinate() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        bgb.fillGrid(0);
        assertTrue(bgb.getFirstValidCoordinate().equals(new Coordinate(0,0)));
    }

    @Test
    public void testGetLastValidCoordinate() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        bgb.fillGrid(0);
        assertTrue(bgb.getLastValidCoordinate().equals(new Coordinate(9,9)));
    }

    @Test
    public void testGetAllValidCoordinates() throws Exception {
        BotGameBoard bgb = new ShipGameBoard().generateBotMap();
        bgb.fillGrid(0);
        assertTrue(bgb.getAllValidCoordinates().size() == 100);
        bgb.fillGrid(2);
        assertTrue(bgb.getAllValidCoordinates().size() == 0);
        bgb.fillGrid(1);
        assertTrue(bgb.getAllValidCoordinates().size() == 0);
    }
}