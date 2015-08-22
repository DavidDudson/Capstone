package nz.daved.starbattle.bots;

import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Bot that picks the First Square
 */
public class FirstSquareBot extends StarBattleBot {

    public FirstSquareBot(String id) {
        super(id);
    }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        return botGameBoard.getFirstValidCoordinate();
    }
}
