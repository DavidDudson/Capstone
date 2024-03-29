var block_tests = [
    {block:'check_state_of_coordinate',
        generated:['botGameBoard.checkStateOfCoordinate((Coordinate), 0)', Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:['Coordinate']},
    {block:'get_all_cells_of_type',
        generated:['botGameBoard.getCoordinatesWithState(0)',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'get_state_coordinate_at_pos',
        generated:['botGameBoard.getStateOfCoordinateAtPosition(, "")',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:['Coordinate']},
    {block:'can_attack_coordinate',
        generated:['botGameBoard.canAttackCoordinate((Coordinate))',Blockly.Java.ORDER_ATOMIC],
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
        generated:['botGameBoard.getNeighbourValidCoordinates((Coordinate))',Blockly.Java.ORDER_COLLECTION],
        output:'Array',
        input:['Coordinate']},
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
        generated:'return (Coordinate);\n',
        input:['Coordinate']},
    {block:'function_next_move',
        generated:'@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n\nreturn (Coordinate);\n}'},
    {block:'last_move_sink_bot',
        generated:['botGameBoard.lastMoveSunkBot()',Blockly.Java.ORDER_ATOMIC],
        output:'Boolean',
        input:[]},
    {block:'check_neighbours_for_best_attack',
        generated:['botGameBoard.findAndHitNeighbour((Coordinate))',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:['Coordinate']},
    {block:'coordinate_state',
        generated:['0',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:[]},
    {block:'states_of_neighbours',
        generated:['botGameBoard.getStatesOfNeighbours()',Blockly.Java.ORDER_COLLECTION],
        output:'Array',
        input:['Coordinate']},
    {block:'get_X_coord',
        generated:['botGameBoard.getXCoord((Coordinate))',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:['Coordinate']},
    {block:'get_Y_coord',
        generated:['botGameBoard.getYCoord((Coordinate))',Blockly.Java.ORDER_ATOMIC],
        output:'Number',
        input:['Coordinate']},
    {block:'list_is_empty',
        generated:['.size() == 0',Blockly.Java.ORDER_LOGICAL_NOT],
        output:'Boolean',
        input:['Array']},
    {block:'get_all_ship_sizes',
        generated:['botGameBoard.getShipSizes()',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'get_remaining_ship_sizes',
        generated:['botGameBoard.getRemainingShipSizes()',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'get_all_coordinates',
        generated:['botGameBoard.getAllCoordinates()',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'lists_concat_with',
        generated:['botGameBoard.concatLists(Arrays.asList())',Blockly.Java.ORDER_COLLECTION],
        output:'Array'},
    {block:'get_step_coord',
        generated:['botGameBoard.getXthCoord((Coordinate),0)',Blockly.Java.ORDER_ATOMIC],
        output:'Coordinate',
        input:['Number','Coordinate']}];


var default_blocks = '<xml id="mitch-startBlocks" style="display:none">' +
    '<block type="function_next_move" id="1" x="63" y="63" deletable="false" editable="false">' +
    '<value name="RETURN">' +
    '<block type="get_first_valid_coordinate"></block>' +
    '</value>' +
    '</block>' +
    '</xml>';

var default_output = 'import java.util.LinkedList;\nimport nz.daved.starbattle.StarBattleBot;\nimport nz.daved.starbattle.Var;\nimport nz.daved.starbattle.game.BotGameBoard;\nimport nz.daved.starbattle.game.Coordinate;\n\n// <xml xmlns=\"http://www.w3.org/1999/xhtml\"><block type="function_next_move" deletable=\"false\" editable=\"false\" x=\"63\" y=\"63\"><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>\n\npublic class CustomStarBattleBot extends StarBattleBot {\n\npublic CustomStarBattleBot(String id) { super(id); }\n\n@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n\nreturn (Coordinate)botGameBoard.getFirstValidCoordinate();\n}\n}\n\n';

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
    '<block type="states_of_neighbours"></block>' +
    '<block type="get_X_coord"></block>' +
    '<block type="get_Y_coord"></block>' +
    '<block type="list_is_empty"></block>' +
    '<block type="get_all_ship_sizes"></block>' +
    '<block type="get_remaining_ship_sizes"></block>' +
    '<block type="get_all_coordinates"></block>' +
    '<block type="lists_concat_with"></block>' +
    '<block type="get_step_coord"></block>' +
    '</xml>';

goog.provide('Blockly.Blocks.lists');
goog.provide('Blockly.Blocks.math');
goog.provide('Blockly.Blocks.variables');
goog.provide('Blockly.Blocks.procedures');
goog.provide('Blockly.Blocks.logic');

goog.require('Blockly.Blocks');

Blockly.Blocks.lists.HUE = 260;
Blockly.Blocks.math.HUE = 230;
Blockly.Blocks.variables.HUE = 330;
Blockly.Blocks.procedures.HUE = 290;
Blockly.Blocks.logic.HUE = 210;