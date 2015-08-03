package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;
import soGaCoGameTemplate.gameLogic.GameBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * The actual battleship Game Instance
 */
public class BattleshipGame extends SimpleGame<List<Integer>,Integer>{

    public static final String MOVE_SEPARATOR = "-";

    private StringBuilder historyInURLToken = new StringBuilder();

    private GameBoard gameboard;

    private Set<Integer> board = new HashSet<Integer>();

    public BattleshipGame(String uid, Bot<List<Integer>, Integer> player1, Bot<List<Integer>, Integer> player2) {
        super(uid, player1, player2, true);
        GameBoard gameboard = new GameBoard();
    }

    @Override
    protected void doMove(Integer integer, Bot<List<Integer>, Integer> bot) {
    }

    @Override
    protected void registerMove(Integer v, Bot<List<Integer>, Integer> bot) {
        this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
        if (historyInURLToken.length() > 0)
            historyInURLToken.append(MOVE_SEPARATOR);
        historyInURLToken.append(v);
    }

    @Override
    protected void checkValidityOfMove(Integer v, Bot<List<Integer>, Integer> playerId) throws IllegalMoveException {
        if (v < 1) {
            throw new IllegalMoveException("The number played " + v
                    + " is not on the board, values must be 0 or greater");
        } else if (v > 100) {
            throw new IllegalMoveException("The number played " + v
                    + " is not on the board, values must be less than "
                    + 100);
        } else if (!board.contains(v)) {
            throw new IllegalMoveException("The number " + v + " played by "
                    + playerId.getId() + " has already been played");
        }
    }

    @Override
    protected void checkGameTermination() {

    }

    @Override
    public List<Integer> getPublicState() {
        return null;
    }
}
