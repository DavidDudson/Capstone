package nz.ac.massey.cs.ig.games.othello.bots;

import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloBot;
import nz.ac.massey.cs.ig.games.othello.OthelloPosition;

public class DumbBot extends OthelloBot {

	public DumbBot(String id) {
		super(id);
	}

	@Override
	public OthelloPosition nextMove(EasyOthelloBoard game) {
		return game.getAvailableMovesForMe().get(0);
	}

}
