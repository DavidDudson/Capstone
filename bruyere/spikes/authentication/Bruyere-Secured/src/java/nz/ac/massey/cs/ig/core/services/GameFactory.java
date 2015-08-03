package nz.ac.massey.cs.ig.core.services;

import java.util.List;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;

/**
 * Factory for games.
 * @author jens dietrich
 */
public interface GameFactory<G extends Game<M,PS>, B extends Bot<?,?>, M, PS> {
    G createGame(String id,String player1, String player2,List<String> params);
}
