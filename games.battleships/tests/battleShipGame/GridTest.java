package battleShipGame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {
    Grid testGrid;
    int SIZE = 10;
    @Before
    public void setUp() throws Exception {

       testGrid = new Grid(SIZE);

    }

    @Test
    public void testGenerateShips() throws Exception {
        testGrid.generateShips();

        int numOfShips = 0;
        for(int i=0; i< testGrid.getShips().size(); i++){
                numOfShips++;
        }
        assertEquals("number of ships Correctly generated",7, numOfShips);
    }

    @Test
    public void testLoadGrid() throws Exception {

    }

    @Test
    public void testPlayMove() throws Exception {

    }
}