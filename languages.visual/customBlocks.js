/**
 *  This file is for any custom blocks needed.
 *  Just copy the JScript code form the block factory and paste them into this file
 */

Blockly.Blocks['gamestate'] = {
    init: function() {
        this.appendDummyInput()
            .appendField("Gamestate");
        this.setOutput(true, "Array");
        this.setColour(330);
        this.setTooltip('');
        this.setHelpUrl('http://www.example.com/');
    }
};