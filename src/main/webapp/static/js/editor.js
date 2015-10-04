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
    .controller("editorCtrl", function (Bots, Build, Game, NotificationBar, BotSelector, $modal, $http, $scope) {

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

        $scope.build = undefined;
        $scope.game = undefined;
        //Create a new bot selector that can select 1 bot at a time
        $scope.botSelector = BotSelector(1);
        //Watches the bot list in the botSelector
        $scope.bots = $scope.botSelector.getBots();

        $scope.notificationBar = NotificationBar();
        
        $scope.allBots = function () {
            var builtIn = $scope.builtInBots.get();
            var user = $scope.user.bots.get();
            return builtIn.concat(user);
        };

        $scope.createNewBot = function () {
            var allBots = $scope.allBots();
            $scope.modal = $modal.open({
                animation: true,
                templateUrl: './static/html/newBotModal.html',
                controller: function ($scope, $modalInstance, modal_bots) {
                    $scope.modal_bots = modal_bots;
                    $scope.selectedBot = modal_bots[0];
                    $scope.ok = function (name,bot) {
                        $modalInstance.close({name: name, src: bot.src, xml: bot.xml, new: true});
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {
                    modal_bots : function(){
                        return allBots
                    }
                }
            });

            // Wait for the modal to be closed then return the bot that's selected
            $scope.modal.result.then(function (bot) {
                $scope.user.bots.add(bot);
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
            Blockly.Xml.domToWorkspace($scope.workspace, Blockly.Xml.textToDom($scope.bots[0].xml));
        };
    });