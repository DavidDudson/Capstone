Blockly.Blocks['check_state_of_coordinate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("there is")
        .appendField(new Blockly.FieldDropdown([["space", "0"], ["miss", "1"],  ["hit", "2"], ["sunk", "3"]]), "state");
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("at position");
    this.setInputsInline(true);
    this.setOutput(true, "Boolean");
    this.setColour(210);
    this.setTooltip('ask if the position is water or a known ship position');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_all_cells_of_type'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("a list of all")
        .appendField(new Blockly.FieldDropdown([["space", "0"], ["miss", "1"],  ["hit", "2"], ["sunk", "3"]]), "state")
        .appendField("coordinates");
    this.setInputsInline(true);
    this.setOutput(true, "Array");
    this.setColour(260);
    this.setTooltip('ask if the position is water or a known ship position');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_state_coordinate_at_pos'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the state of the position")
        .appendField(new Blockly.FieldDropdown([["Left", "left"], ["Right", "right"], ["Up", "up"], ["Down", "down"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("Number")
        .appendField("of");
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
        .appendField("be attacked?");
    this.setOutput(true, "Boolean");
    this.setColour(210);
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
    this.setTooltip('gets the first valid coordinate');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_last_valid_coordinate'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("the last valid move");
        this.setOutput(true, "Coordinate");
        this.setColour(230);
        this.setTooltip('gets the last valid coordinate');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['get_neighbour_valid_coordinates'] = {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("the valid neighbors of");
    this.setInputsInline(true);
    this.setOutput(true, "Array");
    this.setColour(260);
    this.setTooltip('the neighbouring coordinates of the given position that can be hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_all_coordinates'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("list of all coordinates");
        this.setOutput(true, "Array");
        this.setColour(260);
        this.setTooltip('get all the coordinates on the game board');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['get_all_valid_moves'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("list of all valid moves");
        this.setOutput(true, "Array");
        this.setColour(260);
        this.setTooltip('get all the valid moves available on the game board');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['last_move_state'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("was the last move a")
            .appendField(new Blockly.FieldDropdown([["space", "0"], ["miss", "1"],  ["hit", "2"], ["sunk", "3"]]), "STATE");
        this.setOutput(true, "Boolean");
        this.setColour(210);
        this.setTooltip('was the last move a hit, miss, or sunk');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['the_last_move'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("your last move");
        this.setOutput(true, "Coordinate");
        this.setColour(230);
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
        this.setTooltip('Comment block, does not create any code');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['list_of_played_moves'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("list of all moves played");
        this.setOutput(true, "Array");
        this.setColour(260);
        this.setTooltip('a list of the previously played moves');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['last_hit_move'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("your last move that hit a ship");
        this.setOutput(true, "Coordinate");
        this.setColour(230);
        this.setTooltip('the last targeted coordinate');
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
            .appendField("attack");
        this.setPreviousStatement(true, null);
        this.setColour(290);
        this.setTooltip('attack the given coord:');
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
            .appendField("attack");
        this.setColour(290);
        this.setTooltip('The main function block');
        this.setHelpUrl('http://www.example.com/');
        this.hasReturnValue_ = true;
        this.arguments_ = [];
    },
    getProcedureDef: function() {
        return [Object.create(null), this.arguments_, this.hasReturnValue_];
    }
};

Blockly.Blocks['last_move_sink_bot'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the last move sunk a ship?");
    this.setOutput(true, "Boolean");
    this.setColour(210);
    this.setTooltip('did the last move sink a ship');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['check_neighbours_for_best_attack'] = {
  init: function() {
    this.appendValueInput("INPUT")
        .setCheck("Coordinate")
        .appendField("select best move from coord");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
    this.setColour(230);
    this.setTooltip('check the coordinate and neighbours and select next choosen coordinate based on weather the neighbours have been hit');
    this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['coordinate_state'] = {
    init: function() {
        this.appendDummyInput()
            .appendField(new Blockly.FieldDropdown([["space", "0"], ["miss", "1"],  ["hit", "2"], ["sunk", "3"]]), "STATE");
        this.setOutput(true, "Number");
        this.setColour(230);
        this.setTooltip('Possible states of a Coordinate');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['get_all_ship_sizes'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("all spaceship sizes");
    this.setOutput(true, "Array");
    this.setColour(260);
    this.setTooltip('returns a list of all spaceship sizes');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_remaining_ship_sizes'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("all remaining spaceship sizes");
    this.setOutput(true, "Array");
    this.setColour(260);
    this.setTooltip('returns a list of all remaining spaceship sizes');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['states_of_neighbours'] = {
    init: function() {
        this.appendValueInput("NAME")
            .setCheck("Coordinate")
            .appendField("the state of the neighbouring positions of");
        this.setInputsInline(true);
        this.setOutput(true, "Array");
        this.setColour(230);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['get_X_coord']= {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("get X position from :");
    this.setOutput(true, "Number");
    this.setColour(230);
    this.setTooltip('can the given coordinate be hit, or has already been hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_Y_coord']= {
  init: function() {
    this.appendValueInput("Coordinate")
        .setCheck("Coordinate")
        .appendField("get Y position from :");
    this.setOutput(true, "Number");
    this.setColour(230);
    this.setTooltip('can the given coordinate be hit, or has already been hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['list_is_empty'] = {
    init: function() {
        this.appendValueInput("NAME")
            .setCheck("Array");
        this.appendDummyInput()
            .appendField(new Blockly.FieldDropdown([["is", "=="], ["is not", "!="]]), "NAME")
            .appendField("empty");
        this.setInputsInline(true);
        this.setOutput(true, "Boolean");
        this.setColour(260);
        this.setTooltip(Blockly.Msg.LISTS_ISEMPTY_TOOLTIP);
        this.setHelpUrl(Blockly.Msg.LISTS_ISEMPTY_HELPURL);
    }
};