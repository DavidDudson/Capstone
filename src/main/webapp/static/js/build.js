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
                    buildRequest = build._saveNewBot(botInformation)
                } else {
                    buildRequest = build._saveExistingBot(botInformation)
                }
                buildRequest.success(function (data) {
                    build.checkStatus(bot, data.buildStatusURL);
                }).error(function () {
                    console.error("Unable to save Bot")
                })
            },
            _saveNewBot: function (botInformation) {
                return $http.post('bots', botInformation)
            },
            _saveExistingBot: function (botInformation) {
                return $http.put('bots/' + botInformation.id, botInformation)
            },
            //Updates the progress bar
            update: function (complete, position, pass) {
                if (!complete) {
                    notificationBar.postion = postion;
                    notificationBar.type = null;
                    notificationBar.active = true;
                    notificationBar.text = 'Position in queue... ' + notificationBar.position;
                } else {
                    notificationBar.active = false;
                    if (pass) {
                        notificationBar.type = 'success';
                        notificationBar.text = 'Build Success';
                    } else {
                        notificationBar.type = 'failure';
                        notificationBar.text = 'Build Failure';
                    }
                }
            },
            checkStatus: function (bot, buildStatusUrl) {
                var url = buildStatusUrl.replace("/Capstone/", "");
                $http.get(url)
                    .success(function (data) {
                        if (data.done) {
                            if (data.error) {
                                build.update(true, 100, false);
                            } else {
                                build.update(true, 100, true);
                            }
                        } else {
                            build.update(false, data.currentPosition, true);
                            notificationBar.total = data.queueSize;
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