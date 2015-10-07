angular.module("app")
    .factory("Bots", BotService);

// Makes a user, Requires the BotService
// Methods with an _ in front should be considered private.
function BotService($http, Build) {

    //The name is simply the request name to actually retrieve the bots from the server
    return function (name,notificationBar) {
        var bots = {
            list: [],
            get: function () {
                return bots.list;
            },
            //Add a bot to the list
            add: function (bot) {
                bots.list.push(bot);
            },
            //Deletes the bot, just removes it if it wasn't ever saved
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
                        notificationBar.showError("Bot Deletion Failure: Server Deletion Failed")
                    })
            },
            _removeFromList: function (bot) {
                var index = bots.list.indexOf(bot);
                if (index > -1) {
                    bots.list.splice(index, 1);
                    return true;
                } else {
                    notificationBar.showError("Bot Deletion Failure: Bot wasn't in list");
                    return false;
                }
            },
            update: function () {
                $http.get("userbots/" + name)
                    .success(function (data) {
                        data.collection.items.forEach(function (bot) {
                            bots.add(bot);
                            bots._addSource(bot);
                        });
                    })
                    .error(function () {
                        notificationBar.showError("Update user bots Failure");
                    });
            },
            _addSource: function (bot) {
                $http.get("bots-src/" + bot.id)
                    .success(function (data) {
                        bot.src = data;
                        bot.xml = data.match(/<xml.*>/);
                    })
                    .error(function () {
                        notificationBar.showError("Source could not be loaded for: " + bot.id);
                    });
            },
            updateSource: function (bot, src) {
                bot.src = src;
                bot.xml = src.match(/<xml.*>/);
            },
            save: function (bot) {
                var build = Build(notificationBar);
                build.start(bot);
            },
            share: function (bot) {
                $http.post("shareBot", {
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        data: "botId=" + bot.id
                    })
                    .error(function () {
                        notificationBar.showError("Bot share fail");
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
                        notificationBar.showError("Bot unhsare fail")
                    });
            }
        };

        bots.update();
        return bots;
    }
}
