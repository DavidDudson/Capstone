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
        .setCheck("Coordinate")
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

Blockly.Blocks['get_step_coord']= {
  init: function() {
    this.appendValueInput("STEP")
        .setCheck("Number");
    this.appendValueInput("Coordinate")
        .appendField("positions away from ")
        .setCheck("Coordinate");
    this.setInputsInline(true);
    this.setOutput(true, "Coordinate");
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

Blockly.Blocks['lists_concat_with'] = {
    /**
     * Block for creating a list with any number of elements of any type.
     * @this Blockly.Block
     */
    init: function() {
        this.setHelpUrl(Blockly.Msg.LISTS_CREATE_WITH_HELPURL);
        this.setColour(260);
        if (this.workspace.options.useMutators) {
            this.setMutator(new Blockly.Mutator(['lists_create_with_item']));
        } else {
            this.appendAddSubGroup(Blockly.Msg.LISTS_CREATE_WITH_INPUT_WITH, 'items',
                null,
                Blockly.Msg.LISTS_CREATE_EMPTY_TITLE);
        }
        this.itemCount_ = 1;
        this.updateShape_();
        this.setOutput(true, 'Array');
        this.setTooltip(Blockly.Msg.LISTS_CREATE_WITH_TOOLTIP);
    },
    getAddSubName: function(name,pos) {
        return 'ADD'+pos;
    },
    /**
     * Create XML to represent list inputs.
     * @return {!Element} XML storage element.
     * @this Blockly.Block
     */
    mutationToDom: function() {
        var container = document.createElement('mutation');
        container.setAttribute('items', this.itemCount_);
        return container;
    },
    /**
     * Parse XML to restore the list inputs.
     * @param {!Element} xmlElement XML storage element.
     * @this Blockly.Block
     */
    domToMutation: function(xmlElement) {
        this.itemCount_ = parseInt(xmlElement.getAttribute('items'), 10);
        this.updateShape_();
    },
    /**
     * Populate the mutator's dialog with this block's components.
     * @param {!Blockly.Workspace} workspace Mutator's workspace.
     * @return {!Blockly.Block} Root block in mutator.
     * @this Blockly.Block
     */
    decompose: function(workspace) {
        var containerBlock =
            Blockly.Block.obtain(workspace, 'lists_create_with_container');
        containerBlock.initSvg();
        var connection = containerBlock.getInput('STACK').connection;
        for (var i = 0; i < this.itemCount_; i++) {
            var itemBlock = Blockly.Block.obtain(workspace, 'lists_create_with_item');
            itemBlock.initSvg();
            connection.connect(itemBlock.previousConnection);
            connection = itemBlock.nextConnection;
        }
        return containerBlock;
    },
    /**
     * Reconfigure this block based on the mutator dialog's components.
     * @param {!Blockly.Block} containerBlock Root block in mutator.
     * @this Blockly.Block
     */
    compose: function(containerBlock) {
        var itemBlock = containerBlock.getInputTargetBlock('STACK');
        // Count number of inputs.
        var connections = [];
        while (itemBlock) {
            connections.push(itemBlock.valueConnection_);
            itemBlock = itemBlock.nextConnection &&
                itemBlock.nextConnection.targetBlock();
        }
        this.itemCount_ = connections.length;
        this.updateShape_();
        // Reconnect any child blocks.
        for (var i = 0; i < this.itemCount_; i++) {
            if (connections[i]) {
                this.getInput('ADD' + i).connection.connect(connections[i]);
            }
        }
    },
    /**
     * Store pointers to any connected child blocks.
     * @param {!Blockly.Block} containerBlock Root block in mutator.
     * @this Blockly.Block
     */
    saveConnections: function(containerBlock) {
        var itemBlock = containerBlock.getInputTargetBlock('STACK');
        var i = 0;
        while (itemBlock) {
            var input = this.getInput('ADD' + i);
            itemBlock.valueConnection_ = input && input.connection.targetConnection;
            i++;
            itemBlock = itemBlock.nextConnection &&
                itemBlock.nextConnection.targetBlock();
        }
    },
    /**
     * Modify this block to have the correct number of inputs.
     * @private
     * @this Blockly.Block
     */
    updateShape_: function() {
        // Delete everything.
        if (this.getInput('EMPTY')) {
            this.removeInput('EMPTY');
        } else {
            var i = 0;
            while (this.getInput('ADD' + i)) {
                this.removeInput('ADD' + i);
                i++;
            }
        }
        // Rebuild block.
        if (this.itemCount_ == 0) {
            this.appendDummyInput('EMPTY')
                .appendField(Blockly.Msg.LISTS_CREATE_EMPTY_TITLE);
        } else {
            for (var i = 0; i < this.itemCount_; i++) {
                var input = this.appendValueInput('ADD' + i);
                if (i == 0) {
                    input.appendField(Blockly.Msg.LISTS_CREATE_WITH_INPUT_WITH);
                }
            }
        }
    },
    typeblock: [
        { entry: Blockly.Msg.LISTS_CREATE_WITH_TYPEBLOCK,
            mutatorAttributes: { items: 2 } }
//      ,{ entry: Blockly.Msg.LISTS_CREATE_EMPTY_TYPEBLOCK,
//        mutatorAttributes: { items: 0 } }
    ]
};