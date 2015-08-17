import java.util.List;
import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class GreedyBot extends PGBot {

	public GreedyBot(String id) {
		super(id);
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		return game.get(game.size() - 1);
	}

}
