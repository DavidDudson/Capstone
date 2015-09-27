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
                    $rootScope.user.bots.list.push(bot)
                },
                delete: function () {
                    $http.delete('delete:' + $rootScope.editor.selectedBot.id)
                        .success(function () {
                            console.log("Delete Success");
                            var index = $rootScope.user.bots.list.indexOf(name);
                            console.log(index);
                            if (index > -1) user.bots.list.splice(index, 1);
                            console.log(user.bots.list);
                        })
                        .error(function () {
                            console.error("Delete failure")
                        });
                },
                update: function () {
                    $http.get("userbots/__current_user")
                        .success(function (data) {
                            $rootScope.user.bots.list = data.collection.items;
                            $rootScope.user.bots.list.forEach(function (bot) {
                                $rootScope.user.bots.src(bot);
                            })
                        })
                        .error(function () {
                            console.error("Update user bots Failure")
                        });
                },
                //Save the current bot
                save: function () {
                    //The data to use as the request body
                    var data = {
                        name: $rootScope.editor.selectedBot.name,
                        language: 'JAVA',
                        share: share,
                        src: Blockly.Java.workspaceToCode($rootScope.editor.workspace, ["notests"])
                    };
                    if (!$rootScope.editor.selectedBot) {
                        $http.put('bots/' + $rootScope.editor.selectedBot.id, data)
                            .success(function () {
                                $rootScope.editor.build.checkStatus();
                            })
                            .error(function () {
                                console.log("Saving bot failed");
                            });

                    } else {
                        $rootScope.editor.selectedBot.new = null;
                        $http.post('bots', data)
                            .success(function () {
                                $rootScope.editor.selectedBot.id = data.id;
                                $rootScope.editor.build.checkStatus();
                            })
                            .error(function () {
                                console.log("Saving bot failed");
                            });
                    }
                    //The actual request

                },
                //Share bot
                share: function () {
                    if ($rootScope.editor.selectedBot.share === true) {
                        $http.post("shareBot", {
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            data: "botId=" + $rootScope.editor.selectedBot.id + "&unshare=true"
                        })
                            .success(function () {
                                $rootScope.editor.selectedBot.share = false
                            })
                            .error(function () {
                                console.error("Bot unhsare fail")
                            })

                    } else {
                        $http.post("shareBot", {
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            data: "botId=" + $rootScope.editor.selectedBot.id
                        })
                            .success(function () {
                                $rootScope.editor.selectedBot.share = true
                            })
                            .error(function () {
                                console.error("Bot share fail")
                            })

                    }
                },
                src: function (bot) {
                    $http.get("bots-src/" + bot.id)
                        .success(function (data) {
                            bot.src = data;
                            bot.xml = data.match(/<xml.*>/);
                            console.log(bot);
                        })
                        .error(function () {
                            console.error("Source could not be loaded for: " + bot.id)
                        });

                },
                nameInBotList: function (name) {
                    var filteredList = $rootScope.user.bots.list.filter(function (bot) {
                        bot.name = name;
                    });
                    return filteredList.length != 0;
                },
                idInBotList: function (id) {
                    var filteredList = $rootScope.user.bots.list.filter(function (bot) {
                        bot.id = id;
                    });
                    return filteredList.length != 0;
                }
            }
        }
        ;

        $rootScope.builtInBots = {
            list: [],
            update: function () {
                $http.get("userbots/builtinbots")
                    .success(function (data) {
                        console.log($rootScope.builtInBots);
                        return $rootScope.builtInBots.list = data.collection.items;
                    })
                    .error(function () {
                        console.error("Update Built in bots Failure")
                    });
            }

        };

    })
;