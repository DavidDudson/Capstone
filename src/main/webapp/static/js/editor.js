'use strict';


var currentBotID = null;
var workspace;
var reset = false;
var gameTimer;
var currentMoveIndex = 0;



function setupWorkspace() {
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

    workspace = Blockly.inject('blocklyDiv', blocklyConfig);
    Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));
}



function updateBot(){
    var botName = $("#" + currentBotID).html();
    var data = {};
    data.name = botName;
    data.language = 'JAVA';
    data.src = addXMLToSource();
    data = JSON.stringify(data);

    $.ajax({
        dataType: "json",
        url: 'http://localhost:8080/Capstone/bots/' + currentBotID,
        type: "PUT",
        data: data,
        success: function(){console.log("Bot update success");}, //TODO Show green bar
        failure: function(){console.log("Bot update failure");}  //TODO Show Red bar
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




function loadBotBlockly(){
    $.ajax({
        url: 'bots-src/' + currentBotID,
        type: "GET",
        success: function(data, textStatus, jqxhr){
            var xmlString = data.match(/<xml.*>/);
//            console.log(xmlString[0]);
            var blocklyXML = Blockly.Xml.textToDom(xmlString);
            console.log(blocklyXML);
            Blockly.Xml.domToWorkspace( workspace, blocklyXML );
            
            
        }, //TODO Show green bar
        failure: function(){console.log("get bot source failure");}  //TODO Show Red bar
    });
}

function getUserBots() {
    $.ajax({
        url: "userbots/__current_user",
        type: "GET",
        dataType: "json",
        success: addUserBotsToUI,
        failure: function(){console.log("getUserBots failed")}
    });
}

function deleteBotFromUI() {
    var deletedBot = document.getElementById(currentBotID);
    deletedBot.parentNode.removeChild(deletedBot);
    var botList = document.getElementById("userBots").getElementsByTagName("li");

    if (botList.length === 0) {
        $("#del").prop("disabled", true);
        $("#save").prop("disabled", true);
        $("#test").prop("disabled", true);
        $("#reset").prop("disabled", true);
        
    }
}

function deleteCurrentBot() {
    $.ajax({
        url: "delete/" + currentBotID,
        type: "DELETE",
        success: deleteBotFromUI(),
        failure: function(){console.log("deleteCurrentBot failed")}
    });
}

function selectBot(botID){
    if(currentBotID !== null){
        $("#" + currentBotID).css("background-color", "#1a445b");

    }
    currentBotID = botID;
//    loadBotBlockly();

    
    $("#" + currentBotID).css("background-color", "red");
    $("#del").prop("disabled", false);
    $("#save").prop("disabled", false);
    $("#test").prop("disabled", false);
    $("#reset").prop("disabled", false);

}

function saveBot(botName) {

    var data = {};
    data.name = botName;
    data.language = 'JAVA';
    data.src = addXMLToSource();
    data = JSON.stringify(data);

    return $.ajax({
        dataType: "json",
        url: 'http://localhost:8080/Capstone/bots',
        type: "POST",
        data: data,
        success: function(data){
            console.log("Build success");
            
            
            var entry = document.createElement('li');
            var textNode = document.createTextNode(botName);
            entry.appendChild(textNode);
            entry.setAttribute("id", data.botId);
            entry.setAttribute("value", data.botId);
            entry.setAttribute("onClick", "selectBot('" + data.botId + "');");
            entry.className = "bot";
            $("#userBots").prepend(entry);
            $("#del").prop("disabled", false);
            $("#save").prop("disabled", false);
            selectBot(data.botId);
            
            
            }, //TODO Show green bar
        failure: function(){console.log("Build failure");}  //TODO Show Red bar
    });
}


function createNewBot() {
    var botName = $('#botName').val();
    if (botName.length === 0) {
        alert("Bot must have name");
        return;
    }
    saveBot(botName);
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
function testBot(){
    var url = "creategame_b2b";
    var data = "" + currentBotID + "\n" + "FirstSquareBot" + "\n";
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        success : function(data, textStatus, request) {
            $.getJSON(request.getResponseHeader("Location"), function (json) {
                    runTestGame(json);
                
                
            });
        }
    });
    
}

function runTestGame(jsonMoves){
        $("#test").prop("disabled", true);


        gameTimer = setInterval(function() {

            if (currentMoveIndex < jsonMoves.moves.length) {
                var currentMove = jsonMoves.moves[currentMoveIndex];

                if (currentMove.wasPlayer1) {
                    var posA = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    if (currentMove.wasShip) {
                        document.getElementById(posA).innerHTML += "<img src='static/images/hit.png'/>";

                    } else {
                        document.getElementById(posA).innerHTML += "<img src='static/images/miss.png'/>";

                    }
                }
                currentMoveIndex++;
            }

    },250);
    
}  
    
function resetBot(){
    clearTimeout(gameTimer);
    
    for(var i =0; i < 100; i++){
        $("#a"+i).html("");
        
    }
    currentMoveIndex = 0;
    $("#test").prop("disabled", false);
    
}

function addXMLToSource(){
    var xmlSRC = Blockly.Xml.workspaceToDom(workspace);
    console.log(xmlSRC);
    var javaSRC = Blockly.Java.workspaceToCode(workspace, ["notests"]) + "// "+ Blockly.Xml.domToText(xmlSRC);
    return javaSRC;
}