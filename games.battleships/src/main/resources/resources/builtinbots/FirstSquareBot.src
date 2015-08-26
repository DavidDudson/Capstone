package nz.daved.starbattle.bots;

import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

/**
 * Created by aidan on 24/08/2015.
 */
public class FirstSquareBot  extends StarBattleBot {


    public FirstSquareBot(String id) {
        super(id);
    }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        return botGameBoard.getFirstValidCoordinate();
    }
}

