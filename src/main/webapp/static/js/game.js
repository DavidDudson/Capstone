angular.module("app")
    .factory("Game", GameService);

function GameService($http, $interval) {

    return function (notificationBar) {

        //The modal for selecting create new bot options,
        var game = {
            moves: null,
            state: null,
            position: 0,
            create: function (bot1, bot2, testing) {
                if(notificationBar.active) return;
                notificationBar.reset();
                notificationBar.active = true;
                notificationBar.text = "Creating test game";
                var data = testing ? "" + bot1.id + "\n" + bot1.id + "\n" : "" + bot1.id + "\n" + bot2.id + "\n";
                $http.post('creategame_b2b', data)
                    .success(function (data, status, headers) {
                        game.getMoves(headers("Location"), testing);
                    })
                    .error(function () {
                        console.error("Couldnt create bot to bot game");
                    });
            },
            getMoves: function (url, testing) {
                $http.get(url)
                    .success(function (data) {
                        notificationBar.active = false;
                        notificationBar.text = "Test game creation success";
                        if(testing){
                            game.moves = data.moves.filter(function (move) {
                                if (move.wasPlayer1) return move;
                            });
                        } else{
                            game.moves = data.moves
                        }
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
                        var player = move.wasPlayer1 ? "a" : "b";
                        var coordinate = player + (move.coord.x * 10 + move.coord.y);
                        if (move.wasShip) {
                            document.getElementById(coordinate).innerHTML = "<img src='static/images/hit.png'/>";
                        } else {
                            document.getElementById(coordinate).innerHTML = "<img src='static/images/miss.png'/>";
                        }
                        move.sunk.forEach(function (move) {
                            coordinate = player + (move.x * 10 + move.y);
                            document.getElementById(coordinate).innerHTML = "<img src='static/images/sunk.png'/>"
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
                    $("#b" + i).html("");

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