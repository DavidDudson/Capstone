package nz.ac.massey.cs.ig.games.othello.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import nz.ac.massey.cs.ig.games.othello.OthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloPosition;
import nz.ac.massey.cs.ig.games.othello.OthelloValidator;
import nz.ac.massey.cs.ig.games.othello.SimpleOthelloSerializer;

import org.junit.Test;

public class OthelloValidatorTests {

	Object player1 = 1;
	Object player2 = 2;

	@Test
	public void testGetAvailableMovesHorizontalAndVertical() {
		// @formatter:off
		String boardString = ""
				+ "-12345678\n"
				+ "1--------\n"
				+ "2--------\n"
				+ "3---+o---\n"
				+ "4---o+---\n"
				+ "5--------\n"
				+ "6--------\n"
				+ "7--------\n"
				+ "8--------\n";
		// @formatter:on

		OthelloBoard board = new SimpleOthelloSerializer().deserialize(
				boardString, player1, player2);

		OthelloValidator validator = new OthelloValidator(board);
		List<OthelloPosition> moves = validator.getAvailableMoves(player1);

		assertEquals(
				"OthelloValidator should find right amount of possible moves",
				4, moves.size());

		List<OthelloPosition> expectedMoves = Arrays.asList(new OthelloPosition(6, 3),
				new OthelloPosition(5, 2), new OthelloPosition(3, 4), new OthelloPosition(
						4, 5));

		for (OthelloPosition move : expectedMoves) {
			assertTrue(
					"With the current configuration, the move "
							+ move.toString() + "should be allowed",
					moves.contains(move));
		}
	}

	@Test
	public void testGetAvailableMovesDiagonally() {
		// @formatter:off
		String boardString = ""
				+ "-12345678\n"
				+ "1--------\n"
				+ "2----+---\n"
				+ "3---++---\n"
				+ "4---o+---\n"
				+ "5--------\n"
				+ "6--------\n"
				+ "7--------\n"
				+ "8--------\n";
		// @formatter:on

		OthelloBoard board = new SimpleOthelloSerializer().deserialize(
				boardString, player1, player2);

		OthelloValidator validator = new OthelloValidator(board);
		List<OthelloPosition> moves = validator.getAvailableMoves(player2);

		assertEquals(
				"OthelloValidator should find right amount of possible moves",
				3, moves.size());

		List<OthelloPosition> expectedMoves = Arrays.asList(new OthelloPosition(4, 2),
				new OthelloPosition(6, 4), new OthelloPosition(6, 2));

		for (OthelloPosition move : expectedMoves) {
			assertTrue(
					"With the current configuration, the move "
							+ move.toString() + "should be allowed",
					moves.contains(move));
		}
	}
}
