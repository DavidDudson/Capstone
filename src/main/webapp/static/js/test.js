var bot1 = null;
var bot2 = null;
var currentSelectedBot;

jsonMoves = {};
jsonUserBots = {};
jsonBuiltInBots = {};

var currentMoveIndex = 0;
var player1Move = true;
var gameEnded = false;
var gameStart = true;
var gameSpeed = 500;
var playGame = false;
var myGame;
var isRed = true;


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

function setBots(botID){



    if(bot1 === null){
        console.log(botID);
        bot1 = botID;
        $("#" + bot1).css("background-color", "red");

    } else if (bot2 === null){
        bot2 = botID;
        $("#" + bot2).css("background-color", "blue");
    }else{
        if(bot2 === botID){
            return;
        }
        $("#" + bot1).css("background-color", "#1a445b");
        bot1 = bot2;
        bot2 = botID;
        $("#" + bot2).css("background-color", "red");

        $("#" + bot2).css("background-color", isRed ? "red" : "blue");
        isRed = !isRed;
    }
}
function nextMove() {

    if (currentMoveIndex < jsonMoves.moves.length) {
        var currentMove = jsonMoves.moves[currentMoveIndex];
        if (currentMove.wasPlayer1) {
            var posA = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(posA);
            if (currentMove.wasShip) {
                document.getElementById(posA).innerHTML += "<img src='static/images/hit.png'/>";

                var sunkElements = currentMove.sunk.length;
                if (sunkElements > 1) {
                    alterBoatLiveGUI(sunkElements, "p1");
                }
            } else {
                document.getElementById(posA).innerHTML += "<img src='static/images/miss.png'/>";

            }

        } else {
            var posB = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(posB);
            if (currentMove.wasShip) {
                document.getElementById(posB).innerHTML += "<img src='static/images/hit.png'/>";

                var sunkElements = currentMove.sunk.length;

                if (sunkElements > 1) {
                    alterBoatLiveGUI(sunkElements, "p2", "dec");
                }
            } else {
                document.getElementById(posB).innerHTML += "<img src='static/images/miss.png'/>";

            }
        }
        currentMoveIndex++;
    }else{
        gameEnded = true;

    }
}
function prevMove() {
    if (currentMoveIndex > 0) {
        currentMoveIndex--;

        var currentMove = jsonMoves.moves[currentMoveIndex];
        var pos;
        var sunkElements = currentMove.sunk.length;
        if (currentMove.wasPlayer1) {
            pos = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(pos).innerHTML = "";
            if (sunkElements > 0) {
                alterBoatLiveGUI(sunkElements, "p1", "inc");

            }
        } else {
            pos = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(pos).innerHTML = "";
            if (sunkElements > 0) {
                alterBoatLiveGUI(sunkElements, "p2", "inc");

            }
        }

    }else{
        gameStart = true;

    }
}

function endGame() {
    while (currentMoveIndex !== jsonMoves.moves.length) {
        nextMove();
    }
}
function startGame() {
    while (currentMoveIndex !== 0) {
        prevMove();
    }
}
function playPause() {
    if(!playGame && gameStart){
        makeBotGame();
    }

    playGame = !playGame;
    if (playGame) {
        myGame = setInterval(function () {
            nextMove();
        }, gameSpeed);
    } else {
        clearInterval(myGame);
    }
}

function makeBotGame() {
    var url = "creategame_b2b";
    var bot1Id = "FirstSquareBot";
    var bot2Id = "LastSquareBot";
    var data = "" + bot1Id + "\n" + bot2Id + "\n";
    var jqxhr = $.ajax({
        url: url,
        type: "POST",
        data: data,
        dataType: "json",
        success : function () {
            $.getJSON(jqxhr.getResponseHeader("Location"), function(data){
                console.log(data);
                jsonMoves = data;



            });
        }
    });
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
                entry.setAttribute("onClick", "setBots('" + data.collection.items[i].id + "');");
                entry.className = "bot";
                document.getElementById("userBots").appendChild(entry);
            });
        }
    });



};
