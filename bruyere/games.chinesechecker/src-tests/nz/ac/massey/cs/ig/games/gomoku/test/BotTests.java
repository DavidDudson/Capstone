package nz.ac.massey.cs.ig.games.gomoku.test;

import static org.junit.Assert.*;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerBot;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerGame;
import nz.ac.massey.cs.ig.games.chinesechecker.bots.DumbBot;
import nz.ac.massey.cs.ig.games.chinesechecker.bots.RandomBot;

import org.junit.Ignore;
import org.junit.Test;

public class BotTests {

	@Test
	public void testDumbBotsCanCompleteAGame() throws IllegalMoveException {
		ChineseCheckerBot bot1 = new DumbBot("dumb1");
		ChineseCheckerBot bot2 = new DumbBot("dumb2");
		
		ChineseCheckerGame game = new ChineseCheckerGame("3",bot1,bot2);
		System.out.println("player: "+game.getHistory().get(0).getPlayer()+game.getHistory().get(0).getBoard());
		
		System.out.println(game.move());
		System.out.println("player: "+game.getHistory().get(1).getPlayer()+game.getHistory().get(1).getBoard());
		System.out.println(game.move());
		System.out.println("player: "+game.getHistory().get(2).getPlayer()+game.getHistory().get(2).getBoard());

		
		//assertEquals("PLAYER_1_WON",game.getState().toString());
	}


}
