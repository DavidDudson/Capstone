package nz.daved.starbattle.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testAddToX() throws Exception {
        assertTrue(new Coordinate(1, 1).equals(new Coordinate(0, 1).addToX(1)));
        assertTrue(new Coordinate(2, 1).equals(new Coordinate(0, 1).addToX(2)));
        assertTrue(new Coordinate(10, 1).equals(new Coordinate(0, 1).addToX(10)));

        Coordinate c = new Coordinate(1,2);
        assertFalse(c.addToX(1) ==  c);
    }

    @Test
    public void testTakeFromX() throws Exception {
        assertTrue(new Coordinate(9, 1).equals(new Coordinate(10, 1).takeFromX(1)));
        assertTrue(new Coordinate(8, 1).equals(new Coordinate(10, 1).takeFromX(2)));
        assertTrue(new Coordinate(0, 1).equals(new Coordinate(10, 1).takeFromX(10)));

        Coordinate c = new Coordinate(1,2);
        assertFalse(c.takeFromX(1) ==  c);
    }

    @Test
    public void testAddToY() throws Exception {
        assertTrue(new Coordinate(1, 1).equals(new Coordinate(1, 0).addToY(1)));
        assertTrue(new Coordinate(1, 2).equals(new Coordinate(1, 0).addToY(2)));
        assertTrue(new Coordinate(1, 10).equals(new Coordinate(1, 0).addToY(10)));

        Coordinate c = new Coordinate(1,2);
        assertFalse(c.addToY(1) ==  c);
    }

    @Test
    public void testTakeFromY() throws Exception {
        assertTrue(new Coordinate(1, 0).equals(new Coordinate(1, 1).takeFromY(1)));
        assertTrue(new Coordinate(1, 0).equals(new Coordinate(1, 2).takeFromY(2)));
        assertTrue(new Coordinate(1, 0).equals(new Coordinate(1, 10).takeFromY(10)));

        Coordinate c = new Coordinate(1,2);
        assertFalse(c.takeFromY(1) ==  c);
    }

    @Test
    public void testGetX() throws Exception {
        assertTrue(new Coordinate(1,2).getX() == 1);
        assertTrue(new Coordinate(32,2).getX() == 32);
    }

    @Test
    public void testGetY() throws Exception {
        assertTrue(new Coordinate(1,2).getY() == 2);
        assertTrue(new Coordinate(1,32).getY() == 32);
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(new Coordinate(1,2).toString().equals("(1,2)"));
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(new Coordinate(1,2).hashCode() == new Coordinate(1,2).hashCode());
        assertFalse(new Coordinate(3,2).hashCode() == new Coordinate(1,2).hashCode());
        assertFalse(new Coordinate(1,221).hashCode() == new Coordinate(1,2).hashCode());
    }

    @Test
    public void testGetNeighbours() throws Exception {
        assertTrue(new Coordinate(1,2).getNeighbours().contains(new Coordinate(1,1)));
        assertTrue(new Coordinate(1,2).getNeighbours().contains(new Coordinate(2,2)));
        assertTrue(new Coordinate(1,2).getNeighbours().contains(new Coordinate(0,2)));
        assertTrue(new Coordinate(1,2).getNeighbours().contains(new Coordinate(1,3)));
        assertTrue(new Coordinate(1,2).getNeighbours().size() == 4);
    }


}