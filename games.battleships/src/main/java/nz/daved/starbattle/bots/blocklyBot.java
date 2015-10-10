package nz.daved.starbattle.bots;


import java.util.LinkedList;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>

public class blocklyBot extends StarBattleBot {

    public blocklyBot(String id) { super(id); }

    protected Boolean dfdfg = false;
    protected Boolean werwer = false;
    protected Boolean fgh = false;
    protected Object fgfh;


    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        dfdfg = botGameBoard.lastMoveSunkBot();
        werwer = botGameBoard.getLastMoveState(1);
        fgfh = 0;

        return botGameBoard.getFirstValidCoordinate();
    }
}