Blockly.Java['get_state'] = function(block) {
  var dropdown_state = block.getFieldValue('state');
  var value_cell = Blockly.Java.valueToCode(block, 'CELL', Blockly.Java.ORDER_ATOMIC);
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_cell_at_pos'] = function(block) {
  var dropdown_position = block.getFieldValue('POSITION');
  var value_input = Blockly.Java.valueToCode(block, 'INPUT', Blockly.Java.ORDER_ATOMIC);
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['if_cell_hit_aim_direction'] = function(block) {
  var value_cell = Blockly.Java.valueToCode(block, 'CELL', Blockly.Java.ORDER_ATOMIC);
  var dropdown_direction = block.getFieldValue('direction');
  // TODO: Assemble Java into code variable.
  var code = '...';
  return code;
};

Blockly.Java['can_attack_cell'] = function(block) {
  var value_cell = Blockly.Java.valueToCode(block, 'CELL', Blockly.Java.ORDER_ATOMIC);
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_next_valid_cell'] = function(block) {
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['get_neighbour_valid_cells'] = function(block) {
  var value_cell = Blockly.Java.valueToCode(block, 'CELL', Blockly.Java.ORDER_ATOMIC);
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};

Blockly.Java['variable_define'] = function(block) {
  var text_name = block.getFieldValue('NAME');
  var dropdown_type = block.getFieldValue('TYPE');
  // TODO: Assemble Java into code variable.
  var dropdown_options = {INT:['int ','0'], LIST:['List ','new ArrayList<Object>'], COORD:['String ','"A1"'], BOOL: ['boolean ','true']};
  var code = dropdown_options[dropdown_type][0];
  code += text_name;
  code += ' = ';
  code += dropdown_options[dropdown_type][1];
  code += ';\n';
  return code;
};

Blockly.Java['get_gamestate'] = function(block) {
  // TODO: Assemble Java into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Java.ORDER_NONE];
};