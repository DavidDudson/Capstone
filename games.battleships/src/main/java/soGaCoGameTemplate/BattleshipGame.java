package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;
import soGaCoGameTemplate.game.BotMap;
import soGaCoGameTemplate.game.Coordinate;
import soGaCoGameTemplate.game.GameBoard;
import soGaCoGameTemplate.game.ShipMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The actual battleship Game Instance
 */
public class BattleshipGame extends SimpleGame<GameBoard, Coordinate> {

    public static final String MOVE_SEPARATOR = "-";

    private StringBuilder historyInURLToken = new StringBuilder();

    private ShipMap shipMap;
    private BotMap player1Map;
    private BotMap player2Map;
    private List<BattleshipGameMove> history = new ArrayList<>(100);

    /**
     * Construct a new game bu generating the map for each bot and the shipMap
     *
     * @param uid     The uid of game
     * @param player1 The first bot
     * @param player2 The second bot
     */
    @SuppressWarnings("unchecked")
    public BattleshipGame(String uid, Bot<?, ?> player1, Bot<?, ?> player2) {
        super(uid, (Bot<GameBoard, Coordinate>) player1, (Bot<GameBoard, Coordinate>) player2);
        this.shipMap = new ShipMap();
        this.player1Map = new BotMap();
        this.player2Map = new BotMap();
    }

    /**
     * Do a single move
     *
     * @param coordinate The coordinate to "Attack"
     * @param bot        the bot whose move it is
     */
    @Override
    protected void doMove(Coordinate coordinate, Bot<GameBoard, Coordinate> bot) {
        GameBoard gameBoard = (bot == player1) ? player1Map : player2Map;
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        //Add 1 due to compensating for the fact that bot map has 3 states (Including unknown) and shipMap has 2
        int newValue = shipMap.getSquare(row, col) + 1;
        gameBoard.setSquareTo(row, col, newValue);
        boolean wasShip = newValue == 2;
        history.add(new BattleshipGameMove(bot.getId(), row, col, wasShip));
    }

    /**
     * Make sure that the move is registered to prevent timeout
     *
     * @param coordinate The coordinate placed
     * @param bot        The bot in question
     */
    @Override
    protected void registerMove(Coordinate coordinate, Bot<GameBoard, Coordinate> bot) {
        this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
        if (historyInURLToken.length() > 0)
            historyInURLToken.append(MOVE_SEPARATOR);
        historyInURLToken.append(coordinate.toString());
    }

    /**
     * Checks whether or not a move is valid
     *
     * @param coordinate The coordinate to attack
     * @param bot        the bot in question
     * @throws IllegalMoveException when the move would not be possible
     */
    @Override
    protected void checkValidityOfMove(Coordinate coordinate, Bot<GameBoard, Coordinate> bot) throws IllegalMoveException {
    }

    /**
     * Checks whether or not the game has terminated
     */
    @Override
    protected void checkGameTermination() {

    }

    /**
     * Returns the game history
     *
     * @return A copy of the history
     */
    public List<BattleshipGameMove> getHistory() {
        return history.stream().map(BattleshipGameMove::new).collect(Collectors.toList());
    }

    /**
     * Returns the state avaliable to the public, in battleships there is no public state
     *
     * @return Null due to there being no public gamestate
     */
    @Override
    public GameBoard getPublicState() {
        return null;
    }
}
