package soGaCoGameTemplate.game;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * The Gameboard itself
 */
public class GameBoard {

    private int patrolBoatCount;
    private int destroyerCount;
    private int battleshipCount;
    private int aircraftCarrierCount;

    private int boardWidth;
    private int boardHeight;

    private int remainingShips;

    public GameBoard(int _patrolBoatCount, int _destroyerCount, int _battleshipCount,
                     int _aircraftCarrier, int _boardWidth, int _boardHeight) {
        this.patrolBoatCount = _patrolBoatCount;
        this.destroyerCount = _destroyerCount;
        this.battleshipCount = _battleshipCount;
        this.aircraftCarrierCount = _aircraftCarrier;
        this.boardWidth = _boardWidth;
        this.boardHeight = _boardHeight;
        this.remainingShips = getTotalShips();
    }

    public GameBoard(GameBoard _gameboard) {
        this(_gameboard.patrolBoatCount,
                _gameboard.destroyerCount,
                _gameboard.battleshipCount,
                _gameboard.aircraftCarrierCount,
                _gameboard.boardWidth,
                _gameboard.boardHeight);
    }

    public GameBoard() {
        //Plug in default values from the popular hasbro Board game
        //10x10 grid with 2 battleships/submarines (3 long) and 1 of everything else
        this(1, 2, 1, 1, 10, 10);
    }

    public int getTotalShips() {
        return patrolBoatCount + destroyerCount + battleshipCount + aircraftCarrierCount;
    }

    public int getTotalGridSquares() {
        return boardHeight * boardWidth;
    }

    public int getRemainingShips() {
        return remainingShips;
    }


}
