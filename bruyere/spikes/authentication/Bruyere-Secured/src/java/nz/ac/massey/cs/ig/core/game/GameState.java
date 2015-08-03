
package nz.ac.massey.cs.ig.core.game;

/**
 * Encodes game states.
 * @author jens dietrich
 */
public enum GameState {
    
    NEW (false), 
    WAITING_FOR_PLAYER_1 (false),
    WAITING_FOR_PLAYER_2 (false),
    PLAYER_1_WON (true),
    PLAYER_2_WON (true), 
    TIE (true);
    
    private final boolean finished;
    
    GameState (boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {return finished;}
    
}
