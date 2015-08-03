package nz.ac.massey.cs.ig.games.othello.test;

import static org.junit.Assert.*;

import org.junit.Test;

import nz.ac.massey.cs.ig.games.othello.OthelloPosition;

public class OthelloPositionTests {

	@Test
	public void testEqualsIsWorking() {
		OthelloPosition move = new OthelloPosition(4, 3);
		OthelloPosition move2 = new OthelloPosition(4, 3);

		assertEquals(
				"Two othello moves should be equally if their position is matching",
				move, move2);
	}

}
