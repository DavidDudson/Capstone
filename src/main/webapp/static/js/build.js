angular.module("app")
    .factory("Build", BuildService);

function BuildService($http) {

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
                    src: bot.src
                };
                var buildRequest;
                if (bot.new) {
                    buildRequest = build._saveNewBot(botInformation);
                } else {
                    buildRequest = build._saveExistingBot(botInformation);
                }
                buildRequest.success(function (data) {
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
                        bot.new = false;
                    } else {
                        notificationBar.showWarning(text + 'Build Failure');
                    }
                }
            },
            checkStatus: function (bot, buildStatusUrl) {
                var url = buildStatusUrl.replace("/Capstone/", "");
                $http.get(url)
                    .success(function (data) {
                        if (data.done) {
                            if (!data.error) {
                                build.update(bot, true, 100, true);
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