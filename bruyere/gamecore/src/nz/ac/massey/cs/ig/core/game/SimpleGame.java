
package nz.ac.massey.cs.ig.core.game;

import java.util.Properties;

/**
 * Skeleton implementation of Game.
 * @author jens dietrich
 * @param <M>
 */
public abstract class SimpleGame<PS, M> extends AbstractGame<PS, M> {
    
    protected final long timeWhenGameWasCreated = System.currentTimeMillis();
    protected long timeWhenLastSuccessfulMoveWasMade = -1;
    protected long timeWhenLastMoveWasMade = -1;
    protected Throwable error = null;
    protected Properties properties = new Properties();
    protected boolean terminateOnError;
    
    public SimpleGame(String uid, Bot<PS, M> player1, Bot<PS, M> player2) {
        super(uid, player1, player2);
        terminateOnError = true;
    }
    
    public SimpleGame(String uid, Bot<PS, M> player1, Bot<PS, M> player2, boolean terminateOnError) {
        this(uid, player1, player2);
        this.terminateOnError = terminateOnError;
    }
    
    public Properties getMetaData () {
        return properties;
    }
    
    @Override
    public GameState getState() {
        return this.state;
    }
    
    @Override
    public Throwable getError() {
        return this.error;
    }
    
    
    @Override
    public synchronized GameState move() throws IllegalMoveException {
        
        this.timeWhenLastMoveWasMade = System.currentTimeMillis();
        
        Bot<PS, M> currentPlayer = getCurrentPlayer();
        
        // note: we need to set the context here first !! 
        prepareMove(currentPlayer);
        
        
        // nextMove can throw runtime exceptions and errors (in particular stack overflows) !
        M move = null;
        if (this.terminateOnError) {
        	try {
        		move = currentPlayer.nextMove(getPublicState());
        	}
        	catch (Throwable x) {
                this.error = x;
                if (this.player1==currentPlayer) {
                    this.state = GameState.PLAYER_2_WON;
                }
                else {
                    this.state = GameState.PLAYER_1_WON;
                }
        	}
        }
        else {
        	move = currentPlayer.nextMove(getPublicState());
        }
        
        if (!this.state.isFinished()) {
	        checkPlayer(currentPlayer);
	        checkState(currentPlayer);
	        
	        // check whether the actual move is valid
	        if (this.terminateOnError) {
	            try {
	                checkValidityOfMove(move, currentPlayer);
	            }
	            catch (IllegalMoveException x) {
	                this.error = x;
	                if (this.player1.equals(currentPlayer)) {
	                    this.state = GameState.PLAYER_2_WON;
	                }
	                else {
	                    this.state = GameState.PLAYER_1_WON;
	                }
	            }
	        }
	        else {
	            checkValidityOfMove(move, currentPlayer);
	        }
	        
	        // make the actual move
	        if (!this.getState().isFinished()) {
	        	doMove(move, currentPlayer);
	        }
	        
	        // check whether we can set the status to win or tie
	        // unless terminateOnError has already set such a state
	        if (!this.getState().isFinished()) {
	            checkGameTermination();
	        }
        }
	        
        // game successful, write history
        this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
        
        registerMove(move, currentPlayer);
        
        return getState();
    }
    
    /**
     * Prepare the game for the next move made by a player. 
     * This can be used to set the game context before the board is passed to the board that 
     * is the input of the bots move method. 
     * Sometimes the board needs this context, for instance of players operate in different territories of the board.
     * The bots might know them as "my part" and "other players part" - but this notion makes only sense if the board
     * knows whos turn it is next. This can be set with this method.
     * @param currentPlayer
     */
    protected void prepareMove(Bot<PS, M> player) {
		// by default, do nothing here
	}

	/**
     * Make the actual move.
     * All consistency checks have been done somewhere else
     * @param move
     * @param playerId
     */
    protected abstract void doMove(M move, Bot<PS, M> bot);
    
    /**
     * Register a move, e.g. to maintain the game history. 
     * @param move
     * @param playerId 
     */
    protected abstract void registerMove(M move, Bot<PS, M> bot);

    /**
     * Check whether its the players turn. 
     * @param playerId
     * @throws IllegalMoveException thrown if this is not this players turn
     */
    protected void checkPlayer(Bot<PS, M> playerId) throws IllegalMoveException  {
        // check whether this is the right player !!
        if (!playerId.equals(player2) && !playerId.equals(player1)) {
            throw new IllegalMoveException("Illegal player: expected " + this.player1 + " or " + this.player2 + ", but found " + playerId);
        }
        
        // check player
        if (state==GameState.NEW && !playerId.equals(player1)) {
            throw new IllegalMoveException("Player 1 must start the game: " + this.player1 + " expected, but " + playerId + " found");
        } 
        if (state==GameState.WAITING_FOR_PLAYER_1 && !playerId.equals(player1)) {
            throw new IllegalMoveException("Player 1 's turn: " + this.player1 + " expected, but " + playerId + " found");
        }
        if (state==GameState.WAITING_FOR_PLAYER_2 && !playerId.equals(player2)) {
            throw new IllegalMoveException("Player 2 's turn: " + this.player2 + " expected, but " + playerId + " found");
        }

    }
    
    /**
     * Check whether a move can be made.
     * @param playerId
     * @throws IllegalMoveException thrown if the game is already closed (getState().isFinished())
     */
    protected void checkState(Bot<PS, M> playerId) throws IllegalMoveException  {

        // check whether game has already finished
        if (state.isFinished()) {
            throw new IllegalMoveException("Game " + this.uid + " has already finished - cannot accept move by player " + playerId.getId());
        }
    }
    
    /**
     * Check whether the actual move is allowed. 
     * E.g., whether the coordinates on a board are permitted.
     * @param move
     * @param playerId
     * @throws IllegalMoveException thrown if this was an illegal move
     */
    protected abstract void checkValidityOfMove(M move, Bot<PS, M> playerId) throws IllegalMoveException ;
    
    /**
     * Check whether the game can be terminated, and set the respective GameState if this is the case.
     * if necessary. 
     */
    protected abstract void checkGameTermination();
    
	@Override
	public Bot<PS, M> getCurrentPlayer() {
		if(getState() == GameState.WAITING_FOR_PLAYER_1 || getState() == GameState.NEW) {
			return player1;
		} else {
			return player2;
		}
	}

	public boolean isTerminatingOnError() {
		return terminateOnError;
	}

	public void setTerminatingOnError(boolean terminateOnError) {
		this.terminateOnError = terminateOnError;
	}
}
