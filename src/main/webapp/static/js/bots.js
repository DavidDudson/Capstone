angular.module("app")
    .factory("UserBots", UserBotService);

// Makes a user, Requires the BotService
// Methods with an _ in front should be considered private.
function UserBotService($http) {

    return function () {
        var bots = {
            list: [],
            //Add a bot to the list
            add: function (bot) {
                bots.list.push(bot);
            },
            //Deletes the bot, returns true if it was a success
            delete: function (bot) {
                if (bot.new) {
                    return bots._removeFromList(bot)
                } else {
                    return bots._removeFromServer(bot)
                }
            },
            _removeFromServer: function (bot) {
                $http.delete('delete/' + bot.id)
                    .success(function () {
                        return bots._removeFromList(bot);
                    })
                    .error(function () {
                        console.error("Bot Deletion Failure: Server Deletion Failed")
                    })
            },
            _removeFromList: function (bot) {
                var index = bots.list.indexOf(bot);
                if (index > -1) {
                    bots.list.splice(index, 1);
                    return true;
                } else {
                    console.error("Bot Deletion Failure: Bot wasn't in list");
                    return false;
                }
            },
            update: function () {
                $http.get("userbots/__current_user")
                    .success(function (data) {
                        data.collection.items.forEach(function (bot) {
                            bots._addSource(bot);
                        });
                    })
                    .error(function () {
                        console.error("Update user bots Failure");
                    });
            },
            _addSource: function (bot) {
                $http.get("bots-src/" + bot.id)
                    .success(function (data) {
                        bot.src = data;
                        bot.xml = data.match(/<xml.*>/);
                    })
                    .error(function () {
                        console.error("Source could not be loaded for: " + bot.id);
                    });
            },
            save: function (bot) {
                var botInformation = {
                    id: bot.id,
                    name: bot.name,
                    language: 'JAVA',
                    src: bot.src
                };
                if (bot.new) {
                    bots._saveNewBot(botInformation)
                } else {
                    bots._saveExistingBot(botInformation)
                }
            },
            _saveNewBot: function (botInformation) {
                $http.post('bots', botInformation)
                    .success(function () {
                        return true
                    })
                    .error(function () {
                        console.error("Save Failed: New Bot");
                        return false
                    });
            },
            _saveExistingBot: function (botInformation) {
                $http.put('bots/' + botInformation.id, botInformation)
                    .success(function () {
                        return true
                    })
                    .error(function () {
                        console.error("Saving Failed: Existing bot");
                        return false
                    });
            },
            share: function (bot) {
                $http.post("shareBot", {
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        data: "botId=" + bot.id
                    })
                    .error(function () {
                        console.error("Bot share fail");
                    })
            },
            unshare: function (bot) {
                $http.post("shareBot", {
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        data: "botId=" + bot.id + "&unshare=true"
                    })
                    .success(function () {
                        bot.share = false
                    })
                    .error(function () {
                        console.error("Bot unhsare fail")
                    });
            }
        };

        return bots;
    }
}
