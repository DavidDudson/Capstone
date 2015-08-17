package nz.ac.massey.cs.ig.games.primegame;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;

import org.junit.Test;

/**
 * (Runtime) tests used to verify a bot.
 * Note that this test case has a special constructor used to inject the bot tested, 
 * this is required by the test runner we use for dynamic verification.
 * @author jens dietrich
 */
public class JavaPrimeGameBotTests {
	
	private Bot<List<Integer>, Integer> bot = null;
	
	
	public JavaPrimeGameBotTests(Bot<List<Integer>, Integer> bot) {
		super();
		this.bot = bot;
	}


	@Test(timeout=10000)
	public void test1 () throws Exception {
		int move = bot.nextMove(Arrays.asList(new Integer[] {new Integer(1), new Integer(7) }));
		assertTrue("Invalid move: bot generated a turn that is not in the list of inputs",move==1 || move==7);
	}
}
