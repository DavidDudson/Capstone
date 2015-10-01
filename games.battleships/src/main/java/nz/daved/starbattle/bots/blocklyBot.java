package nz.daved.starbattle.bots;


import java.util.LinkedList;;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="138" y="88"><statement name="BLOCK"><block type="controls_if"><statement name="DO0"><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="last_move_state"><field name="STATE">1</field></block></value></block></statement></block></statement><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>

public class CustomStarBattleBot extends StarBattleBot {

    public CustomStarBattleBot(String id) { super(id); }

    protected Coordinate item;


    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        if (false) {
            item = botGameBoard.getCoordinateAtPosition(new Coordinate(0, 0), "left");
        }

        return botGameBoard.getFirstValidCoordinate();
    }
}