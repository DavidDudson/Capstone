angular.module("app")
    .factory("Game", GameService);

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

            genEmptyCell: function () {
                return [-1, -1, 0];
            },
            genShipList: function () {
                var shipList = [];
                [5, 4, 4, 3, 3, 2, 2].forEach(function (length) {
                    var cells = [];
                    for (var i = 0; i < length; i++) {
                        cells.push(game.genEmptyCell())
                    }
                    shipList.push(cells);
                });
                return shipList;
            },
            getShip: function (coords) {
                var cells = [];
                coords.forEach(function (coord) {
                    cells.push([coord.x, coord.y])
                });
                return cells;
            },
            spawnShipInShipList: function (coords) {
                var validIndex = [];
                for (var i = 0; i < game.player1ShipList.length; i++) {
                    if (game.player1ShipList[i].length == coords.length && game.player1ShipList[i][0][0] == -1) {
                        validIndex.push(i);
                    }
                }

                game.player1ShipList[validIndex[0]] = game.getShip(coords);
                game.player2ShipList[validIndex[0]] = game.getShip(coords);
            },
            generateShips: function () {
                game.player1ShipList = game.genShipList();
                game.player2ShipList = game.genShipList();
                var winnerP1 = game.moves[game.moves.length - 1].wasPlayer1;
                game.moves.forEach(function (move) {
                    if (winnerP1 && move.wasPlayer1 && move.sunk.length > 0) {
                        game.spawnShipInShipList(move.sunk);
                    } else if (!winnerP1 && !move.wasPlayer1 && move.sunk.length > 0) {
                        game.spawnShipInShipList(move.sunk);
                    }
                });
            },
            updateShip: function (coord, shipList, state) {
                shipList.forEach(function (ship) {
                    ship.forEach(function (cell) {
                        if (cell[0] == coord[0] && cell[1] == coord[1]) {
                            cell[2] = state;
                        }
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
                if (game.state) game.reset();
                game.start();
            },
            start: function () {
                game.inProgress = true;
                game.state = $interval(function () {
                    game.move_forward();
                }, game.speed, game.moves.length);
                game.move_forward();
            },
            stop: function () {
                $interval.cancel(game.state);
                game.inProgress = false;
                var winningPlayerName = game.moves[game.position - 1].wasPlayer1 ? game.player1.name : game.player2.name;
                notificationBar.showSuccessProgress("Game won by: " + winningPlayerName);
            },
            restart: function () {
                game.reset();
                if (!game.paused) {
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
                game.player1 = undefined;
                game.player2 = undefined;
                game.player1ShipList = [];
                game.player2ShipList = [];
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
                        if (move.wasPlayer1) {
                            game.updateShip([move.coord.x, move.coord.y], game.player1ShipList, 'static/images/hit.png')
                        } else {
                            game.updateShip([move.coord.x, move.coord.y], game.player2ShipList, 'static/images/hit.png')
                        }
                    } else {
                        document.getElementById(coordinate).innerHTML = "<img src='static/images/miss.png' height='100%' width='100%' ;top:0; left:0/>";
                    }
                    move.sunk.forEach(function (coord) {
                        if (move.wasPlayer1) {
                            game.updateShip([coord.x, coord.y], game.player1ShipList, 'static/images/sunk.png')
                        } else {
                            game.updateShip([coord.x, coord.y], game.player2ShipList, 'static/images/sunk.png')
                        }
                        coordinate = player + (coord.x * 10 + coord.y);
                        document.getElementById(coordinate).innerHTML = "<img src='static/images/sunk.png'  height='100%' width='100%';top:0; left:0/>"
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
                    move.sunk.forEach(function (coord) {
                        var sunkCoordinate = player + (coord.x * 10 + coord.y);
                        if (move.wasPlayer1) {
                            game.updateShip([coord.x, coord.y], game.player1ShipList, 'static/images/hit.png')
                        } else {
                            game.updateShip([coord.x, coord.y], game.player2ShipList, 'static/images/hit.png')
                        }
                        document.getElementById(sunkCoordinate).innerHTML = "<img src='static/images/hit.png' height='100%' width='100%'; top:0; left:0/>"
                    });
                    if (move.wasPlayer1) {
                        game.updateShip([move.coord.x, move.coord.y], game.player1ShipList, undefined)
                    } else {
                        game.updateShip([move.coord.x, move.coord.y], game.player2ShipList, undefined)
                    }
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
        game.player1ShipList = game.genShipList();
        game.player2ShipList = game.genShipList();
        return game;
    }
}