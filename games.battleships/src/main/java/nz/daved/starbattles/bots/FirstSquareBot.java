package nz.daved.starbattles.bots;

import nz.daved.starbattles.StarBattlesBot;
import nz.daved.starbattles.game.BotGameBoard;
import nz.daved.starbattles.game.Coordinate;

import java.util.Arrays;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Bot that picks the First Square
 */
public class FirstSquareBot extends StarBattlesBot {

    public FirstSquareBot() {
        super(FirstSquareBot.class.getName());
    }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        Arrays.stream(botGameBoard.getGrid())
                .flatMapToInt(Arrays::stream)
                        .filter(y -> y == 0).findFirst();
        return null;
    }
}
