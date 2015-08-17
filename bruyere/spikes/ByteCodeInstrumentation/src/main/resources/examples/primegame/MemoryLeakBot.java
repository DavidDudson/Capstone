import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


/**
* This is the starting point to implement your own bot.
* @author <your name here>
*/
public class MemoryLeakBot extends PGBot {

	public MemoryLeakBot (String botId) {
		super(botId);
	}

	/**
	 * Implement this method to select a number from the list.
	 * You can assume that the list is not empty,
	 * and that the list is in natural order.
	 */
	@Override
	public Integer nextMove(List<Integer> game) {
		String s = "s42";
		java.util.List<MemoryLeakBot> list = new java.util.ArrayList<>();
		while (s.length()>0) {
		    list.add(new MemoryLeakBot(s));
		}
		return game.get(0);
	}

}