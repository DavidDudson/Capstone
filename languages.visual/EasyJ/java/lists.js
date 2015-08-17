'use strict';

goog.provide('Blockly.Java.lists');

goog.require('Blockly.Java');


Blockly.Java['get_Length'] = function(block) {
  // Text value.
  var code = "Blockly.Java.quote_(block.getFieldValue('TEXT'));"
  alert(code);
  return [code, Blockly.Java.ORDER_ATOMIC];
};

