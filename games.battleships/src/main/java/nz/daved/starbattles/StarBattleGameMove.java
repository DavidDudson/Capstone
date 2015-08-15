package nz.daved.starbattles;

import nz.daved.starbattles.game.Coordinate;

/**
 * Created by David J. Dudson on 6/08/15.
 * <p>
 * Contains a specific move for
 */
public class StarBattleGameMove {

    private String botId;


    private Coordinate coord;
    private boolean wasShip;

    /**
     * Creates a move to be recorded in history
     *
     * @param _coord The Coordinate attacked
     * @param _wasShip Whether or not the location contained a ship, aka was hit.
     */
    public StarBattleGameMove(String _botId, Coordinate _coord, boolean _wasShip) {
        this.botId = _botId;
        this.coord = _coord;
        this.wasShip = _wasShip;
    }

    public StarBattleGameMove(StarBattleGameMove move) {
        this(move.getBot(), move.coord, move.isShip());
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
     * Return the coordinate
     * @return The Coordinate
     */
    public Coordinate getCoord() {
        return coord;
    }

    /**
     * Gets the bot who played the move
     *
     * @return The Bot
     */
    public String getBot() {
        return botId;
    }

    /**
     * Override to string
     * @return a string representation of the Move
     */
    @Override
    public String toString() {
        return "StarBattleGameMove{" +
                "botId='" + botId + '\'' +
                ", coord=" + coord +
                ", wasShip=" + wasShip +
                '}';
    }

}
