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
    .run(function ($rootScope, $location) {
        window.onbeforeunload = function () {
            if ($rootScope.user.unsavedBots > 0) {
                return "You have unsaved bots, are you sure you want to leave this page?";
            }
        };
    })
    .controller("appCtrl", function (User, Bots, Game, NotificationBar, BotSelector, $modal, $scope, $rootScope) {
        // Allows debugging in JS console by going MY_SCOPE.somefunctionOrVariable
        window.MY_SCOPE = $scope;

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //Create the build it bots
        $rootScope.createBuiltInBots = function () {
            $rootScope.builtInBots = Bots("builtinbots")
        };

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

        $scope.addChangeListener = function () {
            $scope.workspace.addChangeListener(function () {
                $scope.syncSource();
            });
        };

        $scope.notificationBar = NotificationBar("Click Save or Test");


        $rootScope.createUser = function (username, profilePictureUrl) {
            var unregister = $scope.$watchGroup(["user.bots.loaded", "builtInBots.loaded"], function () {
                if ($rootScope.user.loaded && $rootScope.builtInBots.loaded) {
                    if (!$rootScope.user.bots.list.length > 0) {
                        console.log("Showing Modal");
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

        //Fallback if your browser doesnt have flash to copy
        $scope.fallback = function () {
            window.prompt('Press cmd+c to copy the text below.', $scope.copy());
        };

        //Save the bot
        $scope.save = function () {
            $scope.syncSource();
            $rootScope.user.bots.save($scope.botSelector.bots[0], $scope.notificationBar);
        };

        //Returns a list of all bots
        $scope.allBots = function () {
            var builtIn = $scope.builtInBots.get();
            var user = $scope.user.bots.get();
            return builtIn.concat(user);
        };

        //Syncs the souce of blockly bot in the editor, with the js object
        $scope.syncSource = function () {
            if ($scope.botSelector.bots) {
                $scope.user.bots.updateSource($scope.botSelector.bots[0],
                    Blockly.Java.workspaceToCode($scope.workspace, ["notests"]));
            }
        };

        //In rootscope so it can be accessed from anywhere
        //Select a bot
        $rootScope.select = function (bot) {
            $scope.syncSource();
            $scope.reset();
            $scope.botSelector.select(bot);
            $scope.loadBlocklyDiv(document.getElementById('toolbox'));
            $scope.switchWorkspace();
        };

        //Delete a bot
        $scope.delete = function (bot) {

            $scope.modal = $modal.open({
                animation: true,
                templateUrl: './static/html/deleteBotModal.html',
                controller: function ($scope, $modalInstance, bot) {
                    $scope.bot = bot;
                    $scope.ok = function () {
                        $modalInstance.close();
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {
                    bot: function () {
                        return $scope.botSelector.bots[0]
                    }
                }
            });

            //Delete only if the OK button is pressed
            $scope.modal.result.then(function () {
                $scope.reset();
                $rootScope.user.bots.delete(bot);
            })
        };

        //Show the error modal
        $scope.displayErrorModal = function () {
            if ($scope.notificationBar.type != 'warning') return;

            if ($scope.notificationBar.text.indexOf('Build Failure, Click this bar to show why.') > -1) {
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
                            return $scope.botSelector.bots[0]
                        },
                        error: function () {
                            return $scope.notificationBar.error;
                        }

                    }
                });
            } else {
                $scope.modal = $modal.open({
                    animation: true,
                    templateUrl: './static/html/GameRunErrorModal.html',
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
                            return $scope.botSelector.bots[0]
                        },
                        error: function () {
                            return $scope.notificationBar.error;
                        }

                    }
                });
            }
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
                $rootScope.select(bot);
                $scope.user.bots.save(bot);
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
            $scope.addChangeListener();
        };

        $scope.switchWorkspace = function () {
            Blockly.Xml.domToWorkspace($scope.workspace, Blockly.Xml.textToDom($scope.botSelector.bots[0].xml));
        };
    });