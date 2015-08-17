package nz.ac.massey.cs.ig.games.gomoku.test;

import static org.junit.Assert.assertEquals;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerBoard;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerPosition;
import nz.ac.massey.cs.ig.games.chinesechecker.EasyChineseCheckerBoard;

import org.junit.Test;

public class ChineseCheckerBoardTests {

	private Object bot1 = 1;
	private Object bot2 = 2;
	
	@Test
	public void testInitialization() {
		ChineseCheckerBoard board =new ChineseCheckerBoard(bot1,bot2);
		//default board size should be 8
		assertEquals(8,board.getFieldSize());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInitializationFails(){
		new ChineseCheckerBoard(bot1,bot2,9);
	}
	
	@Test
	public void test(){
		EasyChineseCheckerBoard board =new EasyChineseCheckerBoard(bot1,bot2);
		board.setMe(bot1);
		ChineseCheckerPosition piece =board.getAvailablePiecesForMe().get(0);
		piece.setDestination(4,0);
		board.makeMove(piece);
		board.removePiece(piece);
		
		board.setMe(bot2);
		piece =board.getAvailablePiecesForMe().get(0);
		piece.setDestination(7,3);
		board.makeMove(piece);
		board.removePiece(piece);
		System.out.println(board);
		
	}
	
}
