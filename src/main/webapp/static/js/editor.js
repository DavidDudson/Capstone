'use strict';

var currentBotID = null;
var workspace;
//Blockly configuration, Set up the grid, specify always left to right,
var blocklyConfig = {
    toolbox: document.getElementById('toolbox'),
    rtl: false,
    comments: true,
    collapse: true,
    scrollbars: true,
    grid: {
        spacing: 25,
        length: 3,
        colour: '#ccc',
        snap: true
    }
};

function setupWorkspace() {
    workspace = Blockly.inject('blocklyDiv', blocklyConfig);

    Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));
    workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
}

function saveBot() {

    var data = {};
    data.name = currentBotID;
    data.language = 'JAVA';
    data.src = Blockly.Java.workspaceToCode(workspace, ["notests"]);
    data = JSON.stringify(data);

    $.ajax({
        dataType: "json",
        url: 'http://localhost:8080/Capstone/bots',
        type: "POST",
        data: data,
        success: console.log("Build success"), //TODO Show green bar
        failure: console.log("Build failure")  //TODO Show Red bar
    });
}

function addUserBotsToUI(data) {
    $.each(data.collection.items, function (i) {
        var entry = document.createElement('li');
        var textNode = document.createTextNode(data.collection.items[i].name);
        entry.appendChild(textNode);
        entry.setAttribute("id", data.collection.items[i].id);
        entry.setAttribute("value", data.collection.items[i].name);
        entry.setAttribute("onClick", "selectBot('" + data.collection.items[i].id + "');");
        entry.className = "bot";
        document.getElementById("userBots").appendChild(entry);
    });
}

function selectBot(botID){
    if(currentBotID !== null){
        $("#" + currentBotID).css("background-color", "#1a445b");

    }
    currentBotID = botID;
    $("#" + currentBotID).css("background-color", "red");
    $("#del").prop("disabled", false);
    $("#save").prop("disabled", false);

}
function getUserBots(callBackFunct) {
    $.ajax({
        url: "userbots/__current_user",
        type: "GET",
        dataType: "json",
        success: callBackFunct,
        failure: console.log("getUserBots failed")
    });
}

function deleteBotFromUI() {
    var deletedBot = document.getElementById(currentBotID);
    deletedBot.parentNode.removeChild(deletedBot);
    var botList = document.getElementById("userBots").getElementsByTagName("li");

    if (botList.length === 0) {
        $("#del").prop("disabled", true);
        $("#save").prop("disabled", true);
    }
}

function deleteCurrentBot() {
    $.ajax({
        url: "delete/" + currentBotID,
        type: "DELETE",
        success: deleteBotFromUI(),
        failure: console.log("deleteCurrentBot failed")
    });
}

function createNewBot() {
    currentBotID = $('#botName').val();

    if (currentBotID.length === 0) {
        alert("Bot must have name");
        return;
    }

    var newBot = saveBot();
    var entry = document.createElement('li');
    var textNode = document.createTextNode(currentBotID);

    entry.appendChild(textNode);
    entry.setAttribute("id", currentBotID);
    entry.setAttribute("value", currentBotID);
    entry.setAttribute("onClick", "selectBot('" + newBot + "');");
    entry.className = "bot";
    document.getElementById("userBots").appendChild(entry);

    $("#del").prop("disabled", false);
    $("#save").prop("disabled", false);
}

function getBotSource(id){
    var url = "/bot-data/" + id;
    
    $.ajax({
        url : url,
        type: "POST",
        datatype : "JSON",
        success : function (data){
            console.log(data);
            return data.src;
            
        }
        
        
    });
    
    
}