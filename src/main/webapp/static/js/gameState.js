
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
function alterBoatLiveGUI(sunkBoat, player, opp) {
    var playerBoat;
    switch (sunkBoat) {
        case 5:
            playerBoat = document.getElementById(player + "motherShip");
            break;
        case 4:
            playerBoat = document.getElementById(player + "carrier");
            break;
        case 3:
            playerBoat = document.getElementById(player + "destroyer");
            break;
        case 2:
            playerBoat = document.getElementById(player + "fighter");
            break;
    }
    var num = playerBoat.innerHTML;
    if(opp == "inc"){
        num++;
    }else{
        num--;
    }

    playerBoat.innerHTML = num;
}
