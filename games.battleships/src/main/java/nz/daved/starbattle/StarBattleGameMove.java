package nz.daved.starbattle;

import nz.daved.starbattle.game.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 6/08/15.
 * <p>
 * Contains a specific move for
 */
public class StarBattleGameMove {

    private final List<Coordinate> sunk;
    private String botId;
    private boolean wasPlayer1;

    private Coordinate coord;
    private boolean wasShip;

    /**
     * Creates a move to be recorded in history
     *
     * @param _coord The Coordinate attacked
     * @param _wasShip Whether or not the location contained a ship, aka was hit.
     */
    public StarBattleGameMove(boolean _wasPlayer1,String _botId, Coordinate _coord, boolean _wasShip, List<Coordinate> _sunk) {
        this.botId = _botId;
        this.coord = _coord;
        this.wasShip = _wasShip;
        this.sunk = _sunk;
        this.wasPlayer1 = _wasPlayer1;
    }

    public StarBattleGameMove(StarBattleGameMove move) {
        this(move.isPlayer1(),move.getBot(), move.coord, move.isShip(), move.getSunk());
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

    @Override
    public String toString() {
        return "StarBattleGameMove{" +
                "sunk=" + sunk +
                ", botId='" + botId + '\'' +
                ", wasPlayer1=" + wasPlayer1 +
                ", coord=" + coord +
                ", wasShip=" + wasShip +
                '}';
    }

    public List<Coordinate> getSunk() {
        return sunk.stream()
                .map(Coordinate::new)
                .collect(Collectors.toList());
    }

    public boolean isPlayer1() {
        return wasPlayer1;
    }
}
