package nz.daved.starbattle.game;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ShipGameBoardTest {

    @Test
    public void testIsValidGridValue() throws Exception {
        ShipGameBoard sgb = new ShipGameBoard();
        assertFalse(sgb.isValidGridValue(0));
        assertTrue(sgb.isValidGridValue(1));
        assertFalse(sgb.isValidGridValue(2));
        assertFalse(sgb.isValidGridValue(321321));
        assertFalse(sgb.isValidGridValue(23));
    }

    @Test
    public void testGenerateBotMap() throws Exception {
        ShipGameBoard sgb = new ShipGameBoard();
        BotGameBoard bgb = sgb.generateBotMap();
        assertFalse(bgb.getGrid() == sgb.getGrid());
        assertFalse(bgb.getShips().equals(sgb.getShips()));
        assertFalse(bgb.getShips() == sgb.getShips());
        assertTrue(bgb.getShipSizes().equals(sgb.getShipSizes()));
        assertFalse(bgb.getShipSizes() == sgb.getShipSizes());
    }


    @Test
    public void testIsShip() throws Exception {
        ShipGameBoard sgb = new ShipGameBoard();
        sgb.fillGrid(1);
        assertTrue(sgb.isShip(new Coordinate(1,2)));
        sgb.fillGrid(0);
        assertFalse(sgb.isShip(new Coordinate(1,2)));
    }

    @Test
    public void testConstructor() throws Exception {
        List<Integer> ships = new LinkedList<>();
        Collections.addAll(ships, 2,2,3,3,4,4,5);
        ShipGameBoard sgb = new ShipGameBoard();
        sgb.fillGrid(0);
        assertTrue(sgb.getShips().size() == 7);
        assertTrue(sgb.getState(new Coordinate(1,2)) == 0);
        //TODO get this test working somehow
//        Integer squareTotal = ships.stream().reduce(0, (a, b) -> a + b);
//        Long gridTotal = Stream.of(sgb.getGrid()).flatMapToInt(IntStream::of).filter(x -> x == 1).count();
//        assertTrue(gridTotal == squareTotal.longValue());
    }
}