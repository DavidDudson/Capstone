Blockly.Blocks['get_state'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["is Water", "isWater"], ["is Ship", "isShip"]]), "state");
    this.appendValueInput("Coordinate")
        .setCheck(["Coordinate", "String"])
        .appendField("at cell");
    this.setInputsInline(true);
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('ask if the position is water or a known ship position');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_cell_at_pos'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["get Left", "getLeft"], ["get Right", "getRight"], ["get Up", "getUp"], ["get Down", "getDown"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("Coordinate")
        .appendField("at position");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
    this.setColour(230);
    this.setTooltip('get the cell neighbouring the given position');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['if_cell_hit_aim_direction'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck(["String", "Coordinate"])
        .appendField("if cell");
    this.appendDummyInput()
        .appendField("is hit, try")
        .appendField(new Blockly.FieldDropdown([["up", "UP"], ["down", "DOWN"], ["left", "LEFT"], ["right", "RIGHT"]]), "direction");
    this.setInputsInline(true);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(230);
    this.setTooltip('if the given cell is hit, go to the given direction');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['can_attack_cell'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck(["String", "Coordinate"])
        .appendField("can");
    this.appendDummyInput()
        .appendField("be attacked");
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('can the given cell be hit, or has already been hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_next_valid_cell'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the next valid move");
    this.setOutput(true, ["String", "Coordinate"]);
	this.setColour(230);
    this.setTooltip('returns the next valid cell');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_neighbour_valid_cells'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck(["String", "Coordinate"])
        .appendField("get valid neighbors of");
    this.setOutput(true, "Array");
    this.setColour(230);
    this.setTooltip('the neighbouring cells of the given position that can be hit');
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
    this.setTooltip('create a new variable of a certain type');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_gamestate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("Gamestate");
    this.setOutput(true, "Array");
    this.setColour(330);
    this.setTooltip('The StarBattles gamestate');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_all_valid_moves'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("list of all valid moves");
        this.setOutput(true, "Array");
        this.setColour(230);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};