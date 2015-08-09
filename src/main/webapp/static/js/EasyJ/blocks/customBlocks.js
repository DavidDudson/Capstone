Blockly.Blocks['get_state'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["is Water", "isWater"], ["is Ship", "isShip"]]), "state");
    this.appendValueInput("CELL")
        .setCheck(["cell", "String"])
        .appendField("at cell");
    this.setInputsInline(true);
    this.setDeletable(false);
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

Blockly.Blocks['next_Move'] = {
  init: function() {
    this.appendStatementInput("nextMove")
        .setCheck("String")
        .appendField("Next Move");
    this.setOutput(true, "String");
    this.setColour(20);
    this.setDeletable(false);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};