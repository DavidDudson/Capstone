import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


public class GreedyBuiltinBot extends PGBot {
	
    public GreedyBuiltinBot(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		Integer index = new Integer(game.size() - 1);
		
		return game.get(index);
	}


}
