angular.module("app")
    .factory("Bots", BotService);

// Makes a user, Requires the BotService
// Methods with an _ in front should be considered private.
function BotService($http, Build, $rootScope) {

    //The name is simply the request name to actually retrieve the bots from the server
    return function (name, notificationBar) {
        var bots = {
            loaded: false,
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
                    return bots._removeFromList(bot);
                } else {
                    return bots._removeFromServer(bot);
                }
            },
            _removeFromServer: function (bot) {
                $http.delete('delete/' + bot.id)
                    .success(function () {
                        if (bot.dirty) {
                            $rootScope.user.unsavedBots--;
                        }
                        return bots._removeFromList(bot);
                    })
                    .error(function () {
                        notificationBar.showError("Server Failure: " + bot.name + " Deletion Failed")
                    });
            },
            _removeFromList: function (bot) {
                var index = bots.list.indexOf(bot);
                if (index > -1) {
                    bots.list.splice(index, 1);
                    if (bot.dirty) {
                        $rootScope.user.unsavedBots--;
                    }
                    return true;
                } else {
                    notificationBar.showError("Server Failure: " + bot.name + " deletion Failure: Bot wasn't in list");
                    return false;
                }
            },
            update: function () {
                $http.get("userbots/" + name)
                    .success(function (data) {
                        data.collection.items.forEach(function (bot) {
                            bots.add(bot);
                            bots._addSource(bot);
                            bots.loaded = true;
                        });
                        console.log(data.collection.items);
                    })
                    .error(function () {
                        notificationBar.showError("Server Failure: Update user bots Failure");
                    });
            },
            _addSource: function (bot) {
                $http.get("bots-src/" + bot.id)
                    .success(function (data) {
                        bot.src = data;
                        bot.xml = data.match(/<xml.*>/);
                    })
                    .error(function () {
                        notificationBar.showError("Server Failure: Source could not be loaded for: " + bot.id);
                    });
            },
            updateSource: function (bot, src) {
                bot.blocklySrc = src;
                if (bot.src != bot.blocklySrc) {
                    if (!bot.dirty) {
                        bot.dirty = true;
                        $rootScope.user.unsavedBots++;
                    }
                }
                bot.xml = src.match(/<xml.*>/);
            },
            save: function (bot) {
                if (notificationBar.active && notificationBar.type == '') return;
                var build = Build(notificationBar);
                build.start(bot);
            },
            unshare: function (bot) {
                $.post("shareBot", {botId: bot.id, unshare: true});

                bot.shared = false;
            },
            share: function (bot) {
                $.post("shareBot", {botId: bot.id});

                bot.shared = true;

                return bot.name + "," + bot.id;
            }
        };

        bots.update();
        return bots;
    }
}
