Blockly.Java['check_state_of_coordinate'] = function (block) {
    var dropdown_state = '';
    var value_coordinate = '';
    try {
        dropdown_state = block.getFieldValue('state');
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getStateOfCoordinate(' + value_coordinate + ', "' + dropdown_state + '")';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_coordinate_at_pos'] = function (block) {
    var dropdown_position = '';
    var value_input = '';
    try {
        dropdown_position = block.getFieldValue('POSITION');
        value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.getCoordinateAtPosition(' + value_input + ', "' + dropdown_position + '")';
    return [code, Blockly.Java.ORDER_ATOMIC];
};



Blockly.Java['can_attack_coordinate'] = function (block) {
    var value_coordinate = '';
    try {
        value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    }
    catch (err){}
    var code = 'botGameBoard.canAttackCoordinate(' + value_coordinate + ')';
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
    var code = 'botGameBoard.getNeighbourValidCoordinates(' + value_coordinate + ")";
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['variable_define'] = function (block) {
    var text_name = block.getFieldValue('NAME');
    var dropdown_type = block.getFieldValue('TYPE');
    var dropdown_options = {
        INT: ['int ', '0'],
        LIST: ['List ', 'new LinkedList<Object>'],
        COORD: ['String ', '"A1"'],
        BOOL: ['boolean ', 'true']
    };
    var code = dropdown_options[dropdown_type][0];

    if (dropdown_type == "LIST") {
        Blockly.Java.addImport("java.util.List");
        Blockly.Java.addImport("java.util.ArrayList");
    }

    code += text_name;
    code += ' = ';
    code += dropdown_options[dropdown_type][1];
    code += ';\n';
    return code;
};

Blockly.Java['get_gamestate'] = function (block) {
    var code = 'botGameBoard.getGameBoard()';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['get_all_valid_moves'] = function(block) {
    var code = 'botGameBoard.getAllValidCoordinates()';
    return [code, Blockly.Java.ORDER_COLLECTION];
};

Blockly.Java['last_move_sunk'] = function(block) {
    var dropdown_state = '1';
    try {
        dropdown_state = block.getFieldValue('state');
    }
    catch (err){}
    var code = 'botGameBoard.lastMove(' + dropdown_state + ')';
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['last_move_state'] = function(block) {
    var state = block.getFieldValue('STATE');
    var code = "botGameBoard.getLastMoveState("+ state +")";
    
    return [code, Blockly.Java.ORDER_ATOMIC];
};

Blockly.Java['comment'] = function(block) {
    var text_comment;
    try {
        text_comment = block.getFieldValue('comment');
    }
    catch(err){}
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
        Blockly.Java.addImport('nz.daved.starbattle.game.Coordinate');
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
    var code = 'return ' + value_return + '\n';
    return code;
};

Blockly.Java['function_next_move'] = function(block) {
    var statements_block = Blockly.Java.statementToCode(block, 'BLOCK');
    var value_return = Blockly.Java.valueToCode(block, 'RETURN', Blockly.Java.ORDER_ATOMIC);
    var code = '@Override\n' +
                'public Coordinate nextMove(BotGameBoard botGameBoard) {\n' +
                statements_block + '\n' +
                'return ' + value_return + ';\n' +
                '}';
    return code;
};

Blockly.Java['last_move_sink_bot'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = 'botGameBoard.lastMoveSinkBot()';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_ATOMIC];
};