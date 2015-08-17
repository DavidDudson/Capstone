Blockly.Blocks['get_state'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["is Water", "isWater"], ["is Ship", "isShip"]]), "state");
    this.appendValueInput("CELL")
        .setCheck(["cell", "String"])
        .appendField("at cell");
    this.setInputsInline(true);
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_cell_at_pos'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["get Left", "getLeft"], ["get Right", "getRight"], ["get Up", "getUp"], ["get Down", "getDown"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("cell")
        .appendField("at position");
    this.setInputsInline(true);
    this.setOutput(true, "cell");
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['if_cell_hit_aim_direction'] = {
  init: function() {
    this.appendValueInput("CELL")
        .setCheck(["String", "CELL"])
        .appendField("if cell");
    this.appendDummyInput()
        .appendField("is hit, try")
        .appendField(new Blockly.FieldDropdown([["up", "UP"], ["down", "DOWN"], ["left", "LEFT"], ["right", "RIGHT"]]), "direction");
    this.setInputsInline(true);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['can_attack_cell'] = {
  init: function() {
    this.appendValueInput("CELL")
        .setCheck(["String", "cell"])
        .appendField("can");
    this.appendDummyInput()
        .appendField("be attacked");
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_next_valid_cell'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the next valid cell");
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_neighbour_valid_cells'] = {
  init: function() {
    this.appendValueInput("CELL")
        .setCheck(["String", "cell"])
        .appendField("get valid neighbors of");
    this.setOutput(true, "Array");
    this.setColour(230);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['variable_define'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("define")
        .appendField(new Blockly.FieldTextInput("name"), "NAME");
    this.appendDummyInput()
        .appendField("of type")
        .appendField(new Blockly.FieldDropdown([["integer", "INT"], ["list", "LIST"], ["coord", "COORD"], ["boolean", "BOOL"]]), "TYPE");
    this.setInputsInline(true);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(330);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_gamestate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("Gamestate");
    this.setOutput(true, "Array");
    this.setColour(330);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
}