package nz.daved.starbattle.bots;


import java.util.LinkedList;;
import nz.daved.starbattle.StarBattleBot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

// <xml xmlns="http://www.w3.org/1999/xhtml"><block type="function_next_move" deletable="false" editable="false" x="138" y="88"><value name="RETURN"><block type="lists_getIndex"><mutation statement="false" at="true"></mutation><field name="MODE">GET</field><field name="WHERE">FROM_START</field><value name="VALUE"><block type="list_of_played_moves"></block></value><value name="AT"><block type="math_number"><field name="NUM">1</field></block></value></block></value></block></xml>

public class blocklyBot extends StarBattleBot {

public blocklyBot(String id) { super(id); }

@Override
public Coordinate nextMove(BotGameBoard botGameBoard) {

    if(botGameBoard.getHistory().size() == 0){
        return new Coordinate(0,0);
    }
    else {
        return     botGameBoard.findAndHitNeighbour(botGameBoard.getLastMove());

    }


}
}