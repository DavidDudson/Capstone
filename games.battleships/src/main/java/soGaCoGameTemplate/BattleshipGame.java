package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * The actual battleship Game Instance
 */
public class BattleshipGame extends SimpleGame<List<Integer>,Integer>{

    public BattleshipGame(String uid, Bot<List<Integer>, Integer> player1, Bot<List<Integer>, Integer> player2, boolean terminateOnError) {
        super(uid, player1, player2, true);
    }

    @Override
    protected void doMove(Integer integer, Bot<List<Integer>, Integer> bot) {

    }

    @Override
    protected void registerMove(Integer integer, Bot<List<Integer>, Integer> bot) {

    }

    @Override
    protected void checkValidityOfMove(Integer integer, Bot<List<Integer>, Integer> bot) throws IllegalMoveException {

    }

    @Override
    protected void checkGameTermination() {

    }

    @Override
    public List<Integer> getPublicState() {
        return null;
    }
}
