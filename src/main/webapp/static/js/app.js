angular
    .module("app", ['ui.bootstrap', 'ui.bootstrap.showErrors'])
    .config(['showErrorsConfigProvider', function (showErrorsConfigProvider) {
        showErrorsConfigProvider.showSuccess(true);
    }])
    //The header, basically it will find <page-header></page-header>
    //and replace it with the contents of header.html
    .directive("pageHeader", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/header.html',
            replace: true
        }
    })
    .controller("appCtrl", function ($http, $rootScope) {
        //The reason i have done it this way is so in the
        //html you type app.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. app.name()
        //Or user.logout

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //The user object
        $rootScope.user = {

            name: "",
            profilePictureUrl: "",
            //This method is a cheat so that we can use the JSP data
            //that gets added to page context after a user is logged in to populate our user
            initialize: function (username, profilePicture) {
                $rootScope.user.name = username;
                $rootScope.user.profilePictureUrl = profilePicture;
                $rootScope.user.bots.update();
                $rootScope.builtInBots.update();
            },
            //The bots object, contains all the bot functionality
            bots: {
                list: [],
                add: function (bot) {
                    $rootScope.user.bots.list.push(bot);
                },
                delete: function () {
                    if ($rootScope.editor.selectedBot.new) {
                        var index = $rootScope.user.bots.list.indexOf($rootScope.editor.selectedBot);
                        if (index > -1) {
                            $rootScope.editor.reset();
                            $rootScope.user.bots.list.splice(index, 1);
                            if ($rootScope.user.bots.list.length === 0) {
                                $rootScope.editor.loadBlocklyDiv(null);
                            } else {
                                $rootScope.user.bots.select($rootScope.user.bots.list[0])
                            }
                        } else {
                            console.error("Bot name wasn't in list");
                        }
                    } else {
                        $http.delete('delete/' + $rootScope.editor.selectedBot.id)
                            .success(function () {
                                console.log("Delete Success");
                                var index = $rootScope.user.bots.list.indexOf($rootScope.editor.selectedBot);
                                if (index > -1) {
                                    $rootScope.editor.reset();
                                    $rootScope.user.bots.list.splice(index, 1);
                                    if ($rootScope.user.bots.list.length === 0) {
                                        $rootScope.editor.loadBlocklyDiv(null);
                                    } else {
                                        $rootScope.user.bots.select($rootScope.user.bots.list[0])
                                    }
                                } else {
                                    console.error("Bot name wasn't in list");
                                }
                            })
                            .error(function () {
                                console.error("Delete failure");
                            });
                    }
                },
                update: function () {
                    $http.get("userbots/__current_user")
                        .success(function (data) {
                            $rootScope.user.bots.list = data.collection.items;
                            $rootScope.user.bots.list.forEach(function (bot) {
                                $rootScope.user.bots.src(bot);
                            });
                        })
                        .error(function () {
                            console.error("Update user bots Failure");
                        });
                },
                //Save the current bot
                save: function () {
                    //The data to use as the request body
                    var botSrc = Blockly.Java.workspaceToCode($rootScope.editor.workspace, ["notests"]);
                    console.log(botSrc);
                    var data = {
                        name: $rootScope.editor.selectedBot.name,
                        language: 'JAVA',
                        share: share,
                        src: botSrc
                    };
                    if ($rootScope.editor.selectedBot.new) {
                        $http.post('bots', data)
                            .success(function (data) {
                                $rootScope.editor.selectedBot.new = null;
                                $rootScope.editor.selectedBot.id = data.botId;
                                $rootScope.editor.build.checkStatus();
                            })
                            .error(function () {
                                console.log("Saving bot failed");
                            });
                        //Update the bot if its not new
                    } else {
                        $http.put('bots/' + $rootScope.editor.selectedBot.id, data)
                            .success(function () {
                                $rootScope.editor.build.checkStatus();
                            })
                            .error(function () {
                                console.log("Saving bot failed");
                            });

                    }
                },
                //Share bot
                share: function () {
                    if ($rootScope.editor.selectedBot.share === true) {
                        $http.post("shareBot", {
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            data: "botId=" + $rootScope.editor.selectedBot.id + "&unshare=true"
                        })
                            .success(function () {
                                $rootScope.editor.selectedBot.share = false;
                            })
                            .error(function () {
                                console.error("Bot unhsare fail")
                            });

                    } else {
                        $http.post("shareBot", {
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            data: "botId=" + $rootScope.editor.selectedBot.id
                        })
                            .success(function () {
                                $rootScope.editor.selectedBot.share = true;
                            })
                            .error(function () {
                                console.error("Bot share fail");
                            })

                    }
                },
                src: function (bot) {
                    $http.get("bots-src/" + bot.id)
                        .success(function (data) {
                            bot.src = data;
                            bot.xml = data.match(/<xml.*>/);
                        })
                        .error(function () {
                            console.error("Source could not be loaded for: " + bot.id);
                        });

                },
                select: function (bot) {
                    if ($rootScope.editor.selectedBot) {
                        $rootScope.editor.selectedBot.src = Blockly.Java.workspaceToCode($rootScope.editor.workspace, ["notests"]);
                        $rootScope.editor.selectedBot.xml = $rootScope.editor.selectedBot.src.match(/<xml.*>/);
                    } else {
                        $rootScope.editor.loadBlocklyDiv(document.getElementById('toolbox'));
                    }
                    $rootScope.editor.reset();
                    $rootScope.editor.build.reset();
                    $rootScope.editor.game.hardReset();
                    $rootScope.editor.selectedBot = bot;
                    $rootScope.editor.xml = bot.xml;
                    Blockly.mainWorkspace.clear();
                    $rootScope.editor.switchWorkspace();
                }
            }

        };

        $rootScope.builtInBots = {
            list: [],
            update: function () {
                $http.get("userbots/builtinbots")
                    .success(function (data) {
                        var botList = data.collection.items;

                        botList.forEach(function (bot) {
                            $rootScope.builtInBots.src(bot);
                        });

                        return $rootScope.builtInBots.list = botList;
                    })
                    .error(function () {
                        console.error("Update Built in bots Failure");
                    });
            },
            src: function (bot) {
                $http.get("bots-src/" + bot.id)
                    .success(function (data) {
                        bot.src = data;
                        bot.xml = data.match(/<xml.*>/);
                    })
                    .error(function () {
                        console.error("Source could not be loaded for: " + bot.id);
                    });

            },

        };

    });