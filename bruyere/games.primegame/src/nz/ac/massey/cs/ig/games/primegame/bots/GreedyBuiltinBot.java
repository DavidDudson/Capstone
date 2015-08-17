package nz.ac.massey.cs.ig.games.primegame.bots;

import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


/**
 * Built-in bot, plays the highest number available.
 * @author jens dietrich
 */
public class GreedyBuiltinBot extends PGBot {

    public GreedyBuiltinBot() {
        super(GreedyBuiltinBot.class.getName());
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		assert game.size()>0;
		return game.get(game.size()-1);
	}


}
