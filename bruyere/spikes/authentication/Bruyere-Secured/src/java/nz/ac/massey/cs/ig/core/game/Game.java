
package nz.ac.massey.cs.ig.core.game;

import java.util.Properties;

/**
 * Abstraction of a game.
 * @author jens dietrich
 * @param <M> type to represent a move
 * @param <PS> data structure representing the public state of a game, e.g. a list or a 2D array
 */
public interface Game <M,PS>{
	
	/**
	 * Returns a data structure representing the public state of a game, e.g. a list or a 2D array.
	 * Bots / players use this data structure to infer the next move. 
	 * This can be the game itself, or an unmodifiable view of the game.
	 * @return
	 */
	PS getPublicState() ;
	
    /**
     * Return additional game meta data. 
     * @return 
     */
    Properties getMetaData () ;
    /**
     * Get a UID
     * @return 
     */
    String getId ();
    /**
     * Get player 1.
     * @return 
     */
    String getPlayer1 ();
    /**
     * Get player 2.
     * @return 
     */
    String getPlayer2 ();
    
    long getTimeWhenLastMoveWasMade();
    
    long getTimeWhenLastSuccessfulMoveWasMade();
    
    long getTimeWhenGameWasCreated();
    
    /**
     * Apply a move. 
     * The move should update the state of the game, including game state (getState()) and
     * the timestamps (getTimeWhen*())
     * @param move
     * @param player if player is player1 (player2), and state is != (WAITING_FOR_PLAYER_1 | NEW) (WAITING_FOR_PLAYER_2), an IllegalMoveException should be thrown
     * @param terminateOnError if true, an exception during a move will be caught internally, and 
     * the other player will automatically win. The exception can be retrieved with getError()
     * if false, exceptions will be throws
     * @throws IllegalMoveException 
     */
    void move (M move,String player,boolean terminateOnError) throws IllegalMoveException ; 
    
    /**
     * Return the game state.
     * @return 
     */
    GameState getState();
    
    /**
     * Get the exception that occurred. 
     * If a non-null value is returned and the state is PLAYER_1_WON, then the exception occurred in PLAYER2's move,
     * and vise versa
     * If an error is returned, the game state is either PLAYER_1_WON or PLAYER_2_WON.
     */
    Throwable getError();
}
