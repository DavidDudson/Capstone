package nz.daved.starbattle.bots;


import java.util.LinkedList;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><statement name="BLOCK"><block type="controls_if"><value name="IF0"><block type="last_move_state"><field name="STATE">1</field></block></value></block></statement></block></xml>

public class CustomStarBattleBot extends StarBattleBot {

    public CustomStarBattleBot(String id) { super(id); }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        if (botGameBoard.getLastMoveState(1)) {
            {}
        }

        return ;
    }
}
