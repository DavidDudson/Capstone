package nz.ac.massey.cs.ig.games.gomoku.test;

import static org.junit.Assert.*;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.gomoku.GomokuBot;
import nz.ac.massey.cs.ig.games.gomoku.GomokuGame;
import nz.ac.massey.cs.ig.games.gomoku.bots.DumbBot;
import nz.ac.massey.cs.ig.games.gomoku.bots.RandomBot;

import org.junit.Ignore;
import org.junit.Test;

public class BotTests {

	@Test
	public void testDumbBotsCanCompleteAGame() throws IllegalMoveException {
		GomokuBot bot1 = new DumbBot("dumb1");
		GomokuBot bot2 = new DumbBot("dumb2");
		
		GomokuGame game = new GomokuGame("3",bot1,bot2);
		int c=1;
		while(!game.getState().isFinished()){
			game.move();
			System.out.println(game.getPublicState());
			//System.out.println("player: "+game.getHistory().get(c).getPlayer()+"moves: "+game.getHistory().get(c).getPosition()+game.getHistory().get(c).getBoard());
			c++;
		}
		System.out.println(game.getWinningPieces());
		
		assertEquals("PLAYER_1_WON",game.getState().toString());
	}
	@Test
	public void testRandomBotAgainstDumbBot() throws IllegalMoveException {
		GomokuBot bot1 = new DumbBot("dumb");
		GomokuBot bot2 = new RandomBot("random");
		GomokuGame game = new GomokuGame("3",bot1,bot2);
		
		while(!game.getState().isFinished()){
			game.move();
		}
		assertEquals("PLAYER_1_WON",game.getState().toString());
			
	}
	@Test
	public void testGomokuGamePlay() throws IllegalMoveException{
		GomokuBot bot1 = new DumbBot("dumb1");
		GomokuBot bot2 = new DumbBot("dumb2");
		
		GomokuGame game = new GomokuGame("3",bot1,bot2);
		
		assertEquals("3",game.getId());
		assertEquals("dumb1",game.getPlayer1().getId());
		assertEquals("dumb2",game.getPlayer2().getId());
		//current player
		assertEquals("dumb1",game.getCurrentPlayer().getId());
		game.move();
		//player 2's turn
		assertEquals("dumb2",game.getCurrentPlayer().getId());
		assertEquals("WAITING_FOR_PLAYER_2",game.getState().toString());
		game.move();
		assertEquals("WAITING_FOR_PLAYER_1",game.getState().toString());
		//only two moves and one init board
		assertEquals(3,game.getHistory().size());
		//first move position[0,0]
		assertEquals(0,game.getHistory().get(1).getPosition().getX());
		assertEquals(0,game.getHistory().get(1).getPosition().getY());
		
		//first move position[1,0]
		assertEquals(1,game.getHistory().get(2).getPosition().getX());
		assertEquals(0,game.getHistory().get(2).getPosition().getY());

	}
	
}
