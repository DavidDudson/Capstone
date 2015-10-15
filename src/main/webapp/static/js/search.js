angular.module("app")
    .factory("Search", SearchService);

function SearchService($http, $rootScope) {

    return function (notificationBar) {
        var search = {
            bots: [],
            find: function (name) {
                if(name.match(/^\s*$/) || '') return;

                var botName = name.split(',')[0];
                var botId = name.split(',')[1];

                $http.get("searchBot", {params: {q: botName}})
                    .success(function (data) {

                        var bot = data.collection.items[0];
                        if (botId) {
                            console.log(data.collection.items);
                            bot = data.collection.items.filter(function (bot) {
                                return bot.id == botId
                            })[0];
                        }


                        if (!bot) {
                            notificationBar.showWarning("Could not find bot:" + botName);
                        } else {
                            notificationBar.showSuccess("Shared bot loaded");
                            var filteredBots = search.bots.filter(function (b) {
                                return b.id == bot.id
                            });

                            if (filteredBots.length < 1) {
                                search.bots.push(bot);
                                $rootScope.select(bot);
                            } else{
                                $rootScope.select(filteredBots[0]);
                            }
                        }
                    })
                    .error(function () {
                        notificationBar.showError("Server Failure: Searched bot could not be retrieved");
                    });
            }
        };

        return search;
    }
}