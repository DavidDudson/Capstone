angular.module("app")
    .factory("Game", GameService);

function GameService($http, $interval) {

    return function (notificationBar) {

        //The modal for selecting create new bot options,
        var game = {
            moves: null,
            state: null,
            position: 0,
            images: {
                hit: 'static/images/hit.png',
                miss: 'static/images/miss.png',
                sunk: 'static/images/sunk.png'
            },
            create: function () {
                $http.post('creategame_b2b', "" + selectedBot.id + "\n" + selectedBot.id + "\n")
                    .success(function (data, status, headers) {
                        //TODO Track the current progress of the bot
                        game.getMoves(headers("Location"));
                    })
                    .error(function () {
                        console.error("Couldnt create bot to bot game");
                    });
            },
            getMoves: function (url) {
                $http.get(url)
                    .success(function (data) {
                        console.log(data);
                        game.moves = data.moves.filter(function (move) {
                            if (move.wasPlayer1) return move;
                        });
                        game.run();
                    })
                    .error(function () {
                        console.error("Could retrieve game moves");
                    })
            },
            run: function () {
                game.reset();
                game.state = $interval(function () {
                    var move = game.moves[game.position];
                    if (move) {
                        var posA = "a" + (move.coord.x * 10 + move.coord.y);
                        if (move.wasShip) {
                            document.getElementById(posA).innerHTML = "<img src='static/images/hit.png'/>";
                        } else {
                            document.getElementById(posA).innerHTML = "<img src='static/images/miss.png'/>";
                        }
                        move.sunk.forEach(function (move) {
                            posA = "a" + (move.x * 10 + move.y);
                            document.getElementById(posA).innerHTML = "<img src='static/images/sunk.png'/>"
                        });
                    }
                    game.position++;
                }, 100, game.moves.length);
            },
            restart: function () {
                game.reset();
                game.run();
            },
            reset: function () {
                $interval.cancel(game.state);
                game.state = null;

                for (var i = 0; i < 100; i++) {
                    $("#a" + i).html("");

                }
                game.position = 0;
            },
            hardReset: function () {
                game.reset();
                game.moves = null;
            }
        };
        return game;
    }
}