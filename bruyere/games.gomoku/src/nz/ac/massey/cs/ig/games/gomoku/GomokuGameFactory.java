package nz.ac.massey.cs.ig.games.gomoku;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameFactory;


public class GomokuGameFactory implements GameFactory{

	@SuppressWarnings("unchecked")
	@Override
	public GomokuGame createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2) {
		return new GomokuGame(id,(Bot<GomokuBoard,GomokuPosition>)player1,(Bot<GomokuBoard,GomokuPosition>)player2);
	}

}
