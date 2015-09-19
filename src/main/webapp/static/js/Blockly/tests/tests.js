var block_tests = [
    {block:'check_state_of_coordinate', expected:['botGameBoard.getStateOfCoordinate(, "")', Blockly.Java.ORDER_ATOMIC]},
    {block:'get_coordinate_at_pos', expected:['botGameBoard.getCoordinateAtPosition(, "")', Blockly.Java.ORDER_ATOMIC]},
    {block:'if_last_move_hit_aim_direction', expected:['botGameBoard.ifLastMoveHitAimDirection("up")', Blockly.Java.ORDER_ATOMIC]},
    {block:'can_attack_coordinate', expected:['botGameBoard.canAttackCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_first_valid_coordinate', expected:['botGameBoard.getFirstValidCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_last_valid_coordinate', expected:['botGameBoard.getLastValidCoordinate()',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_neighbour_valid_coordinates', expected:['botGameBoard.getNeighbourValidCoordinates()',Blockly.Java.ORDER_COLLECTION]},
    {block:'get_gamestate', expected:['botGameBoard',Blockly.Java.ORDER_ATOMIC]},
    {block:'get_all_valid_moves', expected:['botGameBoard.getAllValidCoordinates()',Blockly.Java.ORDER_COLLECTION]},
    {block:'last_move_sunk', expected:['botGameBoard.lastMove(1)',Blockly.Java.ORDER_ATOMIC]},
    {block:'the_last_move', expected:['botGameBoard.getLastMove()',Blockly.Java.ORDER_ATOMIC]}];

var default_blocks = '<xml id="mitch-startBlocks" style="display:none"><block type="procedures_defreturn" id="1" x="63" y="63" deletable="false" editable="false"><mutation></mutation><field name="NAME">nextMove</field><value name="RETURN"><block type="get_first_valid_coordinate"></block></value></block></xml>';
var default_output = 'import nz.daved.starbattle.StarBattleBot;\nimport nz.daved.starbattle.game.BotGameBoard;\nimport nz.daved.starbattle.game.Coordinate;\n\npublic class CustomStarBattleBot extends StarBattleBot {\n\npublic CustomStarBattleBot(String id) { super(id); }\n\n@Override\npublic Coordinate nextMove(BotGameBoard botGameBoard) {\n  return botGameBoard.getFirstValidCoordinate();\n}\n\n}\n\n';

var default_test = {blocks: default_blocks, expected: default_output};

var connection_tests = [{block:'',expected:''}];
