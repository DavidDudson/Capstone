package nz.ac.massey.cs.ig.games.gomoku.bots;

import nz.ac.massey.cs.ig.games.gomoku.EasyGomokuBoard;
import nz.ac.massey.cs.ig.games.gomoku.GomokuBot;
import nz.ac.massey.cs.ig.games.gomoku.GomokuPosition;

public class DumbBot extends GomokuBot{
	public DumbBot(String id) {
		super(id);
	}

	@Override
	public GomokuPosition nextMove(EasyGomokuBoard game) {
		return game.getAvailableMovesForMe().get(0);
	}

}