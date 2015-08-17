package nz.ac.massey.cs.ig.core.game;


/**
 * A player is an entity participating in a game.
 * It is either a human player, or a bot.
 * @author jens dietrich
 * @param <G> a representation of the game state
 * @param <M> a representation of a move
 */
public interface Player<G, M>{

	String getId();
    
    M nextMove(G game);

}
