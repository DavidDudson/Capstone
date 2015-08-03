package soGaCoGameTemplate.bots;

import nz.ac.massey.cs.ig.core.game.Bot;
import soGaCoGameTemplate.BattleshipBot;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Randomly hitting bot
 */
public class RandomBot extends BattleshipBot{

    public RandomBot(String botId) {
        super(botId);
    }

    @Override
    public Integer nextMove(List<Integer> integers) {
        return null;
    }
}
