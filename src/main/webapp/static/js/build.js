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
                console.log(bot.src);
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
            update: function (complete, position, pass) {
                if (!complete) {
                    notificationBar.position = position;
                    notificationBar.showProgress('Position in queue... ' + position)
                } else {
                    if (pass) {
                        notificationBar.showSuccess('Build Success');
                    } else {
                        notificationBar.showWarning('Build Failure');
                    }
                }
            },
            checkStatus: function (bot, buildStatusUrl) {
                var url = buildStatusUrl.replace("/Capstone/", "");
                $http.get(url)
                    .success(function (data) {
                        if (data.done) {
                            if (!data.error) {
                                build.update(true, 100, true);
                            } else {
                                console.error(data.error);
                                build.update(true, 100, false);
                            }
                        } else {
                            build.update(false, data.currentPosition, true);
                            setTimeout(function () {
                                build.checkStatus(bot, buildStatusUrl);
                            }, 250);
                        }
                    })
                    .error(function () {
                        console.error("Failed to check status");
                    });
            }
        };
        return build;
    }
}