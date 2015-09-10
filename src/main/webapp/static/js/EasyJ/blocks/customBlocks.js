Blockly.Blocks['check_state_of_coordinate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["is Water", "water"], ["is Ship", "ship"]]), "state");
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("at coordinate");
    this.setInputsInline(true);
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('ask if the position is water or a known ship position');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_coordinate_at_pos'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldDropdown([["get Left", "left"], ["get Right", "right"], ["get Up", "up"], ["get Down", "down"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("Coordinate")
        .appendField("from position");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
    this.setColour(230);
    this.setTooltip('get the coordinate neighbouring the given position');
    this.setHelpUrl('http://www.example.com/');
  }
};

// TODO: The type of block is incorrect
Blockly.Blocks['if_coordinate_hit_aim_direction'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("if coordinate");
    this.appendDummyInput()
        .appendField("is hit, try")
        .appendField(new Blockly.FieldDropdown([["up", "up"], ["down", "down"], ["left", "left"], ["right", "right"]]), "direction");
    this.setInputsInline(true);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(230);
    this.setTooltip('if the given coordinate is hit, go to the given direction');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['can_attack_coordinate'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("can");
    this.appendDummyInput()
        .appendField("be attacked");
    this.setOutput(true, "Boolean");
    this.setColour(230);
    this.setTooltip('can the given coordinate be hit, or has already been hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_first_valid_coordinate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the first valid move");
    this.setOutput(true, "Coordinate");
	this.setColour(230);
    this.setTooltip('returns the first valid coordinate');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_last_valid_coordinate'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("the last valid move");
        this.setOutput(true, "Coordinate");
        this.setColour(230);
        this.setTooltip('returns the last valid coordinate');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['get_neighbour_valid_coordinates'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("the valid neighbors of");
    this.setOutput(true, "Array");
    this.setColour(230);
    this.setTooltip('the neighbouring coordinates of the given position that can be hit');
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