package nz.daved.starbattle.game;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by David J. Dudson on 11/08/15.
 *
 * Test cases for the ship class
 */
public class ShipTest {

    Ship exampleShip  = new Ship(new Coordinate(2,3),5,0);
    Ship exampleShip2 = new Ship(new Coordinate(2,3),5,0);
    Ship exampleShip3 = new Ship(new Coordinate(2,3),5,0);

    @Test
    public void testReduceHealth() throws Exception {
        assertTrue(exampleShip2.getHealth() == 5);
        exampleShip.reduceHealth();
        assertTrue(exampleShip.isAlive());
        IntStream.range(0,4).forEach(x -> exampleShip.reduceHealth());
        assertFalse(exampleShip.isAlive());
    }

    @Test
    public void testIsAlive() throws Exception {
        assertTrue(exampleShip2.getHealth() == 5);
        assertTrue(exampleShip2.isAlive());
        exampleShip2.reduceHealth();
        assertTrue(exampleShip2.getHealth() == 4);
        exampleShip2.reduceHealth();
        exampleShip2.reduceHealth();
        exampleShip2.reduceHealth();
        exampleShip2.reduceHealth();
        assertTrue(exampleShip2.getHealth() == 0);
        assertFalse(exampleShip2.isAlive());
    }

    @Test
    public void testGetCoordinates() throws Exception {
        List<Coordinate> coords = exampleShip3.getCoordinates();
        assertNotNull(coords);
        Coordinate[] expectedCoords = {new Coordinate(2,3),new Coordinate(3,3),new Coordinate(4,3),new Coordinate(5,3),new Coordinate(6,3)};
        assertTrue(coords.get(0).equals(new Coordinate(2,3)));
        assertTrue(Arrays.asList(expectedCoords).equals(coords));
    }
}