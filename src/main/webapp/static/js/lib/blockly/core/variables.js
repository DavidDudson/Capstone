/**
 * @license
 * Visual Blocks Editor
 *
 * Copyright 2012 Google Inc.
 * https://developers.google.com/blockly/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Utility functions for handling variables.
 * @author fraser@google.com (Neil Fraser)
 */
'use strict';

goog.provide('Blockly.Variables');

// TODO(scr): Fix circular dependencies
// goog.require('Blockly.Block');
goog.require('Blockly.Workspace');
goog.require('goog.string');


/**
 * Category to separate variable names from procedures and generated functions.
 */
Blockly.Variables.NAME_TYPE = 'VARIABLE';

/**
 * Find all user-created variables.
 * @param {!Blockly.Block|!Blockly.Workspace} root Root block or workspace.
 * @return {!Array.<string>} Array of variable names.
 */
Blockly.Variables.allVariables = function(root) {
  var blocks;
  if (root.getDescendants) {
    // Root is Block.
    blocks = root.getDescendants();
  } else if (root.getAllBlocks) {
    // Root is Workspace.
    blocks = root.getAllBlocks();
  } else {
    throw 'Not Block or Workspace: ' + root;
  }
  var variableHash = Object.create(null);
  // Iterate through every block and add each variable to the hash.
  for (var x = 0; x < blocks.length; x++) {
    if (blocks[x].getVars) {
      var blockVariables = blocks[x].getVars();
      for (var y = 0; y < blockVariables.length; y++) {
        var varName = blockVariables[y];
        // Variable name may be null if the block is only half-built.
        if (varName) {
          variableHash[varName.toLowerCase()] = varName;
        }
      }
    }
  }
  // Flatten the hash into a list.
  var variableList = [];
  for (var name in variableHash) {
    variableList.push(variableHash[name]);
  }
  return variableList;
};

Blockly.Variables.Intersection = function(arr1, arr2) {
  var result = goog.array.filter(arr1, function(val, index, a1) {
    return goog.array.contains(arr2, val);
  });
  return result;
}
/**
 * Find all user-created variables with their types.
 * @param {!Blockly.Block|!Blockly.Workspace} root Root block or workspace.
 * @return {!} Hash of variable names and their types.
 */
Blockly.Variables.allVariablesTypes = function(root) {
  var blocks;
  if (root.getDescendants) {
    // Root is Block.
    blocks = root.getDescendants();
  } else if (root.getAllBlocks) {
    // Root is Workspace.
    blocks = root.getAllBlocks();
  } else {
    throw 'Not Block or Workspace: ' + root;
  }
  var variableHash = Object.create(null);
  var variableTypes = Object.create(null);
  // Iterate through every block and add each variable to the hash.
  for (var x = 0; x < blocks.length; x++) {
    var func = blocks[x].getVarsTypes;
    if (func) {
        var temp = '';
        for (var t in variableHash) {
          temp += t + ':'+variableHash[t]+',';
        }
      var blockVariablesTypes = func.call(blocks[x]);
      for (var key in blockVariablesTypes) {
        if (blockVariablesTypes.hasOwnProperty(key)) {
          // For purposes of types, Colours are strings.  We want to convert
          // them all to strings
          for (var slot = 0; slot < blockVariablesTypes[key].length; slot++) {
            if (blockVariablesTypes[key][slot] === 'Colour') {
              blockVariablesTypes[key][slot] = 'String';
            }
          }
          if (typeof variableHash[key] === 'undefined') {
            variableHash[key] = blockVariablesTypes[key];
          } else {
            var intersect = Blockly.Variables.Intersection(
                      variableHash[key], blockVariablesTypes[key]);
            if (goog.array.isEmpty(intersect)) {
              intersect = ['Var'];
            }
            console.log('Block:'+ blocks[x].type + '.'+blocks[x].id+
            ' For: '+key+' was:'+variableHash[key]+' got:'+
            blockVariablesTypes[key]+' result='+intersect);
            variableHash[key] = intersect;
          }
        }
      }
    }
  }
  //
  // We now have all of the variables.  Next we want to go through and flatten
  // the types into what we know and what we don't know.  There will be several
  // options here.
  //   1) We have a single type for the variable.  This is the easy case.  We
  //      take that type.
  //   2) We have no type information.  For these we will assume that the
  //      type will be a scalar.
  //   3) We have more than one type, but the types are all mutable (i.e. int
  //      vs float or JSON vs Array).  For that we use the superior type
  //   4) We have a conflict between types.  For this we will take the superior
  //      type and then tell all of the functions that there is a conflict on
  //      that variable which needs to be resolved.
  var variableList = Object.create(null);;
  for (var key in variableHash) {
    if (variableHash[key].length === 0) {
      variableList[key] = 'Number';
    }
    else if (variableHash[key].length === 1) {
      variableList[key] = variableHash[key][0];
    }
    else if (goog.array.indexOf(variableHash[key], 'Object') !== -1) {
      variableList[key] = variableHash[key][0];
    } else {
      // Conflict of types and JSON isn't one of them. For now we will
      // return the first one we found
      console.log('Multiple types found for '+key+' '+variableHash[key]);
      variableList[key] = variableHash[key][0];
    }

  }
  return variableList;
};

/**
 * Find all instances of the specified variable and rename them.
 * @param {string} oldName Variable to rename.
 * @param {string} newName New variable name.
 * @param {!Blockly.Workspace} workspace Workspace rename variables in.
 */
Blockly.Variables.renameVariable = function(oldName, newName, workspace) {
  var blocks = workspace.getAllBlocks();
  // Iterate through every block.
  for (var i = 0; i < blocks.length; i++) {
    if (blocks[i].renameVar) {
      blocks[i].renameVar(oldName, newName);
    }
  }
};

/**
 * Construct the blocks required by the flyout for the variable category.
 * @param {!Array.<!Blockly.Block>} blocks List of blocks to show.
 * @param {!Array.<number>} gaps List of widths between blocks.
 * @param {number} margin Standard margin width for calculating gaps.
 * @param {!Blockly.Workspace} workspace The flyout's workspace.
 */
Blockly.Variables.flyoutCategory = function(blocks, gaps, margin, workspace) {
  var variableList = Blockly.Variables.allVariables(workspace.targetWorkspace);
  variableList.sort(goog.string.caseInsensitiveCompare);
  // In addition to the user's variables, we also want to display the default
  // variable name at the top.  We also don't want this duplicated if the
  // user has created a variable of the same name.
  variableList.unshift(null);
  var defaultVariable = undefined;
  for (var i = 0; i < variableList.length; i++) {
    if (variableList[i] === defaultVariable) {
      continue;
    }
    var getBlock = Blockly.Blocks['variables_get'] ?
        Blockly.Block.obtain(workspace, 'variables_get') : null;
    getBlock && getBlock.initSvg();
    var setBlock = Blockly.Blocks['variables_set'] ?
        Blockly.Block.obtain(workspace, 'variables_set') : null;
    setBlock && setBlock.initSvg();
    if (variableList[i] === null) {
      defaultVariable = (getBlock || setBlock).getVars()[0];
    } else {
      getBlock && getBlock.setFieldValue(variableList[i], 'VAR');
      setBlock && setBlock.setFieldValue(variableList[i], 'VAR');
    }
    setBlock && blocks.push(setBlock);
    getBlock && blocks.push(getBlock);
    if (getBlock && setBlock) {
      gaps.push(margin, margin * 3);
    } else {
      gaps.push(margin * 2);
    }
  }
};

/**
 * Return a new variable name that is not yet being used. This will try to
 * generate single letter variable names in the range 'i' to 'z' to start with.
 * If no unique name is located it will try 'i' to 'z', 'a' to 'h',
 * then 'i2' to 'z2' etc.  Skip 'l'.
 * @param {!Blockly.Workspace} workspace The workspace to be unique in.
 * @return {string} New variable name.
 */
Blockly.Variables.generateUniqueName = function(workspace) {
  var variableList = Blockly.Variables.allVariables(workspace);
  var newName = '';
  if (variableList.length) {
    var nameSuffix = 1;
    var letters = 'ijkmnopqrstuvwxyzabcdefgh';  // No 'l'.
    var letterIndex = 0;
    var potName = letters.charAt(letterIndex);
    while (!newName) {
      var inUse = false;
      for (var i = 0; i < variableList.length; i++) {
        if (variableList[i].toLowerCase() == potName) {
          // This potential name is already used.
          inUse = true;
          break;
        }
      }
      if (inUse) {
        // Try the next potential name.
        letterIndex++;
        if (letterIndex == letters.length) {
          // Reached the end of the character sequence so back to 'i'.
          // a new suffix.
          letterIndex = 0;
          nameSuffix++;
        }
        potName = letters.charAt(letterIndex);
        if (nameSuffix > 1) {
          potName += nameSuffix;
        }
      } else {
        // We can use the current potential name.
        newName = potName;
      }
    }
  } else {
    newName = 'i';
  }
  return newName;
};

/**
 * Find a context for a variable.  If it is inside a procedure, we want to have
 * The name of the containing procedure.  If this is a global variable then
 * we want to return a null
 * @param {!Blockly.Block} block Block to get context for
 * @param {string} name string of the name to look for.
 * @return {string} Context of the procedure (string) or null)
*/
Blockly.Variables.getLocalContext = function(block,name) {
  do {
    if (block.getProcedureDef) {
      var tuple = block.getProcedureDef.call(block);
      var params = tuple[1];
      if (name === null) {
        return tuple[0]+'.';
      }
      for(var i = 0; i < params.length; i++) {
        if (params[i]['name'] === name) {
          return tuple[0]+'.';
        }
      }
      break;
    } else if (block.type === 'initialize_variable' &&
        block.getFieldValue('VAR') === name ) {
      // We found an initialize_variable block, so now we want to go through
      // and continue until we find the containing procedure (if any)
      name = null;
    }
    block = block.getParent();
  } while (block);
  return null;
};
