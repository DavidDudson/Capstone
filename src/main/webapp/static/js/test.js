jsonMoves = {};
jsonUserBots = {};
jsonBuiltInBots = {};

var currentMoveIndex = 0;
var gameEnded = false;
var gameStart = true;
var gameSpeed = 500;
var playGame = false;
var myGame;
var bot1 = null;
var bot2 = null;
var isRed = true;

function setBots(botID){
    console.log(botID);
    if(bot1 === null){
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
                document.getElementById(posA).innerHTML += "<img src='../images/hit.png'/>";

                var sunkElements = currentMove.sunk.length;
                if (sunkElements > 1) {
                    alterBoatLiveGUI(sunkElements, "p1");
                }
            } else {
                document.getElementById(posA).innerHTML += "<img src='../images/miss.png'/>";

            }

        } else {
            var posB = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(posB);
            if (currentMove.wasShip) {
                document.getElementById(posB).innerHTML += "<img src='../images/hit.png'/>";

                var sunkElements = currentMove.sunk.length;

                if (sunkElements > 1) {
                    alterBoatLiveGUI(sunkElements, "p2", "dec");
                }
            } else {
                document.getElementById(posB).innerHTML += "<img src='../images/miss.png'/>";

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
            if (sunkElements > 0)
                alterBoatLiveGUI(sunkElements, "p1", "inc");
        } else {
            pos = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
            document.getElementById(pos).innerHTML = "";
            if (sunkElements > 0)
                alterBoatLiveGUI(sunkElements, "p2", "inc");
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

function getGameMoves(jqxhr) {
    $.getJSON(jqxhr.getResponseHeader("Location"), function (data) {
        jsonMoves = data;
    });
}

function makeBotGame() {
    var jqxhr = $.ajax({
        url: "creategame_b2b",
        type: "POST",
        data: "" + bot1 + "\n" + bot2 + "\n",
        dataType: "json",
        success : getGameMoves
    });
}

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