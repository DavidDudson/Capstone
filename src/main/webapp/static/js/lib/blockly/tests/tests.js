var block_tests = [
    {block:'check_state_of_coordinate',
        generated:['botGameBoard.checkStateOfCoordinate(, 0)', Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:['Coordinate']},
    {block:'get_all_cells_of_type',
        generated:['botGameBoard.getCoordinatesWithState(0)',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'get_state_coordinate_at_pos',
        generated:['botGameBoard.getStateOfCoordinateAtPosition(, "")',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:['Number']},
    {block:'can_attack_coordinate',
        generated:['botGameBoard.canAttackCoordinate()',Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:['Coordinate']},
    {block:'get_first_valid_coordinate',
        generated:['botGameBoard.getFirstValidCoordinate()',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:[]},
    {block:'get_last_valid_coordinate',
        generated:['botGameBoard.getLastValidCoordinate()',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:[]},
    {block:'get_neighbour_valid_coordinates',
        generated:['botGameBoard.getNeighbourValidCoordinates()',Blockly.Java.ORDER_COLLECTION],
        output:'Array',
        input:['Coordinate']},
    //{block:'get_gamestate', generated:['botGameBoard.getGameBoard()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_all_valid_moves',
        generated:['botGameBoard.getAllValidCoordinates()',Blockly.Java.ORDER_COLLECTION],
        output:'Array',
        input:[]},
    {block:'last_move_state',
        generated:['botGameBoard.getLastMoveState(1)',Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:[]},
    {block:'the_last_move',
        generated:['botGameBoard.getLastMove()',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:[]},
    {block:'list_of_played_moves',
        generated:['botGameBoard.getHistory()',Blockly.Java.ORDER_COLLECTION],
        output:'Array',
        input:[]},
    {block:'last_hit_move',
        generated:['botGameBoard.getLastHitMove()',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:[]},
    {block:'define_coordinate',
        generated:['new Coordinate(0, 0)',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:['Number','Number']},
    {block:'return_coordinate',
        generated:'return ;\n',
        input:['Coordinate']},
    {block:'function_next_move',
        generated:'@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n\nreturn ;\n}'},
    {block:'last_move_sink_bot',
        generated:['botGameBoard.lastMoveSunkBot()',Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:[]},
    {block:'check_neighbours_for_best_attack',
        generated:['botGameBoard.findAndHitNeighbour()',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:['Coordinate']},
    {block:'coordinate_state',
        generated:['0',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:[]},
    {block:'get_X_coord',
        generated:['botGameBoard.getXCoord()',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:['Coordinate']},
    {block:'get_Y_coord',
        generated:['botGameBoard.getYCoord()',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:['Coordinate']},
    {block:'list_is_empty',
        generated:['.size() == 0',Blockly.Java.ORDER_LOGICAL_NOT],
        output:'Boolean',
        input:['Array']}];


var default_blocks = '<xml id="mitch-startBlocks" style="display:none">' +
    '<block type="function_next_move" id="1" x="63" y="63" deletable="false" editable="false">' +
    '<value name="RETURN">' +
    '<block type="get_first_valid_coordinate">' +
    '</block>' +
    '</value>' +
    '</block>' +
    '</xml>';

var default_output = 'import java.util.LinkedList;\nimport nz.daved.starbattle.StarBattleBot;\nimport nz.daved.starbattle.Var;\nimport nz.daved.starbattle.game.BotGameBoard;\nimport nz.daved.starbattle.game.Coordinate;\n\n// <xml xmlns=\"http://www.w3.org/1999/xhtml\"><block type="function_next_move" deletable=\"false\" editable=\"false\" x=\"63\" y=\"63\"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>\n\npublic class CustomStarBattleBot extends StarBattleBot {\n\npublic CustomStarBattleBot(String id) { super(id); }\n\n@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n\nreturn botGameBoard.getFirstValidCoordinate();\n}\n}\n\n';

var default_test = {blocks: default_blocks, expected: default_output};

var connection_blocks = '<xml xmlns="http://www.w3.org/1999/xhtml">' +
    '<block type="check_state_of_coordinate">' +
        '<field name="state">water</field>' +
    '</block>' +
    '<block type="get_all_cells_of_type"></block>' +
    '<block type="get_state_coordinate_at_pos"></block>' +
    '<block type="can_attack_coordinate"></block>' +
    '<block type="get_first_valid_coordinate"></block>' +
    '<block type="get_last_valid_coordinate"></block>' +
    '<block type="get_neighbour_valid_coordinates"></block>' +
    //'<block type="get_gamestate" id="10" x="13" y="363"></block>' +
    '<block type="get_all_valid_moves"></block>' +
    '<block type="last_move_state">' +
        '<field name="state">1</field>' +
    '</block>' +
    '<block type="the_last_move"></block>' +
    '<block type="list_of_played_moves"></block>' +
    '<block type="last_hit_move"></block>' +
    '<block type="define_coordinate"></block>' +
    '<block type="return_coordinate"></block>' +
    '<block type="function_next_move"></block>' +
    '<block type="last_move_sink_bot"></block>' +
    '<block type="check_neighbours_for_best_attack"></block>' +
    '<block type="coordinate_state"></block>' +
    '<block type="get_X_coord"></block>' +
    '<block type="get_Y_coord"></block>' +
    '<block type="list_is_empty"></block>' +
    '</xml>';
