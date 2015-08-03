package soGaCoGameTemplate.bots;

import soGaCoGameTemplate.BattleshipBot;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Bot that picks the First Square
 */
public class FirstSquareBot extends BattleshipBot{

    private int moveCount = - 1;

    public FirstSquareBot(String botId) {
        super(botId);
    }

    @Override
    public Integer nextMove(List<Integer> integers) {
        moveCount++;
        return moveCount;
    }
}
