
function getSectColor(gameArrIndex) {
    var sectionColor;
    switch (gameArrIndex) {
        case 0:
            sectionColor = "#A6B8ED";
            break;
        case 1:
            sectionColor = "#FFA3A3";
            break;
        case 2:
            sectionColor = "#FFFF4D";
            break;
        case 3:
            sectionColor = "#FF9900";
            break;
        case 4:
            sectionColor = "#944DFF";
            break;
        case 5:
            sectionColor = "#94FF70";
            break;
        case 6:
            sectionColor = "#000000";
            break;
        case 7:
            sectionColor = "#FFFFFF";
            break;
    }
    return sectionColor;
}
function retHitOrMiss(gameArrIndex) {
    var newSectCol;
    switch (gameArrIndex) {
        case 0:
            newSectCol = "#FFFFFF";
            break;
        case 1:
            newSectCol = "#FF0000";
            break;
        case 2:
            newSectCol = "#FF0000";
            break;
        case 3:
            newSectCol = "#FF0000";
            break;
        case 4:
            newSectCol = "#FF0000";
            break;
        case 5:
            newSectCol = "#FF0000";
            break;
    }
    return newSectCol;
}
function alterBoatLiveGUI(sunkBoat, player) {
    var playerBoat;
    switch (sunkBoat) {
        case 2:
            playerBoat = document.getElementById(player + "Patrol");
            break;
        case 3:
            playerBoat = document.getElementById(player + "Destroyer");
            break;
        case 4:
            playerBoat = document.getElementById(player + "Battle");
            break;
        case 5:
            playerBoat = document.getElementById(player + "AirCarry");
            break;
    }
    var num = playerBoat.innerHTML;
    num--;
    playerBoat.innerHTML = num;
}
function nextMove() {
    if (player1Move) {
        var yCoordA = bot1Moves[bot1MoveCount][0];
        var xCoordA = bot1Moves[bot1MoveCount][1];
        var posA = "a" + (yCoordA * 10 + xCoordA);
        document.getElementById(posA).style.backgroundColor = retHitOrMiss(gameArrayA[yCoordA][xCoordA]);
        if (bot1Moves[bot1MoveCount][3] !== 0) {
            alterBoatLiveGUI(bot1Moves[bot1MoveCount][3], "p1");
        }
        bot1MoveCount++;
    } else {
        var yCoordB = bot2Moves[bot2MoveCount][0];
        var xCoordB = bot2Moves[bot2MoveCount][1];
        var posB = "b" + (yCoordB * 10 + xCoordB);
        document.getElementById(posB).style.backgroundColor = retHitOrMiss(gameArrayB[yCoordB][xCoordB]);
        if (bot2Moves[bot2MoveCount][3] !== 0) {
            alterBoatLiveGUI(bot2Moves[bot2MoveCount][3], "p2");
        }
        bot2MoveCount++;
    }
    if (bot1Moves[bot1MoveCount][0] === -1) {
        document.getElementById("nextButton").disabled = true;
        alert("bot1 wins");
    }
    if (bot2Moves[bot2MoveCount][0] === -1) {
        document.getElementById("nextButton").disabled = true;
        alert("bot2 wins");
    }
    if (bot1MoveCount !== 0) {
        document.getElementById("prevMove").disabled = false;
    }
    player1Move = !player1Move;
}
function prevMove() {
    player1Move = !player1Move;
    if (bot1Moves[bot1MoveCount][0] !== -1) {
        document.getElementById("nextButton").disabled = false;
    }
    if (bot1MoveCount === 0) {
        document.getElementById("prevButton").disabled = true;
    }
    else if (player1Move) {
        bot1MoveCount--;
        var yCoordA = bot1Moves[bot1MoveCount][0];
        var xCoordA = bot1Moves[bot1MoveCount][1];
        var posA = "a" + (yCoordA * 10 + xCoordA);
        document.getElementById(posA).style.backgroundColor = getSectColor(bot1Moves[bot1MoveCount][2]);
        if (bot1Moves[bot1MoveCount][2] !== 0) {
            alterBoatLiveGUI(bot1Moves[bot1MoveCount][2], "p1");
        }
    } else if (bot2MoveCount !== 0) {
        bot2MoveCount--;
        var yCoordB = bot2Moves[bot2MoveCount][0];
        var xCoordB = bot2Moves[bot2MoveCount][1];
        var posB = "b" + (yCoordB * 10 + xCoordB);
        document.getElementById(posB).style.backgroundColor = getSectColor(bot2Moves[bot2MoveCount][2]);
        if (bot2Moves[bot2MoveCount][2] !== 0) {
            alterBoatLiveGUI(bot2Moves[bot2MoveCount][2], "p2");
        }
    }
}
function endGame() {
    while (!document.getElementById("nextButton").disabled) {
        nextMove();
    }
}
function startOfGame() {
    while (!document.getElementById("prevMove").disabled) {
        prevMove();
    }
}