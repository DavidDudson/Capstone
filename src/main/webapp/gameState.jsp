<!DOCTYPE html>

<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" >
	<title>  </title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0" />
	<meta name="description" content="">
	<meta name="author" content="">
        <script src="static/js/jquery-1.7.2.min.js"></script>
        <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
        <script src="static/js/gameState.js"></script>
	<link rel="stylesheet" type="text/css" href="static/css/style.css">
	<link rel="stylesheet" type="text/css" href="static/css/grid.css">
        <script src="https://apis.google.com/js/api.js"></script>
        <script src="static/js/ace.js" charset="utf-8"></script>


</head>

<script lang="JavaScript">
    
            var json = '{"moves":[{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":7},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":3},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":4},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":5},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":5},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":4},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":6},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":7},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":2},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":0,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":9,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":1},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":2},"wasShip":true},{"sunk":[{"x":1,"y":1},{"x":1,"y":2},{"x":1,"y":3}],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":3},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":4},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":5},"wasShip":true},{"sunk":[{"x":1,"y":6},{"x":0,"y":6}],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":6},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":7},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":1,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":7},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":5},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":4},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":3},"wasShip":true},{"sunk":[{"x":8,"y":2},{"x":8,"y":3},{"x":8,"y":4},{"x":8,"y":5}],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":2},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":8,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":3},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":4},"wasShip":true},{"sunk":[{"x":2,"y":5},{"x":1,"y":5}],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":5},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":6},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":7},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":7},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":6},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":5},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":4},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":3},"wasShip":true},{"sunk":[{"x":7,"y":2},{"x":7,"y":3},{"x":7,"y":4},{"x":7,"y":5},{"x":7,"y":6}],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":2},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":2,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":7,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":7},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":3},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":5},"wasShip":false},{"sunk":[{"x":3,"y":4},{"x":2,"y":4},{"x":1,"y":4}],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":4},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":5},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":4},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":6},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":7},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":2},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":3,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":6,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":3},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":7},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":4},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":5},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":5},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":4},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":6},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":7},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":2},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":4,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":5,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":3},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":7},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":4},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":5},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":5},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":4},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":6},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":7},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":2},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":5,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":4,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":2},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":3},"wasShip":false},{"sunk":[{"x":6,"y":7},{"x":5,"y":7},{"x":4,"y":7},{"x":3,"y":7}],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":7},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":4},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":5},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":5},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":4},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":6},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":2},"wasShip":false},{"sunk":[{"x":6,"y":7},{"x":5,"y":7},{"x":4,"y":7},{"x":3,"y":7}],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":7},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":6,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":3,"y":0},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":9},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":8},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":2},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":3},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":4},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":5},"wasShip":true},{"sunk":[{"x":7,"y":2},{"x":7,"y":3},{"x":7,"y":4},{"x":7,"y":5},{"x":7,"y":6}],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":6},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":7},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":7},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":8},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":6},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":7,"y":9},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":5},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":4},"wasShip":true},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":3},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":0},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":2},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":1},"wasShip":false},{"sunk":[],"botId":"2","wasPlayer1":false,"coord":{"x":2,"y":1},"wasShip":false},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":2},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":3},"wasShip":true},{"sunk":[],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":4},"wasShip":true},{"sunk":[{"x":8,"y":2},{"x":8,"y":3},{"x":8,"y":4},{"x":8,"y":5}],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":5},"wasShip":true},{"sunk":[{"x":8,"y":2},{"x":8,"y":3},{"x":8,"y":4},{"x":8,"y":5}],"botId":"1","wasPlayer1":true,"coord":{"x":8,"y":5},"wasShip":true}]}';
            var jsonMoves = JSON.parse(json);
            
            
            var currentMoveIndex = 0;
            var bot2MoveCount = 0;
            var player1Move = true;
            var gameEnded = false;
            var gameStart = true;
            var gameSpeed = 500;
            var playGame = false;
            var myGame;
            function nextMove() {
                var currentMove = jsonMoves.moves[currentMoveIndex];
                console.log(currentMove);
                if(currentMove.wasPlayer1){
                    var posA = "a" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(posA);
                    if(currentMove.wasShip){
                        document.getElementById(posA).innerHTML += "<img src='static/images/hit.png'/>";
                        
                        var sunkElements = currentMove.sunk.length;
                        if (sunkElements > 1){
                            alterBoatLiveGUI(sunkElements, "p1");
                            
                        }
                        
                    }else{
                        document.getElementById(posA).innerHTML += "<img src='static/images/miss.png'/>";
                        
                    }
                    
                }else{
                    var posB = "b" + (currentMove.coord.x * 10 + currentMove.coord.y);
                    document.getElementById(posB);
                    if(currentMove.wasShip){
                        document.getElementById(posB).innerHTML += "<img src='static/images/hit.png'/>";
                        
                        var sunkElements = currentMove.sunk.length;
                        
                        if (sunkElements > 1){
                            alterBoatLiveGUI(sunkElements, "p2", "dec");
                            
                        }
                        
                    }else{
                        document.getElementById(posB).innerHTML += "<img src='static/images/miss.png'/>";
                        
                    }
                    
                    
                    
                    
                    
                }
                currentMoveIndex++;

            }
            function prevMove() {
                player1Move = !player1Move;
                if (player1Move) {
                    bot1MoveCount--;
                    var yCoordA = bot1Moves[bot1MoveCount][0];
                    var xCoordA = bot1Moves[bot1MoveCount][1];
                    var posA = "a" + (yCoordA * 10 + xCoordA);
                    document.getElementById(posA).style.backgroundColor = "#A6B8ED";
                    if (bot1Moves[bot1MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot1Moves[bot1MoveCount][2], "p1", "inc");
                    }
                } else if (bot2MoveCount !== 0) {
                    bot2MoveCount--;
                    var yCoordB = bot2Moves[bot2MoveCount][0];
                    var xCoordB = bot2Moves[bot2MoveCount][1];
                    var posB = "b" + (yCoordB * 10 + xCoordB);
                    document.getElementById(posB).style.backgroundColor = "#A6B8ED";
                    if (bot2Moves[bot2MoveCount][3] !== 0) {
                        alterBoatLiveGUI(bot2Moves[bot2MoveCount][2], "p2", "inc");
                    }
                }
                if (bot1MoveCount === 0 && player1Move) {
                    bot2MoveCount = 0;
                    var yCoordB = bot2Moves[bot2MoveCount][0];
                    var xCoordB = bot2Moves[bot2MoveCount][1];
                    var posB = "b" + (yCoordB * 10 + xCoordB);
                    document.getElementById(posB).style.backgroundColor = "#A6B8ED";
                    gameStart = true;
                }
            }
            function endGame() {
                while (!gameEnded) {
                    nextMove();
                }
            }
            function startOfGame() {
                while (!gameStart) {
                    prevMove();
                }
            }
            function playPause() {
                playGame = !playGame;
                if (playGame) {
                    myGame = setInterval(function () {
                        nextMove();
                    }, gameSpeed);
                } else {
                    clearInterval(myGame);
                }
                while(currentMoveIndex !== jsonMoves.moves.length){
                    nextMove();
                    
                }
            }

            function makeBotGame(){
                var url = "creategame_b2b";
                var bot1Id = "FirstSquareBot";
                var bot2Id = "LastSquareBot";
                var data = "" + bot1Id + "\n" + bot2Id + "\n";
                var jqxhr = $.ajax({
                    url : url,
                    type : "POST",
                    data : data
                }).done(function(src) {
                    // get results
                    var url2 = jqxhr.getResponseHeader("Location");
                    commons.debug("done creating game, results will be available at " + url2);

                    _fetchGame(url2);
                }).fail(function(jqXHR, textStatus, error) {
                    var errorLoc = jqXHR.getResponseHeader("Location-Error");
                    commons.handleError(textStatus,"cannot post game");
                    commons.notifyUser("Server Error",jqXHR.responseText);
                    console.log(jqXHR);
                    console.log(errorLoc);
                    console.log(error);
                });
            }
            function _fetchGame(url) {
                var game = $.getJSON(url)
                        .done(function (data) {
                            $("#gameloadingdialog").dialog("close");
                            $("#errordialog-content").html(data);
                        })
                        .fail(function (error) {
                            commons.debug("game execution error");
                        });
                document.getElementById("response").value=JSON.stringify(game);
                alert(game);
            }

        </script>

<body>



<!--Header-->
<div id="header">
		<div id="nav_container">
        
        
        <div class="container_12" style="padding:0;"> 
        
        
			<div id="nav_menu" class="left">
				<div id="logo" class="left">
					<a href="index.html"> Battle Spaceship </a>
				</div>
				<nav class="menu">
				<a class="toggle-nav" href="#">&#9776;</a>
					<ul class="list_inline active">
						<li> <a href=""> Editor </a> </li>
						<li> <a href=""> Test </a> </li>
						<li> <a href=""> Help </a> </li>
						<li> <a href=""> Community </a> </li>
						<li> <a href=""> Survey </a> </li>	
						<li> <a href=""> About </a> </li>
						
						<li> <div class="menu-on"> <a href=""> My Bots </a> </div> </li>
						<li> <div class="menu-on">  <a href=""> Built-in Bots </a> </div> </li>
						<li> <div class="menu-on">  <a href=""> Shared Bots </a> </div> </li>
					</ul>
					
				</nav>
			</div>	
			
				
			<div id="user" class="right">
				<ul class="list_inline">
					<li> <a class="username" href=""> Houlihan, Aidan </a> </li>
					<li> <a class="logout" href=""> Logout </a> </li>
				</ul>
			</div>
            
            
            </div>
			
			<div class="clear"> </div>
		</div>
	</div>


<!--End Header-->



<div class="container_12"> 
	

	<div id="content">
		<div id="sidebar_left" class="sidebar left">
			<div id="my_bots" class="sidebar_box">
            <div class="sidebar_box_inner">
            
				<div class="sidebar_head">
					My Bots
				</div>
				
				<div class="sidebar_content">
					<ul class="list_block">
						<li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
						<li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
						<li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
						<li class="bot"> <a href=""> Lorem Ipsum Dolor </a> </li>
						<li class="bot more"> <a href=""> more... </a> </li>
					</ul>
				</div>
                
                
              </div>  
			</div>
			
			<div id="built-in_bots" class="sidebar_box">
            <div class="sidebar_box_inner">
				<div class="sidebar_head">
					Built-in Bots
				</div>
				
				<div class="sidebar_content">
					<ul class="list_block">
						<li class="bot"> <a href=""> Cautious Built-in Bot </a> </li>
						<li class="bot"> <a href=""> Greedy Built-in Bot </a> </li>
						<li class="bot"> <a href=""> Smarter Bot </a> </li>
						<li class="bot"> <a href=""> Black Mamba </a> </li>
						<li class="bot more"> <a href=""> more... </a> </li>
					</ul>
				</div>
                
                </div>
			</div>
			
			<div id="shared_bots" class="sidebar_box">
            <div class="sidebar_box_inner">
				<div class="sidebar_head">
					Shared Bots
				</div>
				
				<div class="sidebar_content">
					<div id="search">
						<form method="">
							<input class="search_box" type="text" value="Search..." onBlur="if(this.value == '') { this.value='Search...'}" onFocus="if (this.value == 'Search...') {this.value=''}" />
                            
							<input class="search_btn" type="submit" value="" />
						</form>
					</div>
				
					
				</div>
			</div>
            </div>
		</div>
		
<div id="main_content">
                    <div id="player_one" class="left">
                        <ul>
                            <li> <b> Player 1 </b> </li>
                        </ul>

                        <ul class="grid_box">
                            <%for (int i = 0; i < 10; i++) {%>
                            <%for (int j = 0; j < 10; j++) {%>
                            <li id="a<%=i * 10 + j%>"></li>
                                <%}%>
                            </br>
                            <%}%>

                        </ul>


                    </div>

                    <div id="player_two" class="left">
                        <ul>
                            <li> <b> Player 2 </b> </li>
                        </ul>

                        <ul class="grid_box">
                            <%for (int i = 0; i < 10; i++) {%>
                            <%for (int j = 0; j < 10; j++) {%>
                            <li id="b<%=i * 10 + j%>"></li>
                                <%}%>
                            </br>
                            <%}%>

                        </ul>
                    </div>


                </div>

		
		<div id="sidebar_right" class="sidebar right">
        
			<div id="game_controls" class="sidebar_box">
            <div class="right-inner">
            
				<div class="sidebar_head">
					Game Controls
				</div>
				
				<div class="sidebar_content">
					<div id="controls">
						<div id="set_one">
							<ul class="list_inline">
								<li> <a class="fast_prev" href=""> </a> </li> 
								<li> <a class="prev" href=""> </a> </li> 
								<li> <a class="pause" onClick="playPause()"> </a> </li> 
								<li> <a class="forward" href=""> </a>  </li> 
								<li> <a class="fast_forward" href=""> </a> </li> 
							</ul>
						</div>
						
						<div id="set_two" class="slow_fast">
							<div class="left"> Slow </div>
							<section> <div id="slider" style="width: 175px"> </div></section>
							<div class="right"> Fast </div>
							<div class="clear"> </div>

							<script>
								$(function() {

									
									var slider  = $('#slider'),
										tooltip = $('.tooltip');

									
									tooltip.hide();

									
									slider.slider({
										
										range: "min",
										min: 1,
										value: 35,

										start: function(event,ui) {
											tooltip.fadeIn('fast');
										},

										
										slide: function(event, ui) { 

											var value  = slider.slider('value'),
												volume = $('.volume');

											tooltip.css('left', value).text(ui.value);  

											

										},

										stop: function(event,ui) {
											tooltip.fadeOut('fast');
										}
									});

								});
							</script>
						</div>
					</div>
					
					<div id="results">
						
                        
                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="results-list">
  <tr>
    <td width="48%">SPACESHIPS LEFT</td>
    <td width="26%" class="t-center">PLAYER 1 <br>
      <img src="static/images/layer1-ship1.png"></td>
    <td width="26%" class="t-center">PLAYER 2 <br>
      <img src="static/images/layer2-ship2.png"></td>
  </tr>
  <tr>
    <td>Alien Mother Ship<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
    <td id="p1motherShip" class="b-left">1</td>
    <td id="p2motherShip" class="b-left">1</td>
  </tr>
  <tr>
    <td>Alien Carrier Ship<br> <img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
    <td id="p1carrier" class="b-left">2</td>
    <td id="p2carrier" class="b-left">2</td>
  </tr>
  <tr>
    <td>Alien Destroyer<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
    <td id="p1destroyer" class="b-left">2</td>
    <td id="p2destroyer" class="b-left">2</td>
  </tr>
  <tr>
    <td>Alien Fighter Ship<br><img src="static/images/layer1-ship.png"><img src="static/images/layer1-ship.png"></td>
    <td id="p1fighter" class="b-left">2</td>
    <td id="p2fighter" class="b-left">2</td>
  </tr>
</table>
                        
                        
						
					</div>
				</div>
			</div>
            </div>
		</div>
		
		<div class="clear"> </div>
	</div>

	<div id="footer">
		

	</div>
</div>

<script>
jQuery(document).ready(function() {
    jQuery('.toggle-nav').click(function(e) {
        jQuery(this).toggleClass('active');
        jQuery('.menu ul').toggleClass('active');
 
        e.preventDefault();
    });
});
</script>

</body>
</html>