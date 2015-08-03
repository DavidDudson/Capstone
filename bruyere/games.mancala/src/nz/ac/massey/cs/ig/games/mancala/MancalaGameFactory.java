package nz.ac.massey.cs.ig.games.mancala;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameFactory;

/**
 * Factory for mancala games.
 * @author jens dietrich
 */
public class MancalaGameFactory implements GameFactory {

	@SuppressWarnings("unchecked")
	@Override
	public MancalaGame createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2) {
		return new MancalaGame(id,(Bot<Mancala,Integer>)player1,(Bot<Mancala,Integer>)player2);
	}

}
