
package nz.ac.massey.cs.ig.core.game;

import java.util.Properties;

/**
 * Skeleton implementation of Game.
 * @author jens dietrich
 * @param <M>
 */
public abstract class AbstractGame <M,PS> implements Game <M,PS>{
    
    protected String uid = null;
    protected String player1 = null;
    protected String player2 = null;
    protected final long timeWhenGameWasCreated = System.currentTimeMillis();
    protected long timeWhenLastSuccessfulMoveWasMade = -1;
    protected long timeWhenLastMoveWasMade = -1;
    protected GameState state = GameState.NEW;
    protected Throwable error = null;
    protected Properties properties = new Properties();
    
    public AbstractGame(String uid,String player1, String player2) {
        super();
        this.uid = uid;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    @Override
    public synchronized String getId() {
        return uid;
    }

    @Override
    public synchronized String getPlayer1() {
        return this.player1;
    }

    @Override
    public synchronized String getPlayer2() {
        return this.player2;
    }
    
    @Override
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
    public synchronized void move(M move, String playerId,boolean terminateOnError) throws IllegalMoveException {
        
        this.timeWhenLastMoveWasMade = System.currentTimeMillis();
        
        checkPlayer(playerId);
        checkState(playerId);
        
        // check whether the actual move is valid
        if (terminateOnError) {
            try {
                checkValidityOfMove(move,playerId);
            }
            catch (IllegalMoveException x) {
                this.error = x;
                if (this.player1.equals(playerId)) {
                    this.state = GameState.PLAYER_2_WON;
                }
                else {
                    this.state = GameState.PLAYER_1_WON;
                }
            }
        }
        else {
            checkValidityOfMove(move,playerId);
        }
        
        // make the actual move
        doMove(move,playerId);
        
        // check whether we can set the status to win or tie
        // unless terminateOnError has already set such a state
        if (!this.getState().isFinished()) {
            checkGameTermination();
        }
        
        // game succesful, write history
        this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
        
        registerMove(move,playerId);
    }
    
    /**
     * Make the actual move.
     * All consistency checks have been done somewhere else
     * @param move
     * @param playerId
     */
    protected abstract void doMove(M move,String playerId);
    
    /**
     * Register a move, e.g. to maintain the game history. 
     * @param move
     * @param playerId 
     */
    protected abstract void registerMove(M move,String playerId);

    /**
     * Check whether its the players turn. 
     * @param playerId
     * @throws IllegalMoveException thrown if this is not this players turn
     */
    protected void checkPlayer(String playerId) throws IllegalMoveException  {
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
    protected void checkState(String playerId) throws IllegalMoveException  {

        // check whether game has already finished
        if (state.isFinished()) {
            throw new IllegalMoveException("Game " + this.uid + " has already finished - cannot accept move by player " + playerId);
        }
    }
    
    /**
     * Check whether the actual move is allowed. 
     * E.g., whether the coordinates on a board are permitted.
     * @param move
     * @param playerId
     * @throws IllegalMoveException thrown if this was an illegal move
     */
    protected abstract void checkValidityOfMove(M move, String playerId) throws IllegalMoveException ;
    
    /**
     * Check whether the game can be terminated, and set the respective GameState if this is the case.
     * if necessary. 
     */
    protected abstract void checkGameTermination();

	@Override
	public long getTimeWhenLastMoveWasMade() {
	    return this.timeWhenLastMoveWasMade;
	}

	@Override
	public synchronized long getTimeWhenLastSuccessfulMoveWasMade() {
	    return this.timeWhenLastSuccessfulMoveWasMade;
	}

	@Override
	public synchronized long getTimeWhenGameWasCreated() {
	    return this.timeWhenGameWasCreated;
	}
    

}
