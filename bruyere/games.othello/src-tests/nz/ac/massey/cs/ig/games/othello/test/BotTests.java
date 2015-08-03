package nz.ac.massey.cs.ig.games.othello.test;

import static org.junit.Assert.assertTrue;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.othello.OthelloBot;
import nz.ac.massey.cs.ig.games.othello.OthelloGame;
import nz.ac.massey.cs.ig.games.othello.bots.DumbBot;
import nz.ac.massey.cs.ig.games.othello.bots.SmartBot;
import nz.ac.massey.cs.ig.games.othello.bots.SmarterBot;

import org.junit.Test;

public class BotTests {

	@Test
	public void testDumbBotsCanCompleteAGame() throws IllegalMoveException {
		OthelloBot bot1 = new DumbBot("1");
		OthelloBot bot2 = new DumbBot("2");
		
		OthelloGame game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
	}
	
	@Test
	public void testSmartBotWinsAgainstDumbBot() throws IllegalMoveException {
		OthelloBot bot1 = new SmartBot("1");
		OthelloBot bot2 = new DumbBot("2");
		
		OthelloGame game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
		assertTrue("SmartBot should win against DumbBot", game.getState() == GameState.PLAYER_1_WON);
		
		bot1 = new DumbBot("1");
		bot2 = new SmartBot("2");
		
		game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
		assertTrue("SmartBot should win against DumbBot", game.getState() == GameState.PLAYER_2_WON);
	}
	
	@Test
	public void testSmarterBotWinsAgainstDumbBot() throws IllegalMoveException {
		OthelloBot bot1 = new SmarterBot("1");
		OthelloBot bot2 = new DumbBot("2");
		
		OthelloGame game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			System.out.println(game.getPublicState());
			game.move();
		}
		System.out.println(game.getPublicState());		
		assertTrue("SmarterBot should win against DumbBot", game.getState() == GameState.PLAYER_1_WON);
		
		bot1 = new DumbBot("1");
		bot2 = new SmarterBot("2");
		
		game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
		assertTrue("SmarterBot should win against DumbBot", game.getState() == GameState.PLAYER_2_WON);
	}
	
	@Test
	public void testSmarterBotWinsAgainstSmartBot() throws IllegalMoveException {
		OthelloBot bot1 = new SmarterBot("1");
		OthelloBot bot2 = new SmartBot("2");
		
		OthelloGame game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
		
		assertTrue("SmarterBot should win against SmartBot", game.getState() == GameState.PLAYER_1_WON);
		
		bot1 = new SmartBot("1");
		bot2 = new SmarterBot("2");
		
		game = new OthelloGame("3", bot1, bot2);
		
		while(!game.getState().isFinished()) {
			game.move();
		}
		assertTrue("SmarterBot should win against SmartBot", game.getState() == GameState.PLAYER_2_WON);
	}
	
}
