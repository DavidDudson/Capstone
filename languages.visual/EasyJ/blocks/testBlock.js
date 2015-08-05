Blockly.Blocks['create_list'] = {
  init: function() {
    this.appendDummyInput()
        .appendField("create list of type")
        .appendField(new Blockly.FieldDropdown([["String", "String"], ["Int", "Int"], ["Section", "Section"]]), "NAME");
    this.setOutput(true, ["Int", "String", "Section"]);
    this.setColour(20);
    this.setTooltip('create a new list of variable size');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['get_Length'] = {
  init: function() {
    this.appendValueInput("NAME")
        .appendField("get length of:");
    this.appendDummyInput();
    this.setOutput(true);
    this.setColour(20);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};  
Blockly.Blocks['get_item'] = {
  init: function() {
    this.appendValueInput("list_index")
        .setCheck("Number")
        .appendField("get item at index :");
    this.appendValueInput("list_input")
        .setCheck("Array")
        .appendField("in list:");
    this.appendDummyInput();
    this.setOutput(true, ["Int", "String", "Section"]);
    this.setColour(20);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};

Blockly.Blocks['remove_item'] = {
  init: function() {
    this.appendValueInput("list_index")
        .setCheck("Number")
        .appendField("remove item at index :");
    this.appendValueInput("list_input")
        .setCheck("Array")
        .appendField("in list:");
    this.appendDummyInput()
        .appendField("from list");
    this.setOutput(true);
    this.setColour(20);
    this.setTooltip('');
    this.setHelpUrl('http://www.example.com/');
  }
};