package nz.ac.massey.cs.ig.games.primegame.bots;

import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


/**
 * Built-in bot, plays a random number.
 * @author jens dietrich
 */
public class CautiousBuiltinBot extends PGBot {

    public CautiousBuiltinBot() {
        super(CautiousBuiltinBot.class.getName());
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		assert game.size()>0;
		return game.get(0);
	}


}
