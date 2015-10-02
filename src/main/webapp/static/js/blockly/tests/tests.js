var block_tests = [
    {block:'check_state_of_coordinate', expected:['botGameBoard.getStateOfCoordinate(, "")', Blockly.Java.ORDER_ATOMIC]},
    {block:'get_coordinate_at_pos', expected:['botGameBoard.getCoordinateAtPosition(, "")', Blockly.Java.ORDER_ATOMIC]},
    {block:'can_attack_coordinate', expected:['botGameBoard.canAttackCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_first_valid_coordinate', expected:['botGameBoard.getFirstValidCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_last_valid_coordinate', expected:['botGameBoard.getLastValidCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_neighbour_valid_coordinates', expected:['botGameBoard.getNeighbourValidCoordinates()',Blockly.Java.ORDER_COLLECTION]},
    {block:'get_gamestate', expected:['botGameBoard.getGameBoard()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_all_valid_moves', expected:['botGameBoard.getAllValidCoordinates()',Blockly.Java.ORDER_COLLECTION]},
    {block:'last_move_state', expected:['botGameBoard.getLastMoveState(1)',Blockly.Java.ORDER_ATOMIC]},
    {block:'the_last_move', expected:['botGameBoard.getHistory().getLast()',Blockly.Java.ORDER_ATOMIC]},
    {block:'list_of_played_moves', expected:['botGameBoard.getHistory()',Blockly.Java.ORDER_COLLECTION]},
    {block:'last_hit_move', expected:['botGameBoard.getLastHitMove()',Blockly.Java.ORDER_ATOMIC]},
    {block:'define_coordinate', expected:['new Coordinate(0, 0)',Blockly.Java.ORDER_ATOMIC]},
    {block:'return_coordinate', expected:'return \n'}];

var default_blocks = '<xml id="mitch-startBlocks" style="display:none"><block type="function_next_move" id="1" x="63" y="63" deletable="false" editable="false"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>';
var default_output = 'import java.util.LinkedList;\nimport nz.daved.starbattle.StarBattleBot;\nimport nz.daved.starbattle.game.BotGameBoard;\nimport nz.daved.starbattle.game.Coordinate;\n\n// <xml xmlns=\"http://www.w3.org/1999/xhtml\"><block type="function_next_move" deletable=\"false\" editable=\"false\" x=\"63\" y=\"63\"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>\n\npublic class CustomStarBattleBot extends StarBattleBot {\n\npublic CustomStarBattleBot(String id) { super(id); }\n\n@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n\nreturn botGameBoard.getFirstValidCoordinate();\n}\n}\n\n';

var default_test = {blocks: default_blocks, expected: default_output};

var connection_blocks = '<xml xmlns="http://www.w3.org/1999/xhtml"><block type="check_state_of_coordinate" id="3" x="13" y="13"><field name="state">water</field></block><block type="get_coordinate_at_pos" id="4" x="13" y="63"><field name="POSITION">left</field></block><block type="can_attack_coordinate" id="6" x="12" y="163"></block><block type="get_first_valid_coordinate" id="7" x="12" y="213"></block><block type="get_last_valid_coordinate" id="8" x="13" y="263"></block><block type="get_neighbour_valid_coordinates" id="9" x="13" y="313"></block><block type="get_gamestate" id="10" x="13" y="363"></block><block type="get_all_valid_moves" id="11" x="13" y="413"></block><block type="last_move_state" id="12" x="13" y="463"><field name="state">1</field></block><block type="the_last_move" id="13" x="13" y="512"></block><block type="list_of_played_moves" id="14"></block><block type="last_hit_move" id="15"></block><block type="define_coordinate" id="16"></block><block type="return_coordinate" id="17"></block></xml>';
var output_connection_tests = [
    {block:'check_state_of_coordinate', expected:'Boolean'},
    {block:'get_coordinate_at_pos', expected:'Coordinate'},
    {block:'can_attack_coordinate', expected:'Boolean'},
    {block:'get_first_valid_coordinate', expected:'Coordinate'},
    {block:'get_last_valid_coordinate', expected:'Coordinate'},
    {block:'get_neighbour_valid_coordinates', expected:'Array'},
    {block:'get_gamestate', expected:'Array'},
    {block:'get_all_valid_moves', expected:'Array'},
    {block:'last_move_state', expected:'Boolean'},
    {block:'the_last_move', expected:'Coordinate'},
    {block:'list_of_played_moves', expected:'Array'},
    {block:'last_hit_move', expected:'Coordinate'},
    {block:'define_coordinate', expected:'Coordinate'}];

var input_connection_tests = [
    {block:'check_state_of_coordinate', expected:['Coordinate']},
    {block:'get_coordinate_at_pos', expected:['Coordinate']},
    {block:'can_attack_coordinate', expected:['Coordinate']},
    {block:'get_first_valid_coordinate', expected:[]},
    {block:'get_last_valid_coordinate', expected:[]},
    {block:'get_neighbour_valid_coordinates', expected:['Coordinate']},
    {block:'get_gamestate', expected:[]},
    {block:'get_all_valid_moves', expected:[]},
    {block:'last_move_state', expected:[]},
    {block:'the_last_move', expected:[]},
    {block:'list_of_played_moves', expected:[]},
    {block:'last_hit_move', expected:[]},
    {block:'define_coordinate', expected:['Number', 'Number']},
    {block:'return_coordinate', expected:['Coordinate']}];