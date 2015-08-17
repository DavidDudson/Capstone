import java.util.List;

import java.util.List;
import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class AnxiousBot extends PGBot {

	public AnxiousBot(String id) {
		super(id);
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		return game.get(0);
	}

}
