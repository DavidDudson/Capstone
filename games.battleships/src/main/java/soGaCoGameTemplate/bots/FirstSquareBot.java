package soGaCoGameTemplate.bots;

import soGaCoGameTemplate.BattleshipBot;
import soGaCoGameTemplate.game.BotMap;
import soGaCoGameTemplate.game.Coordinate;

import java.util.Arrays;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Bot that picks the First Square
 */
public class FirstSquareBot extends BattleshipBot {

    public FirstSquareBot() {
        super(FirstSquareBot.class.getName());
    }

    @Override
    public Coordinate nextMove(BotMap botMap) {
        Arrays.stream(botMap.getGrid()).flatMapToInt();
        return null;
    }
}
