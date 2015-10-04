angular
    .module("app")
    //Blockly toolbox
    .directive("toolbox", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/toolbox.xml',
            replace: true
        };
    })
    //Basically the Editor "class", it has functions you can call on it etc.
    .controller("editorCtrl", function (Bots, Build, Game, NotificationBar, BotSelector, $http, $scope) {

        $scope.workspace = Blockly.inject('blocklyDiv', {
            toolbox: null,
            rtl: false,
            comments: true,
            collapse: true,
            scrollbars: true,
            grid: {
                spacing: 25,
                length: 3,
                colour: '#ccc',
                snap: true
            },
            zoom: {
                enabled: true
            }
        });

        $scope.builtInBots = Bots("builtinbots");

        $scope.build = undefined;
        $scope.game = undefined;
        //Create a new bot selector that can select 1 bot at a time
        $scope.botSelector = BotSelector(1);

        $scope.allBots = function () {
            return $scope.builtInBots.list.concat($scope.userBots.list);
        };

        $scope.createNewBot = function () {
            $scope.instance = $modal.open({
                animation: true,
                templateUrl: './static/html/newBotModal.html',
                resolve: {
                    bots: allBots()
                }
            });
            //Wait for the modal to be closed then return the bpt that's selected
            $scope.instance.result.then(function (bot) {
                $scope.userBots.add(bot);
                $scope.botSelector.select(bot);
            })
        };

        $scope.reset = function () {
            $scope.game.reset();
            $scope.build.reset();
            $scope.botSelector.reset();
            Blockly.mainWorkspace.clear();
        };

        $scope.loadBlocklyDiv = function (toolbox) {
            $scope.workspace.dispose();
            $scope.workspace = Blockly.inject('blocklyDiv', {
                toolbox: toolbox,
                rtl: false,
                comments: true,
                collapse: true,
                scrollbars: true,
                grid: {
                    spacing: 25,
                    length: 3,
                    colour: '#ccc',
                    snap: true
                },
                zoom: {
                    enabled: true
                }
            });
        };

        $scope.switchWorkspace = function () {
            Blockly.Xml.domToWorkspace($scope.workspace, Blockly.Xml.textToDom(BotSelector.bots[0].xml));
        };
    });