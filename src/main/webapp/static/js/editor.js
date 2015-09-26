angular
    .module("app")
    //Blockly toolbox
    .directive("toolbox", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/toolbox.xml',
            replace: true
        }
    })
    //Initial blockly state
    .directive("blocklyInitial", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/blocklyInitial.xml',
            replace: true
        }
    })
    //Basically the Editor "class", it has functions you can call on it etc.
    .controller("editorCtrl", function ($modal, $http, $rootScope, $interval) {

        //Initialize blockly once the dom has finished loading,
        //aka after the toolbox has been injected
        addEventListener('load', function () {
            $rootScope.editor.initialize()
        }, false);

        //The reason i have done it this way is so in the
        //html you type editor.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. editor.save()
        $rootScope.editor = {
            workspace: null,
            workspaceXml: '<xml><xml>',
            selectedBot: null,
            allBots: function () {
                return $rootScope.builtInBots.list.concat($rootScope.user.bots.list)
            },
            //Initialize the blockly workspace
            initialize: function () {
                $rootScope.editor.workspace = Blockly.inject('blocklyDiv', {
                    toolbox: document.getElementById('toolbox'),
                    rtl: false,
                    comments: true,
                    collapse: true,
                    scrollbars: true,
                    grid: {
                        spacing: 25,
                        length: 3,
                        colour: '#ccc',
                        snap: true
                    }
                });
                //TODO, only call this line once a bot has been selected and use the bot src rather than this.
                //Also pull this data from the builtInBot source code, that way it isn't hardcoded here.
                Blockly.Xml.domToWorkspace($rootScope.editor.workspace, document.getElementById('initialBlocklyState'));
                $rootScope.editor.workspace.getBlockById(1).inputList[2].connection.check_ = ["Coordinate"];

            },
            build: {
                //How many bots in queue
                total: 100,
                //position in queue
                position: 100,
                //Whether a build is in progress
                active: '',
                //eg success error etc.
                type: null,
                //The text to show on the bar
                text: 'Press save to build your bot',

                //Updates the progress bar
                update: function (position, pass) {
                    $rootScope.editor.build.position = position;
                    if (position != $rootScope.editor.build.total) {
                        $rootScope.editor.build.active = 'active';
                        $rootScope.editor.build.type = null;
                        $rootScope.editor.build.text =
                            'In queue... ' + $rootScope.editor.build.position + '/' + $rootScope.editor.build.total;
                    } else if (pass) {
                        $rootScope.editor.build.active = '';
                        $rootScope.editor.build.type = 'success';
                        $rootScope.editor.build.text = 'Build Success'
                    } else {
                        $rootScope.editor.build.active = '';
                        $rootScope.editor.build.type = 'error';
                        $rootScope.editor.build.text = 'Build Failure'
                    }
                },
                checkStatus: function () {
                    $http.get('http://localhost:8080/CCapstone/buildStatus/' + $rootScope.editor.selectedBot.id)
                        .success(function () {
                            if (data.done) {
                                if (data.error) {
                                    $rootScope.editor.build.update(100, false)
                                } else {
                                    $rootScope.editor.build.update(100, true)
                                }
                            } else {
                                $rootScope.editor.build.update(data.position, true)
                                $rootScope.editor.build.total = data.queueSize;
                            }
                        })

                }
            },
            //The modal for selecting create new bot options
            modal: {
                //The modal itself
                instance: null,
                //The currently selected bot to use as a template
                selectedBot: {name: 'FirstSquareBot'},
                //When the modal ok button is pressed create a new bot and close modal
                ok: function (name, bot) {
                    $rootScope.editor.modal.instance.dismiss();
                    $rootScope.user.bots.list.push({name: name, src: bot.src, xml: bot.xml, new: true})
                },
                //When the modal cancel button close the model
                cancel: function () {
                    $rootScope.editor.modal.instance.dismiss();
                },
                //Generate and display the modal
                create: function () {
                    $rootScope.editor.modal.instance = $modal.open({
                        animation: true,
                        templateUrl: './static/html/newBotModal.html',
                        size: "small",
                        resolve: {
                            bots: function () {
                                return $rootScope.builtInBots.list;
                            }
                        }
                    });
                }
            },
            game: {
                moves: null,
                state : null,
                position : 0,
                images:{
                    hit: 'static/images/hit.png',
                    miss: 'static/images/miss.png',
                    sunk: 'static/images/sunk.png'
                },
                create: function () {
                    $http.post('creategame_b2b', "" + $rootScope.editor.selectedBot.id + "\n" + $rootScope.editor.selectedBot.id + "\n")
                        .success(function (data, status, headers) {
                            $rootScope.editor.game.getMoves(headers("Location"))
                        })
                        .error(function () {
                            console.error("Couldnt create bot to bot game")
                        })
                },
                getMoves: function (url) {
                    $http.get(url)
                        .success(function (data) {
                            $rootScope.editor.game.moves = data.moves.filter(function (move) {
                                if (move.wasPlayer1) return move
                            });
                            $rootScope.editor.game.run();
                        })
                        .error(function () {
                            console.error("Could retrieve game moves")
                        })
                },
                run: function() {
                    $rootScope.editor.game.state = $interval(function(){
                        var move = $rootScope.editor.game.moves[$rootScope.editor.game.position];
                        if (move) {
                            var posA = "a" + (move.coord.x * 10 + move.coord.y);
                            if (move.wasShip) {
                                document.getElementById(posA).innerHTML += "<img src='static/images/hit.png'/>";
                            } else {
                                document.getElementById(posA).innerHTML += "<img src='static/images/miss.png'/>";
                            }
                        }
                        $rootScope.editor.game.position++;
                    },100,  $rootScope.editor.game.moves.length);
                },
                reset: function(){
                    for(var i =0; i < 100; i++){
                        $("#a"+i).html("");

                    }
                    $rootScope.editor.game.position = 0;
                }
            }
        }
    });
