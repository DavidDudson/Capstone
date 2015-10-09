Blockly.Blocks['check_state_of_coordinate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("there is")
        .appendField(new Blockly.FieldDropdown([["space", 0], ["Ship", 1]]), "state");
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

Blockly.Blocks['get_coordinate_at_pos'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("the position")
        .appendField(new Blockly.FieldDropdown([["Left", "left"], ["Right", "right"], ["Up", "up"], ["Down", "down"]]), "POSITION");
    this.appendValueInput("INPUT")
        .setCheck("Coordinate")
        .appendField("of");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
    this.setColour(230);
    this.setTooltip('get the coordinate neighbouring the given position');
    this.setHelpUrl('http://www.example.com/');
  }
};

//Blockly.Blocks['if_last_move_hit_aim_direction'] = {
//    init: function() {
//        this.appendDummyInput()
//            .appendField("if the last move was a hit, aim")
//            .appendField(new Blockly.FieldDropdown([["up", "up"], ["down", "down"], ["left", "left"], ["right", "right"]]), "direction");
//        this.setOutput(true, "Coordinate");
//        this.setColour(230);
//        this.setTooltip('if the last move was a hit, then get the coordinate next to that target');
//        this.setHelpUrl('http://www.example.com/');
//    }
//};

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
    this.setOutput(true, "Array");
    this.setColour(260);
    this.setTooltip('the neighbouring coordinates of the given position that can be hit');
    this.setHelpUrl('http://www.example.com/');
  }
};

//Blockly.Blocks['variable_define'] = {
//  init: function() {
//    this.appendDummyInput()
//        .appendField("define")
//        .appendField(new Blockly.FieldTextInput("name"), "NAME");
//    this.appendDummyInput()
//        .appendField("of type")
//        .appendField(new Blockly.FieldDropdown([["integer", "INT"], ["list", "LIST"], ["coord", "COORD"], ["boolean", "BOOL"]]), "TYPE");
//    this.setInputsInline(true);
//    this.setPreviousStatement(true, null);
//    this.setNextStatement(true, null);
//    this.setColour(330);
//    this.setTooltip('create a new variable of a certain type');
//    this.setHelpUrl('http://www.example.com/');
//  }
//};

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
            .appendField(new Blockly.FieldDropdown([["hit", "1"], ["miss", "2"], ["destroy", "3"]]), "STATE");
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
        this.appendDummyInput()
            .appendField("get coord X:")
            .appendField(new Blockly.FieldDropdown([["1", 0], ["2", 1], ["3", 2], ["4", 3], ["5", 4], ["6", 5], ["7", 6], ["8", 7], ["9", 8], ["10", 9]]), "XCOORD");
        this.appendDummyInput()
            .appendField("Y:")
            .appendField(new Blockly.FieldDropdown([["1", 0], ["2", 1], ["3", 2], ["4", 3], ["5", 4], ["6", 5], ["7", 6], ["8", 7], ["9", 8], ["10", 9]]), "YCOORD");
        this.setOutput(true, "Coordinate");
        this.setColour(330);
        this.setTooltip('get the coordinate at position x, y');
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
            .appendField(new Blockly.FieldDropdown([["space", "0"], ["hit", "1"], ["miss", "2"], ["sunk", "3"]]), "STATE");
        this.setOutput(true, "State");
        this.setColour(230);
        this.setTooltip('Possible states of a Coordinate');
        this.setHelpUrl('http://www.example.com/');
    }
};

Blockly.Blocks['states_of_neighbours'] = {
    init: function() {
        this.appendValueInput("Coordinate")
            .setCheck("Coordinate")
            .appendField("get status of neigbouring coords ");
        this.setInputsInline(true);
        this.setOutput(true, "Array");
        this.setColour(260);
        this.setTooltip('get a list of the neighbours if they are hit, miss, sunk or space (up, right, down, left)');
        this.setHelpUrl('http://www.example.com/');
    }
};