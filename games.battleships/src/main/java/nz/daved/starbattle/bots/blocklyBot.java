package nz.daved.starbattle.bots;


import java.util.LinkedList;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="procedures_defreturn" deletable="false" editable="false" x="63" y="63"><field name="NAME">nextMove</field><statement name="STACK"><block type="variables_set"><field name="VAR">item</field><value name="VALUE"><block type="get_neighbour_valid_coordinates"><value name="Coordinate"><block type="the_last_move"></block></value></block></value></block></statement><value name="RETURN"><block type="variables_get"><field name="VAR">item</field></block></value></block></xml>

public class blocklyBot extends StarBattleBot {

    public blocklyBot(String id) {
        super(id);
    }

    protected LinkedList item;

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        item = (LinkedList) botGameBoard.getNeighbourValidCoordinates(botGameBoard.getHistory().get(botGameBoard.getHistory().size()).getCoord());
        return (Coordinate) item.get(0);
    }
}
