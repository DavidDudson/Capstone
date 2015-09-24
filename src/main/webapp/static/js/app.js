angular
    .module("app", [])
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
            bots: {
                list: [],
                selected: {},
                add: function (bot) {
                    $rootScope.user.bots.list.push(bot)
                },
                remove: function () {
                    $http.delete('delete/' + $rootScope.user.bots.selected.id)
                        .success(function () {
                            var index = $rootScope.user.bots.list.indexOf(name);
                            if (index > -1) user.bots.list.splice(index, 1);
                        })
                        .error(function () {
                            console.error("Delete failure")
                        });
                },
                update: function () {
                    $http.get("userbots/__current_user")
                        .success(function (data) {
                            $rootScope.user.bots.list = data.collection.items;
                            $rootScope.user.bots.list.forEach(function(bot){
                                bot.metadata = $rootScope.user.bots.getMeta(bot.id)
                            })
                        })
                        .error(function () {
                            console.error("Update user bots Failure")
                        });
                },
                getMeta: function(id){
                    $http.get("bot-data/" + id)
                        .success(function (data) {
                           return data
                        })
                        .error(function () {
                            console.error("MetaData could not be loaded for: " + id)
                        });

                }
            },
            //This method is a cheat so that we can use the JSP data
            //that gets added to page context after a user is logged in to populate our user
            initialize: function (username, profilePicture) {
                $rootScope.user.name = username;
                $rootScope.user.profilePictureUrl = profilePicture;
                $rootScope.user.bots.update();
                $rootScope.builtInBots.update();
            }
        };

        $rootScope.builtInBots = {
            list: [],
            update: function () {
                $http.get("userbots/builtinbots")
                    .success(function (data) {
                        console.log($rootScope.builtInBots);
                        return $rootScope.builtInBots = data.collection.items;
                    })
                    .error(function () {
                        console.error("Update Built in bots Failure")
                    });
            }

        };

    });