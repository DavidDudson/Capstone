package nz.daved.starbattles.bots;

import nz.daved.starbattles.StarBattleBot;
import nz.daved.starbattles.game.BotGameBoard;
import nz.daved.starbattles.game.Coordinate;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Bot that picks the First Square
 */
public class FirstSquareBot extends StarBattleBot {

    public FirstSquareBot() {
        super(FirstSquareBot.class.getName());
    }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        return botGameBoard.getNextValidCoordinate();
    }
}
