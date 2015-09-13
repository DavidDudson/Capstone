package nz.daved.starbattle;

import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.daved.starbattle.bots.FirstSquareBot;
import nz.daved.starbattle.bots.LastSquareBot;
import nz.daved.starbattle.game.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarBattleGameTest {

    @Test
    public void testDoMove() throws Exception {
        StarBattleGame sbg = new StarBattleGame("ghj",new FirstSquareBot("21312"), new LastSquareBot("2112"));
        sbg.doMove(new Coordinate(0,0),sbg.getCurrentPlayer());
        assertTrue(sbg.getHistory().size() == 1);
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMoveException()throws Exception {
        StarBattleGame sbg = new StarBattleGame("ghj",new FirstSquareBot("21312"), new LastSquareBot("2112"));
        sbg.checkValidityOfMove(new Coordinate(20130,213),sbg.getCurrentPlayer());
    }

    @Test
    public void testValidMove() throws Exception {
        StarBattleGame sbg = new StarBattleGame("ghj",new FirstSquareBot("21312"), new LastSquareBot("2112"));
        sbg.checkValidityOfMove(new Coordinate(3,3),sbg.getCurrentPlayer());
    }
}