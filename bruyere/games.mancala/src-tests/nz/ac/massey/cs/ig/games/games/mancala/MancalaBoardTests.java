package nz.ac.massey.cs.ig.games.games.mancala;

import static org.junit.Assert.*;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.mancala.MancalaBot;
import nz.ac.massey.cs.ig.games.mancala.MancalaGame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the mancala board. 
 * Use game._printBoard() to debug - this will print to board to the console in ascii art.
 * @author jens dietrich
 */

@RunWith(MockitoJUnitRunner.class)
public class MancalaBoardTests {
	
	@Mock private MancalaBot player1 = null;
	@Mock private MancalaBot player2 = null;
	private MancalaGame game = null;
	
	@Before 
	public void setup() throws Exception {
		when(player1.getId()).thenReturn("player1");
		when(player2.getId()).thenReturn("player2");
	}

	@Test
	public void testValidMove1() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
	}
	
	// player 1 encounters an IllegalArgumentException, so player 2 wins
	@Test
	public void testValidMove2() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(-1);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		assertSame(GameState.PLAYER_2_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}
	
	// player 1 encounters an IllegalArgumentException, so player 2 wins
	@Test
	public void testValidMove3() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(6);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		assertSame(GameState.PLAYER_2_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}
	
	// the respective pit 0 is empty ! 
	// player 1 encounters an IllegalArgumentException, so player 2 wins
	public void testValidMove4() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{0,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		assertSame(GameState.PLAYER_2_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}
	
	// test bot 2 moves
	@Test
	public void testValidMove5() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		game.move();
	}
	
	@Test
	public void testValidMove6() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(-1);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		game.move();
		assertSame(GameState.PLAYER_1_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}

	@Test
	public void testValidMove7() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(6);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		game.move();
		assertSame(GameState.PLAYER_1_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}
	
	// the respective pit 0 is empty ! 
	@Test
	public void testValidMove8() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{0,4,4,4,4,4},0);
		game.move();
		game.move();
		assertSame(GameState.PLAYER_1_WON,game.getState());
		assertTrue(game.getError()!=null);
		assertTrue(game.getError() instanceof IllegalMoveException);
	}
	
	// this will move one stone from last pit into mancala, player 1 wins 2:1
	@Test
	public void testTermination1() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(5);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{0,0,0,0,0,1},1,new int[]{0,0,0,0,0,1},0);
		game.move();		
		assertSame(GameState.PLAYER_1_WON,game.getState());
		assertEquals(2,game._getStonesInMancala1());
		assertEquals(1,game._getStonesInMancala2());
	}
	
	// this will move one stone from last pit into mancala, player 2 wins 2:1
	@Test
	public void testTermination2() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(5);
		game = new MancalaGame("test-game",player1,player2,new int[]{1,0,0,0,0,0},0,new int[]{0,0,0,0,0,1},1);
		game.move();
		game.move();
		assertSame(GameState.PLAYER_2_WON,game.getState());
		assertEquals(1,game._getStonesInMancala1());
		assertEquals(2,game._getStonesInMancala2());
	}
	
	// player 1 terminates the game, but player 2 still wins because he has three 
	// stones still on the board
	@Test
	public void testTermination3() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(5);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{0,0,0,0,0,1},1,new int[]{0,0,2,0,0,1},0);
		game.move();
		assertSame(GameState.PLAYER_2_WON,game.getState());
		assertEquals(2,game._getStonesInMancala1());
		assertEquals(3,game._getStonesInMancala2());
	}
	
	// player 2 terminates the game, but this is still a tie because player one gains another
	// stone still on the board
	@Test
	public void testTermination4() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(4);
		when(player2.nextMove(Mockito.any())).thenReturn(5);
		game = new MancalaGame("test-game",player1,player2,new int[]{0,0,0,0,1,0},0,new int[]{0,0,0,0,0,1},0);
		game.move();
		game.move();
		assertSame(GameState.TIE,game.getState());
		assertEquals(1,game._getStonesInMancala1());
		assertEquals(1,game._getStonesInMancala2());
	}
	
	// test the state of a game not yet terminated
	@Test
	public void testGameState1() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(4);
		when(player2.nextMove(Mockito.any())).thenReturn(5);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		assertSame(GameState.NEW,game.getState());
	}
	
	// test the state of a game not yet terminated
	@Test
	public void testGameState2() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		assertSame(GameState.WAITING_FOR_PLAYER_2,game.getState());
	}
	
	// test the state of a game not yet terminated
	@Test
	public void testGameState3() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		game.move();
		assertSame(GameState.WAITING_FOR_PLAYER_1,game.getState());
	}
	
	// test playing a large number of stones, and whether the move "comes around"
	@Test
	public void testLargeNumberOfStones() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{30,0,0,0,0,0},0,new int[]{0,0,0,0,0,1},0);
		//game._printBoard();
		game.move();
		//game._printBoard();
		assertEquals(2,game._getStonesInMancala1());
		assertEquals(0,game._getStonesInMancala2());
	}
	
	// test additional turn gained when player places last stone in own mancala
	@Test
	public void testBonusMoveRule1() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(2);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		assertSame(GameState.WAITING_FOR_PLAYER_1,game.getState());
	}
	
	// test additional turn gained when player places last stone in own mancala
	@Test
	public void testBonusMoveRule2() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(2);
		when(player2.nextMove(Mockito.any())).thenReturn(0);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,0},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		when(player1.nextMove(Mockito.any())).thenReturn(5); // now there is a stone in last pit ! 
		game.move();
		assertSame(GameState.WAITING_FOR_PLAYER_1,game.getState());
	}
	
	// test additional turn gained when player places last stone in own mancala
	@Test
	public void testBonusMoveRule3() throws Exception {
		when(player1.nextMove(Mockito.any())).thenReturn(0);
		when(player2.nextMove(Mockito.any())).thenReturn(2);
		game = new MancalaGame("test-game",player1,player2,new int[]{4,4,4,4,4,4},0,new int[]{4,4,4,4,4,4},0);
		game.move();
		game.move();
		assertSame(GameState.WAITING_FOR_PLAYER_2,game.getState());
	}
	
	
}
