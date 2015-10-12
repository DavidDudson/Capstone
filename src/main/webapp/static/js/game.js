angular.module("app")
    .factory("Game", GameService);

function adjustHeight() {
    var myWidth = jQuery('.square-element').width();
    var myString = myWidth + 'px';
    jQuery('.square-element').css('height', myString);
    return myHeight;
}

// calls adjustHeight on window load
jQuery(window).load(function() {
    adjustHeight();
});

// calls adjustHeight anytime the browser window is resized
jQuery(window).resize(function() {
    adjustHeight();
});

function GameService($http, $interval, Ship) {

    return function (notificationBar) {

        //The modal for selecting create new bot options,
        var game = {
            moves: undefined,
            state: undefined,
            position: 0,
            inProgress: false,
            paused: false,
            speed: 100,
            player1: undefined,
            player2: undefined,
            player1ShipList: [],
            player2ShipList: [],
            player1ShipMap: {},
            player2ShipMap: {},
            generateShips: function () {
                game.moves.forEach(function (move) {
                    if (move.sunk !== []) {
                        if (move.wasPlayer1) {
                            game.player1ShipList.push(Ship(move.sunk))
                        } else {
                            game.player2ShipList.push(Ship(move.sunk))
                        }
                    }
                });
                game.generateShipMap(game.player1ShipList, game.player1ShipMap);
                game.generateShipMap(game.player2ShipList, game.player2ShipMap);
            },
            generateShipMap: function (shipList, shipMap) {
                shipList.forEach(function (ship) {
                    ship.coordinateList.forEach(function (coordinate) {
                        shipMap[coordinate] = ship
                    })
                })
            },
            create: function (bot1, bot2, testing) {
                if (notificationBar.active && notificationBar.type == '') return;
                game.player1 = bot1;
                game.player2 = bot2;
                notificationBar.showProgress("Creating test game");
                var data = testing ? "" + bot1.id + "\n" + bot1.id + "\n" : "" + bot1.id + "\n" + bot2.id + "\n";
                $http.post('creategame_b2b', data)
                    .success(function (data, status, headers) {
                        game.getMoves(headers("Location"), testing);
                    })
                    .error(function () {
                        notificationBar.showError("Couldnt create bot to bot game");
                    });
            },
            getMoves: function (url, testing) {
                $http.get(url)
                    .success(function (data) {
                        notificationBar.showSuccess("Test game creation success");
                        if (testing) {
                            game.moves = data.moves.filter(function (move) {
                                if (move.wasPlayer1) return move;
                            });
                        } else {
                            game.moves = data.moves
                        }
                        game.generateShips();
                        game.run();
                    })
                    .error(function () {
                        notificationBar.showError("Game Moves could not be retrieved")
                    })
            },
            run: function () {
                if(game.state) game.reset();
                game.start();
            },
            start: function () {
                game.inProgress = true;
                game.state = $interval(function () {
                    game.move_forward();
                }, game.speed, game.moves.length);
                game.move_forward();
            },
            stop: function(){
                $interval.cancel(game.state);
                game.inProgress = false;
                var winninPlayerName = game.moves[game.position - 1].wasPlayer1 ? game.player1.name : game.player2.name;
                notificationBar.showSuccessProgress("Game won by: " + winninPlayerName);
            },
            restart: function () {
                game.reset();
                if(!game.paused){
                    game.start()
                }
            },
            reset: function () {
                game.state = null;
                game.paused = false;

                for (var i = 0; i < 100; i++) {
                    $("#a" + i).html("");
                    $("#b" + i).html("");

                }
                game.position = 0;
            },
            hardReset: function () {
                game.reset();
                game.moves = null;
            },
            step_forward: function () {
                game.pause();
                game.move_forward();
            },
            move_forward: function () {
                var move = game.moves[game.position];
                if (move) {
                    var player = move.wasPlayer1 ? "a" : "b";
                    var coordinate = player + (move.coord.x * 10 + move.coord.y);
                    if (move.wasShip) {
                        document.getElementById(coordinate).innerHTML = "<img src='static/images/hit.png' height='100%' width='100%'; top:0; left:0/>";
                    } else {
                        document.getElementById(coordinate).innerHTML = "<img src='static/images/miss.png' height='100%' width='100%' ;top:0; left:0/>";
                    }
                    move.sunk.forEach(function (move) {
                        coordinate = player + (move.x * 10 + move.y);
                        document.getElementById(coordinate).innerHTML = "<img src='static/images/sunk.png' height='100%' width='100%';top:0; left:0/>"
                    });
                } else {
                 game.stop()
                }
                game.position++;
            },
            step_backward: function () {
                game.pause();
                game.position--;
                var move = game.moves[game.position];
                if (move) {
                    var player = move.wasPlayer1 ? "a" : "b";
                    var coordinate = player + (move.coord.x * 10 + move.coord.y);
                    move.sunk.forEach(function (move) {
                        var sunkCoordinate = player + (move.x * 10 + move.y);
                        document.getElementById(sunkCoordinate).innerHTML = "<img src='static/images/hit.png'/>"
                    });
                    document.getElementById(coordinate).innerHTML = "";
                    game.inProgress = true;
                }
            },
            play_pause: function (botSelector, testing) {
                if (!game.inProgress) {
                    game.inProgress = true;
                    if (testing) {
                        game.create(botSelector.bots[0], botSelector.bots[0], testing)
                    } else {
                        game.create(botSelector.bots[0], botSelector.bots[1], testing)
                    }
                } else {
                    game.paused ? game.unpause() : game.pause()
                }
            },
            pause: function () {
                game.paused = true;
                $interval.cancel(game.state)
            },
            unpause: function () {
                game.paused = false;
                game.start();
            },
            end: function () {
                while (game.inProgress) {
                    game.move_forward();
                }
            }
        };
        return game;
    }
}