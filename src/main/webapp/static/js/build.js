angular.module("app")
    .factory("Build", BuildService);

function BuildService($http, $rootScope) {

    return function (notificationBar) {
        var build = {
            //Rests the build to default,
            reset: function () {
                notificationBar.reset();
            },
            start: function (bot) {
                var botInformation = {
                    id: bot.id,
                    name: bot.name,
                    language: 'JAVA',
                    src: !bot.blocklySrc ? bot.src : bot.blocklySrc
                };
                var buildRequest;
                if (bot.new) {
                    buildRequest = build._saveNewBot(botInformation);
                } else {
                    buildRequest = build._saveExistingBot(botInformation);
                }
                buildRequest.success(function (data) {
                    if(bot.new){
                        bot.id = data.botId;
                        bot.shared = 'false';
                        bot.new = false;
                    }
                    build.checkStatus(bot, data.buildStatusURL);
                }).error(function () {
                    notificationBar.showError("Unable to save Bot");
                });
            },
            _saveNewBot: function (botInformation) {
                return $http.post('bots', botInformation);
            },
            _saveExistingBot: function (botInformation) {
                return $http.put('bots/' + botInformation.id, botInformation);
            },
            //Updates the progress bar
            update: function (bot,complete, position, pass) {

                var text = "Saving " + bot.name + ": ";
                if (!complete) {
                    notificationBar.position = position;
                    notificationBar.showProgress(text + 'Position in queue... ' + position)
                } else {
                    if (pass) {
                        notificationBar.showSuccess(text + 'Build Success');
                    } else {
                        notificationBar.showWarning(text + 'Build Failure, Click this bar to show why.');
                    }
                }
            },
            checkStatus: function (bot, buildStatusUrl) {
                $http.get(buildStatusUrl)
                    .success(function (data) {
                        if (data.done) {
                            if (!data.error) {
                                build.update(bot, true, 100, true);
                                bot.dirty = false;
                                $rootScope.user.unsavedBots--;
                                bot.src = bot.blocklySrc;
                               
                            } else {
                                notificationBar.error = data.error;
                                build.update(bot, true, 100, false);
                            }
                        } else {
                            build.update(bot, false, data.currentPosition, true);
                            setTimeout(function () {
                                build.checkStatus(bot, buildStatusUrl);
                            }, 250);
                        }
                    })
                    .error(function () {
                        notificationBar.error("Saving " + bot.name + ": Failed to check status");
                    });
            }
        };
        return build;
    }
}