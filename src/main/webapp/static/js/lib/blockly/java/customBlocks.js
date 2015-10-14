Blockly.Java['check_state_of_coordinate'] = function (block) {
    var dropdown_state = 0;
    var value_coordinate = '';
    try {
        dropdown_state = block.getFieldValue('state');
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}

    var code = 'botGameBoard.checkStateOfCoordinate((Coordinate)' + value_coordinate + ", " + dropdown_state + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_all_cells_of_type'] = function (block) {
    var dropdown_state = 0;
    try {
        dropdown_state = block.getFieldValue('state');
    }
    catch (err){}
    var code = 'botGameBoard.getCoordinatesWithState('+ dropdown_state +')';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_state_coordinate_at_pos'] = function (block) {
    var dropdown_position = '';
    var value_input = '';
    try {
        dropdown_position = block.getFieldValue('POSITION');
        value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getStateOfCoordinateAtPosition(' + value_input + ', "' + dropdown_position + '")';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['can_attack_coordinate'] = function (block) {
    var value_coordinate = '';
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.canAttackCoordinate((Coordinate)' + value_coordinate + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_first_valid_coordinate'] = function (block) {
    var code = 'botGameBoard.getFirstValidCoordinate()';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_last_valid_coordinate'] = function (block) {
    var code = 'botGameBoard.getLastValidCoordinate()';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_neighbour_valid_coordinates'] = function (block) {
    var value_coordinate = '';
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getNeighbourValidCoordinates((Coordinate)' + value_coordinate + ")";
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_all_valid_moves'] = function(block) {
    var code = 'botGameBoard.getAllValidCoordinates()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['last_move_state'] = function(block) {
    var state = 1;
    try {
        state = block.getFieldValue('STATE');
    }
    catch (err){}
    var code = 'botGameBoard.getLastMoveState(' + state + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['the_last_move'] = function(block) {
    var code = 'botGameBoard.getLastMove()';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['comment'] = function(block) {
    var text_comment;
    try {
        text_comment = block.getFieldValue('comment');
    }
    catch (err){}
    var code = '';
    return code;
};

Blockly.Java['list_of_played_moves'] = function(block) {
    var code = 'botGameBoard.getHistory()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['last_hit_move'] = function(block) {
    var code = 'botGameBoard.getLastHitMove()';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['define_coordinate'] = function(block) {
    var value_x = 0;
    var value_y = 0;
    try {
        value_x = Blockly.Java.valueToCode(block, 'X', Blockly.Java.ORDER_ATOMIC);
        value_y = Blockly.Java.valueToCode(block, 'Y', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'new Coordinate(' + value_x + ', ' + value_y + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['return_coordinate'] = function(block) {
    var value_return = '';
    try {
        value_return = Blockly.Java.valueToCode(block, 'RETURN', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'return (Coordinate)' + value_return + ';\n';
    return code;
};

Blockly.Java['function_next_move'] = function(block) {
    var statements_block = '';
    var value_return = '';
    try {
        statements_block = Blockly.Java.statementToCode(block, 'BLOCK');
        value_return = Blockly.Java.valueToCode(block, 'RETURN', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var return_code = 'return (Coordinate)' + value_return + ';\n';
    
    var code = '@Override\n' +
                'public Coordinate nextMove(BotGameBoard botGameBoard) {\n' +
                statements_block + '\n' +
                return_code +
                '}';
    return code;
};

Blockly.Java['last_move_sink_bot'] = function(block) {
  var code = 'botGameBoard.lastMoveSunkBot()';
  return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['check_neighbours_for_best_attack'] = function (block) {
    var value_input = '';
    try {
        value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.findAndHitNeighbour((Coordinate)' + value_input + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['coordinate_state'] = function(block) {
    var dropdown_state = '0';
    try {
        dropdown_state = block.getFieldValue('STATE');
    }
    catch (err){}
    var code = dropdown_state;
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['states_of_neighbours'] = function(block) {
    var value_name = '';
    try {
        value_name = Blockly.Java.valueToCode(block, 'NAME', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getStatesOfNeighbours(' + value_name + ')';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_X_coord'] = function (block) {
    var value_coordinate = '';
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getXCoord((Coordinate)'+ value_coordinate + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_Y_coord'] = function (block) {
    var value_coordinate = '';
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getYCoord((Coordinate)'+ value_coordinate + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['list_is_empty'] = function(block) {
    var value_name = '';
    var dropdown_name = '==';
    try {
        value_name = Blockly.Java.valueToCode(block, 'NAME', Blockly.Java.ORDER_ATOMIC);
        dropdown_name = block.getFieldValue('NAME');
    }
    catch (err){}

    var code = value_name + '.size() ' + dropdown_name + ' 0';
    return [code, Blockly.Java.ORDER_LOGICAL_NOT];
};

Blockly.Java['get_all_ship_sizes'] = function(block) {
    var code = 'botGameBoard.getShipSizes()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_remaining_ship_sizes'] = function(block) {
    var code = 'botGameBoard.getRemainingShipSizes()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_all_coordinates'] = function(block) {
    var code = 'botGameBoard.getAllCoordinates()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['lists_concat_with'] = function(block) {
    // Create a list with any number of elements of any type.
    var code = [];
    try {
        code = new Array(block.itemCount_);
        for (var n = 0; n < block.itemCount_; n++) {
            code[n] = Blockly.Java.valueToCode(block, 'ADD' + n,
                    Blockly.Java.ORDER_NONE) || 'None';
        }
        Blockly.Java.addImport('java.util.Arrays');
    }
    catch (err){}

    code = 'botGameBoard.concatLists(Arrays.asList(' + code.join(', ') + '))';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['get_step_coord'] = function (block) {

    var value_coordinate = '';
    var step_amount = 0;
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
        step_amount = Blockly.Java.valueToCode(block, 'STEP', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getXthCoord((Coordinate)'+ value_coordinate + ',' + step_amount + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};