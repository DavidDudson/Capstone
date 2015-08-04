package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;
import soGaCoGameTemplate.game.GameBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The actual battleship Game Instance
 */
public class BattleshipGame extends SimpleGame<List<Integer>, Integer> {

    public static final String MOVE_SEPARATOR = "-";

    private StringBuilder historyInURLToken = new StringBuilder();

    private GameBoard gameboard;

    private Set<Integer> board = new HashSet<>();

    public BattleshipGame(String uid, Bot<List<Integer>, Integer> player1, Bot<List<Integer>, Integer> player2) {
        super(uid, player1, player2, true);
        this.gameboard = new GameBoard();
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
                    + " is not on the board, values must be 1 or greater");
        } else if (v > gameboard.getTotalGridSquares()) {
            throw new IllegalMoveException("The number played " + v
                    + " is not on the board, values must be less than "
                    + gameboard.getTotalGridSquares());
        } else if (!board.contains(v)) {
            throw new IllegalMoveException("The location " + v
                    + " has been shot at already");
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
