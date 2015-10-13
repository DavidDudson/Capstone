package nz.daved.starbattle.bots;


import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;
import nz.daved.starbattle.Var;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>


// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><statement name="BLOCK"><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="get_all_cells_of_type"><field name="state">0</field></block></value><next><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="math_number"><field name="NUM">0</field></block></value></block></next></block></statement><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>

import java.util.LinkedList;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.Var;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><statement name="BLOCK"><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="get_all_cells_of_type"><field name="state">0</field></block></value></block></statement><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>

public class blocklyBot extends StarBattleBot {

    public blocklyBot(String id) { super(id); }



    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        if(botGameBoard.getHistory().isEmpty()){
            return botGameBoard.getFirstValidCoordinate();
        }

        return botGameBoard.getXthCoord(botGameBoard.getLastMove(), 9);
    }
}
