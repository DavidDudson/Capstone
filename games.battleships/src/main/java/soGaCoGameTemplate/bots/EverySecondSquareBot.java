package soGaCoGameTemplate.bots;

import soGaCoGameTemplate.BattleshipBot;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Bot that picks every seconds Square
 */
public class EverySecondSquareBot extends BattleshipBot{

    private int moveCount = -1;
    private boolean goingDown = false;

    public EverySecondSquareBot() {
        super(EverySecondSquareBot.class.getName());
    }

    @Override
    public Integer nextMove(List<Integer> integers) {
        if(moveCount >= 99){
            goingDown = true;
            moveCount -= 1;
        }
        //
        moveCount = goingDown ? moveCount - 2 : moveCount + 2;

        return moveCount;
    }
}
