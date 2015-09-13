package nz.daved.starbattle;

import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;
import nz.daved.starbattle.game.Ship;
import nz.daved.starbattle.game.ShipGameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarBattleGameSupportTest {

    @Test
    public void testLoadBuiltInBots() throws Exception {
        StarBattleGameSupport sbgs = new StarBattleGameSupport();
        assertTrue(sbgs.loadBuiltInBots().size() == 2);
    }

    @Test
    public void testGetWhitelistedClasses() throws Exception {
        StarBattleGameSupport sbgs = new StarBattleGameSupport();
        assertTrue(sbgs.getWhitelistedClasses().contains(Coordinate.class));
        assertTrue(sbgs.getWhitelistedClasses().contains(BotGameBoard.class));
        assertTrue(sbgs.getWhitelistedClasses().contains(Ship.class));
        assertTrue(sbgs.getWhitelistedClasses().contains(ShipGameBoard.class));
        assertTrue(sbgs.getWhitelistedClasses().contains(StarBattleBot.class));
    }

    @Test
    public void testGetTestClass() throws Exception {
        StarBattleGameSupport sbgs = new StarBattleGameSupport();
        assertTrue(sbgs.getTestClass() == null);
    }
}