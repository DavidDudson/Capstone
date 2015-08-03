package nz.ac.massey.cs.ig.games.mancala;


import static org.junit.Assert.*;
import nz.ac.massey.cs.ig.core.game.Bot;

import org.junit.Test;

/**
 * (Runtime) tests used to verify a bot.
 * Note that this test case has a special constructor used to inject the bot tested, 
 * this is required by the test runner we use for dynamic verification.
 * @author jens dietrich
 */
public class JavaMancalaBotTests {
	
	private Bot<Mancala, Integer> testedBot = null;
	
	private static MancalaBot opponent = new MancalaBot("test.opponent.bot") {

		@Override
		public Integer nextMove(Mancala board) {
			for (int i=0;i<6;i++) {
				// dumb bot save strategy
				if (board.getStonesInMyPit(i) > 0) return i;
			}
			throw new IllegalStateException("Bot used as opponent in runtime verification tests encountered a problem");
		}
		
	};
	
	
	public JavaMancalaBotTests(Bot<Mancala, Integer> bot) {
		super();
		this.testedBot = bot;
	}


	@Test(timeout=100)
	public void test1 () throws Exception {
		Mancala board = new Mancala(new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		board.setPlayer1(testedBot);
		board.setPlayer2(opponent);
		board.setActivePlayer(testedBot);
		int move = testedBot.nextMove(board);
		assertTrue("Moves must select a position greater than 0",-1<move);
		assertTrue("Moves must select a position less than 6",6>move);
	}
	
	@Test(timeout=100)
	public void test2 () throws Exception {
		Mancala board = new Mancala(new int[]{0,0,0,1,0,0},0,new int[]{4,4,4,4,4,4},0);
		board.setPlayer1(testedBot);
		board.setPlayer2(opponent);
		board.setActivePlayer(testedBot);
		int move = testedBot.nextMove(board);
		assertEquals("Move must select a position with some stones",3,move);
	}
	
	
}
