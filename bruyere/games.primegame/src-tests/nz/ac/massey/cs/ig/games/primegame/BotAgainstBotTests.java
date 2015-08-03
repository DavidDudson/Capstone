package nz.ac.massey.cs.ig.games.primegame;

import static org.junit.Assert.assertEquals;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.games.primegame.bots.CautiousBuiltinBot;
import nz.ac.massey.cs.ig.games.primegame.bots.GreedyBuiltinBot;
import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;

import org.junit.Test;

public class BotAgainstBotTests {

	@Test
	public void testGreedyAgainstCautious10() throws Exception {
		PGGame game = new PGGame("game1",new GreedyBuiltinBot(),new CautiousBuiltinBot());
		game.setBoardSize(10);
		
		BotAgainstBotPlay play = new BotAgainstBotPlay(game);
		play.call();
		
		assertEquals(GameState.PLAYER_1_WON,game.getState());
		// manually played game to get end scores
		assertEquals(34,game.getPlayer1score());
		assertEquals(21,game.getPlayer2score());
	}
	
	@Test
	public void testGreedyAgainstCautious20() throws Exception {
		PGGame game = new PGGame("game1",new GreedyBuiltinBot(),new CautiousBuiltinBot());
		game.setBoardSize(20);
		
		BotAgainstBotPlay play = new BotAgainstBotPlay(game);
		play.call();
		
		assertEquals(GameState.PLAYER_1_WON,game.getState());
		// manually played game to get end scores
		assertEquals(119,game.getPlayer1score());
		assertEquals(91,game.getPlayer2score());
	}
	
	@Test
	public void testCautiousAgainstGreedy20() throws Exception {
		PGGame game = new PGGame("game1",new CautiousBuiltinBot(),new GreedyBuiltinBot());
		game.setBoardSize(20);
		
		BotAgainstBotPlay play = new BotAgainstBotPlay(game);
		play.call();
		
		assertEquals(GameState.PLAYER_2_WON,game.getState());
		// manually played game to get end scores
		assertEquals(91,game.getPlayer1score());
		assertEquals(119,game.getPlayer2score());
	}

}
