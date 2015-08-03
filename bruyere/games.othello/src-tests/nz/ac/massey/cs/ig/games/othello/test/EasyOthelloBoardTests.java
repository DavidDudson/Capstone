package nz.ac.massey.cs.ig.games.othello.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloBoard;

import org.junit.Test;

public class EasyOthelloBoardTests {

	private Object bot1 = 1;
	private Object bot2 = 2;
	
	@Test
	public void testBoardIsInitializedRight() {
		OthelloBoard board = new OthelloBoard(bot1, bot2, 8);
		
		int middle = 8/2;
		
		assertEquals("Field [3,3] should be set", 1, board.compareFieldTo(middle, middle, bot1));
		assertEquals("Field [4,4] should be set", 1, board.compareFieldTo(middle+1, middle+1, bot1));
		assertEquals("Field [4,3] should be set", 1, board.compareFieldTo(middle+1, middle, bot2));
		assertEquals("Field [3,4] should be set", 1, board.compareFieldTo(middle, middle+1, bot2));
	}
	
	@Test
	public void createForDoc() {
		OthelloBoard board = new OthelloBoard(bot1, bot2, 8);
		System.out.println(board);
	}

	@Test
	public void testBoardCanNotBeInitializedWithWrongFieldSizes() {
		try {
			new OthelloBoard(bot1, bot2, 9);
			fail("The creation of a invalid OthelloBoard should throw an exception");
		} catch(UnsupportedOperationException e) {}
	}
	
	@Test
	public void testValuesAreSpread() {
		EasyOthelloBoard board = new EasyOthelloBoard(bot1, bot2);
		
		board.makeMove(6, 4);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfMe(), 4);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfEnemy(), 1);
		board.switchPlayerPerspective();
		
		board.makeMove(6, 3);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 3);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 3);
		board.switchPlayerPerspective();
		
		board.setField(5, 3, bot1);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 5);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 2);

		board.setField(4, 3, bot2);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 3);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 5);
		
		board.setField(3, 2, bot1);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 5);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 4);
		
		board.setField(6, 5, bot2);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 3);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 7);
		
		board.setField(4, 6, bot1);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 6);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 5);
		
		board.setField(2, 1, bot2);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 3);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 9);
		
		board.setField(7, 3, bot1);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfBlack(), 6);
		assertEquals("Seting fields should spread to other fields right", board.getScoreOfWhite(), 7);
	}
	
}
