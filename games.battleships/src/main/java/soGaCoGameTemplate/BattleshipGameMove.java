package soGaCoGameTemplate;

/**
 * Created by David J. Dudson on 6/08/15.
 * <p>
 * Contains a specific move for
 */
public class BattleshipGameMove {

    private String botId;
    private int row;
    private int col;
    private boolean wasShip;

    /**
     * Creates a move to be recorded in history
     *
     * @param _row     The row
     * @param _col     The column
     * @param _wasShip Whether or not the location contained a ship, aka was hit.
     */
    public BattleshipGameMove(String _botId, int _row, int _col, boolean _wasShip) {
        this.botId = _botId;
        this.row = _row;
        this.col = _col;
        this.wasShip = _wasShip;
    }

    public BattleshipGameMove(BattleshipGameMove move) {
        this(move.getBot(), move.getRow(), move.getCol(), move.isShip());
    }

    /**
     * Whether or not this move found a ship
     *
     * @return Whether or not this move found a ship
     */
    public boolean isShip() {
        return wasShip;
    }

    /**
     * Returns the row
     *
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column
     *
     * @return The column
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the bot who played the move
     *
     * @return The Bot
     */
    public String getBot() {
        return botId;
    }
}
