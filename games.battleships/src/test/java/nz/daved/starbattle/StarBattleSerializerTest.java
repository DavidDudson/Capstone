package nz.daved.starbattle;

import nz.daved.starbattle.bots.FirstSquareBot;
import nz.daved.starbattle.bots.LastSquareBot;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class StarBattleSerializerTest {

    @Test
    public void testEncodeGame() throws Exception {
        //TODO Find a way to test this nicely
    }

    @Test
    public void testGetGameContentType() throws Exception {
        StarBattleSerializer sbs = new StarBattleSerializer();
        assertTrue(sbs.getGameContentType().equals("application/json"));
    }

    @Test
    public void testEncodeGameCompact() throws Exception {
        StarBattleSerializer sbs = new StarBattleSerializer();
        StarBattleGame sbg = new StarBattleGame("dsa",new FirstSquareBot("dsa"), new LastSquareBot("dsad"));
        assertTrue(sbs.encodeGameCompact(sbg) == null);
    }

    @Test
    public void testSupportsCompactGameEncoding() throws Exception {
        StarBattleSerializer sbs = new StarBattleSerializer();
        assertFalse(sbs.supportsCompactGameEncoding());
    }
}