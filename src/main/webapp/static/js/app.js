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
    .controller("appCtrl", function (User, $http, $rootScope) {
        //The reason i have done it this way is so in the
        //html you type app.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. app.name()
        //Or user.logout

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //The user object
        $rootScope.user = undefined;

        $rootScope.createUser = function(username,profilePictureUrl){
            $rootScope.user = User(username,profilePictureUrl)
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
            }
        }
    });
