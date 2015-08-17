package nz.ac.massey.cs.ig.games.primegame;

import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameFactory;

/**
 * Factory for prime games.
 * @author jens dietrich
 */
public class PGGameFactory implements GameFactory {

	@SuppressWarnings("unchecked")
	@Override
	public PGGame createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2) {
		return new PGGame(id, (Bot<List<Integer>, Integer>)player1, (Bot<List<Integer>, Integer>)player2);
	}

}
