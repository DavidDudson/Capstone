package nz.daved.starbattle;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class StarBattleGameSchematicTest {

    @Test
    public void testFullConstructor() throws Exception {
        List<Integer> ships = new LinkedList<>();
        Collections.addAll(ships, 2, 2, 3, 3, 4, 4, 5);
        StarBattleGameSchematic schematic = new StarBattleGameSchematic(ships,10,10,1L);
        assertTrue(schematic.getSeed() == 1L);
        assertTrue(schematic.getShips().equals(ships));
        assertFalse(schematic.getShips() == ships);
        assertTrue(schematic.getHeight() == 10);
        assertTrue(schematic.getWidth() == 10);
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        List<Integer> ships = new LinkedList<>();
        Collections.addAll(ships, 2,2,3,3,4,4,5);
        StarBattleGameSchematic schematic = new StarBattleGameSchematic();
        assertTrue(schematic.getSeed() == 1L);
        assertTrue(schematic.getShips().equals(ships));
        assertFalse(schematic.getShips() == ships);
        assertTrue(schematic.getHeight() == 10);
        assertTrue(schematic.getWidth() == 10);
    }

    @Test
    public void testCopyConstructor() throws Exception {
        StarBattleGameSchematic schematic = new StarBattleGameSchematic();
        StarBattleGameSchematic copy = new StarBattleGameSchematic(schematic);
        assertTrue(schematic.getSeed() == copy.getSeed());
        assertTrue(schematic.getShips().equals(copy.getShips()));
        assertFalse(schematic.getShips() == copy.getShips());
        assertTrue(schematic.getHeight() == copy.getHeight());
        assertTrue(schematic.getWidth() == copy.getHeight());

    }

}