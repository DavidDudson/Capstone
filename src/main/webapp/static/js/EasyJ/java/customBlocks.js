Blockly.Java['check_state_of_cell'] = function (block) {
    var dropdown_state = block.getFieldValue('state');
    var value_cell = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    // TODO: Assemble Java into code variable.
    var code = 'botGameBoard.getStateOfCell(' + value_cell + ', "' + dropdown_state + '")';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_cell_at_pos'] = function (block) {
    var dropdown_position = block.getFieldValue('POSITION');
    var value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.getCellAtPosition(' + value_input + ', "' + dropdown_position + '")';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['if_cell_hit_aim_direction'] = function (block) {
    var value_cell = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var dropdown_direction = block.getFieldValue('direction');
    // TODO: Assemble Java into code variable.
    var code = 'botGameBoard.ifCellHitAimDirection(' + value_cell + ', "' + dropdown_direction + '")';
    return code;
};

Blockly.Java['can_attack_cell'] = function (block) {
    var value_cell = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.canAttackCell(' + value_cell + ')';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_next_valid_cell'] = function (block) {
    var code = 'botGameBoard.getNextValidMove()';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_neighbour_valid_cells'] = function (block) {
    var value_cell = Blockly.Java.valueToCode(block, 'Coordinate', Blockly.Java.ORDER_ATOMIC);
    var code = 'botGameBoard.getNeighbourValidCells(' + value_cell + ")";
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['variable_define'] = function (block) {
    var text_name = block.getFieldValue('NAME');
    var dropdown_type = block.getFieldValue('TYPE');
    var dropdown_options = {
        INT: ['int ', '0'],
        LIST: ['List ', 'new ArrayList<Object>'],
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
    var code = 'botGameBoard.getAllValidMoves()';
    // TODO: Change ORDER_NONE to the correct strength.
    return [code, Blockly.Java.ORDER_NONE];
};