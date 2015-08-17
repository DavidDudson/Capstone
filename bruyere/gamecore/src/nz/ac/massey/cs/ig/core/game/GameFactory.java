package nz.ac.massey.cs.ig.core.game;


/**
 * Factory for games.
 * @author jens dietrich
 */
public interface GameFactory {
    
	Game<?, ?> createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2);

}
