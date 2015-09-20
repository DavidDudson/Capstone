'use strict';


var currentBotName = null;
var currentJavaCode = null;
var selectedBot = null;


var workspace = null;
function start() {
    var workspace = Blockly.inject('blocklyDiv',
        {
            toolbox: document.getElementById('toolbox'),
            rtl: false,
            comments: true,
            collapse: true,
            scrollbars: true,
            grid: {
                spacing: 25,
                length:3,
                colour: '#ccc',
                snap:true
            }
        });
    Blockly.Xml.domToWorkspace(workspace, document.getElementById('initialBlocklyState'));
    workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];
}

function toCode() {
    currentJavaCode = Blockly.Java.workspaceToCode(workspace, ["notests"]);
}

function selectBot(botID){
    if(selectedBot !== null){
        $("#" + selectedBot).css("background-color", "#1a445b");

    }
    selectedBot = botID;
    $("#" + selectedBot).css("background-color", "red");
    $("#del").prop("disabled", false);
    $("#save").prop("disabled", false);

}


function compileAndSave() {

    var data = 	{};
    data.name = currentBotName;
    data.language = 'JAVA';
    data.src = currentJavaCode;
    data = JSON.stringify(data);
    var url = 'http://localhost:8080/Capstone/bots';

    $.ajax({
        dataType: "json",
        url : url,
        type : "POST",
        data : data
    }).done(function(src) {


        return src.botId;
    }).fail(function(jqXHR, error) {
        console.log(src.buildStatusUrl);
        var errorLoc = jqXHR.getResponseHeader("Location-Error");
        console.log("build error is available at " + errorLoc);
        _fetchGame('http://localhost:8080' + errorLoc);
    });
}



function toXml() {
    var output = document.getElementById('importExport');
    var xml = Blockly.Xml.workspaceToDom(workspace);
    output.value = Blockly.Xml.domToPrettyText(xml);
}

var commons = {
    DEBUG : true,
    handleError : function(textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("ERROR: " + err);
    },
    warn : function(message) {
        console.log("WARN: " + message);
    },
    debug : function(message) {
        if (this.DEBUG) {
            console.log("DEBUG: " + message);
        }
    },
    notifyUser : function(title,message) {
        $("#errordialog-content").html(message);
        $("#errordialog").dialog({title: title,text: message,modal: true,width:350,height:300});
    },
    formatServerError : function(error,maxStackTraceSize) {
        var botEncounteringError = (error.winner==1)?2:1;
        var errorInfo = "<b>A server error occured during execution of bot " + botEncounteringError + "</b><br/>" +
            "error type: " + error.type +"<br/>";
        if (error.message) errorInfo = errorInfo + "message: " + error.message +"<br/>";
        errorInfo = errorInfo+"</hr>" + "stacktrace:<br/>";
        var stackTraceSize = Math.min(error.stacktrace.length, maxStackTraceSize);
        for (var i=0;i<stackTraceSize;i++) {
            errorInfo = errorInfo + error.stacktrace[i] + "<br/>";
        }
        return errorInfo;
    }
};

function getBots() {

    var userBotsURL = "userbots/__current_user";
    var buildBotsURL = "builtinbots";
    $.ajax({
        url: userBotsURL,
        type: "GET",
        dataType: "json",
        success : function (data) {
            $.each(data.collection.items, function(i, items){
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
    });
};

function deleteBot(){
    var deleteBotURl = "delete/" + selectedBot ;
    var buildBotsURL = "builtinbots";
    $.ajax({
        url: deleteBotURl,
        type: "DELETE",
        success : function (){
            var deletedBot = document.getElementById(selectedBot);
            deletedBot.parentNode.removeChild(deletedBot);
            alert("bot deleted");
            var botList = document.getElementById("userBots").getElementsByTagName("li");

            if(botList.length === 0){
                $("#del").prop("disabled", true);
                $("#save").prop("disabled", true);

            }

        }
    });
}




function createNewBot(){
    currentBotName = $('#botName').val();

    var botList = document.getElementById("userBots").getElementsByTagName("li");

    if(currentBotName.length === 0){
        alert("bot must have name");
        return;
    }
    toCode();

    var newBot = compileAndSave();
    console.log(newBot);
    var entry = document.createElement('li');
    var textNode = document.createTextNode(currentBotName);

    entry.appendChild(textNode);
    entry.setAttribute("id", newBot);
    entry.setAttribute("value", currentBotName);
    entry.setAttribute("onClick", "selectBot('" + newBot + "');");
    entry.className = "bot";
    document.getElementById("userBots").appendChild(entry);

    $("#del").prop("disabled", false);
    $("#save").prop("disabled", false);

}