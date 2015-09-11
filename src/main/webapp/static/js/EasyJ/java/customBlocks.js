Blockly.Java['check_state_of_coordinate'] = function (block) {
    var dropdown_state = block.getFieldValue('state');
    var value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    // TODO: Assemble Java into code variable.
    var code = 'botGameBoard.getStateOfCoordinate(' + value_coordinate + ', "' + dropdown_state + '")';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_coordinate_at_pos'] = function (block) {
    var dropdown_position = block.getFieldValue('POSITION');
    var value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.getCoordinateAtPosition(' + value_input + ', "' + dropdown_position + '")';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['if_coordinate_hit_aim_direction'] = function (block) {
    var value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var dropdown_direction = block.getFieldValue('direction');
    // TODO: Assemble Java into code variable.
    var code = 'botGameBoard.ifCoordinateHitAimDirection(' + value_coordinate + ', "' + dropdown_direction + '")';
    return code;
};

Blockly.Java['can_attack_coordinate'] = function (block) {
    var value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.canAttackCoordinate(' + value_coordinate + ')';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_first_valid_coordinate'] = function (block) {
    var code = 'botGameBoard.getFirstValidCoordinate()';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_last_valid_coordinate'] = function (block) {
    var code = 'botGameBoard.getLastValidCoordinate()';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_neighbour_valid_coordinates'] = function (block) {
    var value_coordinate = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.getNeighbourValidCoordinates(' + value_coordinate + ")";
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
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
    var code = 'botGameBoard';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_all_valid_moves'] = function(block) {
    var code = 'botGameBoard.getAllValidCoordinates()';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};