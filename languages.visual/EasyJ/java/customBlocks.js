Blockly.Java['get_state'] = function(block) {
  var dropdown_state = block.getFieldValue('state');
  var value_cell = Blockly.JavaScript.valueToCode(block, 'CELL', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.Java['get_cell_at_pos'] = function(block) {
  var dropdown_position = block.getFieldValue('POSITION');
  var value_input = Blockly.JavaScript.valueToCode(block, 'INPUT', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.Java['if_cell_hit_aim_direction'] = function(block) {
  var value_cell = Blockly.JavaScript.valueToCode(block, 'CELL', Blockly.JavaScript.ORDER_ATOMIC);
  var dropdown_direction = block.getFieldValue('direction');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  return code;
};

Blockly.Java['can_attack_cell'] = function(block) {
  var value_cell = Blockly.JavaScript.valueToCode(block, 'CELL', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.Java['get_next_valid_cell'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.Java['get_neighbour_valid_cells'] = function(block) {
  var value_cell = Blockly.JavaScript.valueToCode(block, 'CELL', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};