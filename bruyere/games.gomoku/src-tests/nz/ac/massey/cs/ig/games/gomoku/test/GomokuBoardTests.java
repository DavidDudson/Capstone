package nz.ac.massey.cs.ig.games.gomoku.test;

import static org.junit.Assert.assertEquals;
import nz.ac.massey.cs.ig.games.gomoku.GomokuBoard;

import org.junit.Test;

public class GomokuBoardTests {

	private Object bot1 = 1;
	private Object bot2 = 2;
	
	@Test
	public void testInitialization() {
		GomokuBoard board =new GomokuBoard(bot1,bot2);
		//default board size should be 19
		assertEquals(9,board.getFieldSize());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInitializationFails(){
		new GomokuBoard(bot1,bot2,12);
	}
	
}
