
package nz.ac.massey.cs.ig.core.game;


/**
 * Skeleton implementation of Game.
 * @author jens dietrich
 * @param <M>
 */
public abstract class AbstractGame<PS, M> implements Game <PS, M>{
    
    protected String uid = null;
    protected Bot<PS, M> player1 = null;
    protected Bot<PS, M> player2 = null;
    
    protected GameState state = GameState.NEW;
    protected Throwable error = null;
    
    public AbstractGame(String uid,Bot<PS, M> player1, Bot<PS, M> player2) {
        super();
        this.uid = uid;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    @Override
    public String getId() {
        return uid;
    }

    @Override
    public Bot<PS, M> getPlayer1() {
        return this.player1;
    }

    @Override
    public Bot<PS, M> getPlayer2() {
        return this.player2;
    }
    
    @Override
    public GameState getState() {
        return this.state;
    }
    
    @Override
    public Throwable getError() {
        return this.error;
    }
}
