package nz.daved.starbattle;

import com.google.common.annotations.VisibleForTesting;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;
import nz.daved.starbattle.game.ShipGameBoard;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The actual battleship Game Instance
 */
public class StarBattleGame extends SimpleGame<BotGameBoard, Coordinate> {

    private ShipGameBoard shipGameBoard;
    private BotGameBoard bot1map;
    private BotGameBoard bot2map;
    private List<StarBattleGameMove> history = new LinkedList<>();
    private static final int BOT1 = 1;
    private static final int BOT2 = 2;

    /**
     * Construct a new game bu generating the map for each bot and the shipMap
     *
     * @param uid     The uid of game
     * @param player1 The first bot
     * @param player2 The second bot
     */
    @SuppressWarnings("unchecked")
    public StarBattleGame(String uid, Bot<?, ?> player1, Bot<?, ?> player2) {
        super(uid, (StarBattleBot) player1, (StarBattleBot) player2);
        this.shipGameBoard = new ShipGameBoard();
        this.bot1map = shipGameBoard.generateBotMap(BOT1,history);
        this.bot2map = shipGameBoard.generateBotMap(BOT2,history);
    }

    @VisibleForTesting
    public void testDoMove(Coordinate coord, Bot<BotGameBoard,Coordinate> bot){
        doMove(coord,bot);
    }

    /**
     * Do a single move
     *
     * @param coord The coordinate to "Attack"
     * @param bot   the bot whose move it is
     */
    @Override
    protected void doMove(Coordinate coord, Bot<BotGameBoard,Coordinate> bot) {
        boolean isPlayer1 = (bot == player1);
        BotGameBoard botMap = isPlayer1 ? bot1map : bot2map;
        //Add 1 due to compensating for the fact that bot map has 3 states (Including unknown) and shipMap has 2
        boolean wasShip = shipGameBoard.getStateOfCoordinate(coord) == 1;
        List<Coordinate> coordList  = botMap.attackCoordinate(coord,wasShip);
        history.add(new StarBattleGameMove(isPlayer1,bot.getId(), coord, wasShip,coordList));
        updateGameState(coord, (StarBattleBot) bot);
    }

    public void runTestGame(){
        boolean completed = false;
        Coordinate move = new Coordinate(0,0);
        StarBattleBot player = (StarBattleBot)player1;
        System.out.println(this.bot1map.getShipSizes());
        while(!completed){
            if(state == GameState.NEW){
                state = GameState.WAITING_FOR_PLAYER_1;
            } else if(state == GameState.WAITING_FOR_PLAYER_1){
                move = player1.nextMove(bot1map);
                player = (StarBattleBot) player1;
            } else if(state == GameState.WAITING_FOR_PLAYER_2){
                move = player2.nextMove(bot2map);
                player = (StarBattleBot) player2;
            } else {
                completed = true;
            }
            doMove(move, player);
            checkGameTermination();
        }
    }

    /**
     * Updates the game state to tell the game whose turn is next
     *
     * @param coord The Coordinate
     * @param bot   The bot whose move was last
     */
    private void updateGameState(Coordinate coord, StarBattleBot bot) {
        if (bot == player1 && shipGameBoard.isShip(coord)) {
            this.state = GameState.WAITING_FOR_PLAYER_1;
        } else if (bot == player2 && shipGameBoard.isShip(coord)) {
            this.state = GameState.WAITING_FOR_PLAYER_2;
        } else if (bot == player1) {
            this.state = GameState.WAITING_FOR_PLAYER_2;
        } else {
            this.state = GameState.WAITING_FOR_PLAYER_1;
        }
    }


    /**
     * Make sure that the move is registered to prevent timeout
     *
     * @param coordinate The coordinate placed
     * @param bot        The bot in question
     */
    @Override
    protected void registerMove(Coordinate coordinate, Bot<BotGameBoard, Coordinate> bot) {
    }

    /**
     * Checks whether or not a move is valid
     *
     * @param coordinate The coordinate to attack
     * @param bot        the bot in question
     * @throws IllegalMoveException when the move would not be possible
     */
    @Override
    protected void checkValidityOfMove(Coordinate coordinate, Bot<BotGameBoard, Coordinate> bot) throws IllegalMoveException {
        BotGameBoard botmap = (bot == player1) ? bot1map : bot2map;
        if (botmap.getStateOfCoordinate(coordinate) != 0) {
            throw new IllegalMoveException("Coordinate not Water: " + coordinate.toString());
        }
    }

    /**
     * Checks whether or not the game has terminated
     */
    @Override
    protected void checkGameTermination() {
        if(bot1map.getRemainingShips() == 0){
            this.state = GameState.PLAYER_1_WON;
        } else if (bot2map.getRemainingShips() == 0){
            this.state = GameState.PLAYER_2_WON;
        }
    }

    /**
     * Returns the game history
     *
     * @return A copy of the history
     */
    public List<StarBattleGameMove>getHistory() {
        return history.stream().map(StarBattleGameMove::new).collect(Collectors.toList());
    }

    /**
     * Returns the state avaliable to the public, in battleships there is no public state
     *
     * @return Null due to there being no public gamestate
     */

    @Override
    public BotGameBoard getPublicState() {
        return state == GameState.WAITING_FOR_PLAYER_1 ? bot1map : bot2map;
    }
}
