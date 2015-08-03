package soGaCoGameTemplate.bots;

import soGaCoGameTemplate.BattleshipBot;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Bot that picks the last square
 */
public class LastSquareBot extends BattleshipBot{

    private int moveCount = 100;

    public LastSquareBot(String botId) {
        super(botId);
    }

    @Override
    public Integer nextMove(List<Integer> integers) {
        moveCount--;
        return moveCount;
    }
}
