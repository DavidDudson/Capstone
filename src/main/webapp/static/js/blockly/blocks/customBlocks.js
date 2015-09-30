Blockly.Blocks['check_state_of_coordinate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("is there ")
        .appendField(new Blockly.FieldDropdown([["Water", "0"], ["Ship", "1"]]), "state");
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("at position");
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
        .appendField("get the position")
        .appendField(new Blockly.FieldDropdown([["Left", "left"], ["Right", "right"], ["Up", "up"], ["Down", "down"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("Coordinate")
        .appendField("of the position");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
    this.setColour(230);
    this.setTooltip('get the coordinate neighbouring the given position');
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
        .appendField("Game Board");
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
        this.setTooltip('get all the valid moves available on the game board');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['last_move_state'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("was the last move a")
            .appendField(new Blockly.FieldDropdown([["hit", "1"], ["miss", "2"], ["sunk", "3"]]), "state");
        this.setOutput(true, "Boolean");
        this.setColour(230);
        this.setTooltip('was the last move a hit, miss, or sunk');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['the_last_move'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("the last move");
        this.setOutput(true, "Coordinate");
        this.setColour(330);
        this.setTooltip('get the last targeted coordinate');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['comment'] = {
    init: function() {
        this.appendDummyInput()
            .appendField(new Blockly.FieldTextInput("Comment"), "comment");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour(65);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['list_of_played_moves'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("list of all moves played");
        this.setOutput(true, "Array");
        this.setColour(230);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['last_hit_move'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("the last move that was a hit");
        this.setOutput(true, "Coordinate");
        this.setColour(230);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['define_coordinate'] = {
    init: function() {
        this.appendValueInput("X")
            .setCheck("Number")
            .appendField("Coordinate X:");
        this.appendValueInput("Y")
            .setCheck("Number")
            .appendField(" Y:");
        this.setInputsInline(true);
        this.setOutput(true, "Coordinate");
        this.setColour(330);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['return_coordinate'] = {
    init: function() {
        this.appendValueInput("RETURN")
            .setCheck('Coordinate')
            .appendField("return");
        this.setPreviousStatement(true, null);
        this.setColour(290);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['function_next_move'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("To get the next move");
        this.appendStatementInput("BLOCK")
            .setCheck(null);
        this.appendValueInput("RETURN")
            .setCheck("Coordinate")
            .setAlign(Blockly.ALIGN_RIGHT)
            .appendField("return");
        this.setColour(290);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};