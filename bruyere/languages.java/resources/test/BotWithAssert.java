package test;

import java.util.List;
import nz.ac.massey.cs.ig.games.primegame.*;

/**
 * Built-in bot, plays the highest number available.
 * @author jens dietrich
 */
public class BotWithAssert extends PGBot {

    public BotWithAssert(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		List<Integer> numbers = game;
		assert numbers.size()>0;
		
		return numbers.get(numbers.size()-1);
	}


}