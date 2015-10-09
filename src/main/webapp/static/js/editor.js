angular
    .module("app", ['ui.bootstrap', 'ui.bootstrap.showErrors', 'ngClipboard'])
    .config(['showErrorsConfigProvider', function (showErrorsConfigProvider) {
        showErrorsConfigProvider.showSuccess(true);
    }])
    .config(['ngClipProvider', function (ngClipProvider) {
        ngClipProvider.setPath("//cdnjs.cloudflare.com/ajax/libs/zeroclipboard/2.1.6/ZeroClipboard.swf");
    }])
    //Blockly toolbox
    .directive("toolbox", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/toolbox.xml',
            replace: true
        };
    })
    .
    controller("appCtrl", function (User, Bots, Game, NotificationBar, BotSelector, $modal, $scope, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //The user object
        $rootScope.user = undefined;

        $rootScope.createBuiltInBots = function () {
            $rootScope.builtInBots = Bots("builtinbots")
        };

        $rootScope.builtInBots = undefined;

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

        $scope.notificationBar = NotificationBar("Click Save or Test");


        $rootScope.createUser = function (username, profilePictureUrl) {
            var unregister = $scope.$watchGroup(["user.bots.loaded", "builtInBots.loaded"], function () {
                if ($rootScope.user.loaded && $rootScope.builtInBots.loaded) {
                    if (!$rootScope.user.bots.get() == []) {
                        $scope.createNewBot();
                        unregister();
                    }
                }
            });
            $rootScope.user = User(username, profilePictureUrl, $scope.notificationBar);
            //Wait for user to be created than load new bot modal if the user has no bots
        };

        //Create a new bot selector that can select 1 bot at a time
        $scope.botSelector = BotSelector(1);

        $scope.game = Game($scope.notificationBar);

        $scope.copy = function () {
            var bot = $scope.botSelector.bots[0];
            return JSON.stringify({
                "name": bot.name,
                "src": bot.src,
                "xml": bot.xml,
                "error": $scope.notificationBar.error
            })
        };

        $scope.fallback = function() {
            window.prompt('Press cmd+c to copy the text below.', $scope.copy());
        };

        $scope.save = function () {
            $scope.syncSource();
            $rootScope.user.bots.save($scope.botSelector.getBots()[0], $scope.notificationBar);

        };

        $scope.allBots = function () {
            var builtIn = $scope.builtInBots.get();
            var user = $scope.user.bots.get();
            return builtIn.concat(user);
        };

        $scope.syncSource = function () {
            if ($scope.botSelector.getBots()) {
                $scope.user.bots.updateSource($scope.botSelector.getBots()[0],
                    Blockly.Java.workspaceToCode($scope.workspace, ["notests"]));
            }
        };

        // in rootscope so it can be accessed from anywhere
        $rootScope.select = function (bot) {
            $scope.syncSource();

            $scope.botSelector.select(bot);
            $scope.reset();
            $scope.loadBlocklyDiv(document.getElementById('toolbox'));
            $scope.switchWorkspace();
        };

        $scope.delete = function (bot) {
            $scope.reset();
            $rootScope.user.bots.delete(bot);
        };

        $scope.displayErrorModal = function () {
            if ($scope.notificationBar.type != 'warning') return;
            $scope.modal = $modal.open({
                animation: true,
                templateUrl: './static/html/BotErrorModal.html',
                controller: function ($scope, $modalInstance, bot, error) {
                    $scope.bot = bot;
                    $scope.error = error;

                    $scope.ok = function () {
                        $modalInstance.dismiss('Ok');
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {
                    bot: function () {
                        return $scope.botSelector.getBots()[0]
                    },
                    error: function () {
                        return $scope.copy();
                    }

                }
            });
        };

        $scope.createNewBot = function () {
            var allBots = $scope.allBots();
            $scope.modal = $modal.open({
                animation: true,
                templateUrl: './static/html/newBotModal.html',
                controller: function ($scope, $modalInstance, modal_bots) {
                    $scope.modal_bots = modal_bots;
                    $scope.modal = {
                        selected: $scope.modal_bots[0]
                    };
                    $scope.ok = function (name, bot) {
                        $modalInstance.close({name: name, src: bot.src, xml: bot.xml, new: true});
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {
                    modal_bots: function () {
                        return allBots
                    }
                }
            });

            // Wait for the modal to be closed then return the bot that's selected
            $scope.modal.result.then(function (bot) {
                $scope.user.bots.add(bot);
                $scope.user.bots.save(bot);
                $rootScope.select(bot);
            })
        };

        $scope.reset = function () {
            $scope.game.hardReset();
            $scope.notificationBar.reset();
            Blockly.mainWorkspace.clear();
            $scope.loadBlocklyDiv(null);
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
            Blockly.Xml.domToWorkspace($scope.workspace, Blockly.Xml.textToDom($scope.botSelector.getBots()[0].xml));
        };
    });