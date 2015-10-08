package nz.daved.starbattle.bots;


import java.util.LinkedList;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="138" y="88"><statement name="BLOCK"><block type="controls_if"><mutation elseif="1" else="1"></mutation><value name="IF0"><block type="lists_isEmpty"><value name="VALUE"><block type="list_of_played_moves"></block></value></block></value><statement name="DO0"><block type="return_coordinate"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></statement><value name="IF1"><block type="lists_isEmpty"><value name="VALUE"><block type="get_neighbour_valid_coordinates"><value name="Coordinate"><block type="the_last_move"></block></value></block></value></block></value><statement name="DO1"><block type="return_coordinate"><value name="RETURN"><block type="the_last_move"></block></value></block></statement><statement name="ELSE"><block type="return_coordinate"><value name="RETURN"><block type="check_neighbours_for_best_attack"><value name="INPUT"><block type="the_last_move"></block></value></block></value></block></statement></block></statement></block></xml>

public class blocklyBot extends StarBattleBot {

    public blocklyBot(String id) { super(id); }

    @Override
    public Coordinate nextMove(BotGameBoard botGameBoard) {
        if (botGameBoard.getHistory().size() == 0) {
            return botGameBoard.getFirstValidCoordinate();
        } else if (botGameBoard.getNeighbourValidCoordinates(botGameBoard.getLastMove()).size() == 0) {
            return botGameBoard.getLastMove();
        } else {
            return botGameBoard.findAndHitNeighbour(botGameBoard.getLastMove());
        }


    }
}
