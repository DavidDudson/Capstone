package nz.daved.starbattle.bots;


import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;
import nz.daved.starbattle.Var;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>


// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="63" y="63"><statement name="BLOCK"><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="get_all_cells_of_type"><field name="state">0</field></block></value><next><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="math_number"><field name="NUM">0</field></block></value></block></next></block></statement><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>

public class blocklyBot extends StarBattleBot {

    public blocklyBot(String id) { super(id); }

    protected Var item = new Var(0);


    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        item.setObject(botGameBoard.getCoordinatesWithState(0));
        item.setObject(botGameBoard.checkStateOfCoordinate(new Coordinate(0, 0),0));

        return botGameBoard.getFirstValidCoordinate();
    }
}
