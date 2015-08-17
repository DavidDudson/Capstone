
package nz.ac.massey.cs.ig.tasks;

import java.util.concurrent.Callable;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;

/**
 * Bot against bot game. 
 * Can be executed in a sandbox using futures (timeouts, catch stack overflows).
 * @author jens dietrich
 * @param G the game class
 */
public class BotAgainstBotPlay<G extends Game<M,PS>, M, PS, B extends Bot<PS,M>> implements Callable<G> {

    private G game = null;
    private B player1 = null;
    private B player2 = null;

    public BotAgainstBotPlay(G game,B player1,B player2) {
        super();
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    @Override
    public G call() throws Exception {
        
        assert game.getState()==GameState.NEW;
        
        while (!game.getState().isFinished()) {
            if (game.getState()==GameState.NEW || game.getState()==GameState.WAITING_FOR_PLAYER_1) {
                M move = player1.nextMove(game.getPublicState());
                game.move(move, player1.getId(),true);
            }
            else if (game.getState()==GameState.WAITING_FOR_PLAYER_2) {
                M move = player2.nextMove(game.getPublicState());
                game.move(move, player2.getId(),true);
            }
        }    
        assert game.getState().isFinished();          
        return game;
    }
    
}
