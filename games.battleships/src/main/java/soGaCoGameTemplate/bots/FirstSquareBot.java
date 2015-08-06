package soGaCoGameTemplate.bots;

import soGaCoGameTemplate.BattleshipBot;
import soGaCoGameTemplate.game.BotMap;
import soGaCoGameTemplate.game.Coordinate;
import soGaCoGameTemplate.game.GameBoard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Bot that picks the First Square
 */
public class FirstSquareBot extends BattleshipBot{

    public FirstSquareBot() {
        super(FirstSquareBot.class.getName());
    }

    @Override
    public Coordinate nextMove(BotMap botMap) {
        Arrays.stream(botMap.getGrid()).flatMapToInt();
        return null;
    }
}
