angular.module("app")
    .factory("Build", BuildService);

function BuildService($http) {

    return function (bot, notificationBar) {
        var build = {

            //Rests the build to default,
            reset: function () {
                notificationBar.reset();
            },
            start: function () {
                console.log("Start");
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
            update: function (position, pass) {
                console.log(position, notificationBar.position);
                if (position === -1) {
                    notificationBar.position = notificationBar.total;
                } else {
                    notificationBar.position = position;
                }

                if (notificationBar.position !== notificationBar.total) {
                    notificationBar.type = null;
                    notificationBar.text = 'In queue... ' + notificationBar.position + '/' + notificationBar.total;
                } else if (pass) {
                    notificationBar.type = 'success';
                    notificationBar.text = 'Build Success';
                } else {
                    notificationBar.type = 'failure';
                    notificationBar.text = 'Build Failure';
                }
            },
            checkStatus: function (bot, buildStatusUrl) {
                var url = buildStatusUrl.replace("/Capstone/", "");
                $http.get(url)
                    .success(function (data) {
                        if (data.done) {
                            build.getResults(bot, data.resultsURL);
                        } else {
                            build.update(data.currentPosition, true);
                            notificationBar.total = data.queueSize;
                            build.checkStatus(bot);
                        }
                    })
                    .error(function () {
                        console.error("Failed to check status");
                    });
            },
            getResults: function (bot, resultsUrl) {
                var url = resultsUrl.replace("/Capstone/", "");
                $http.get(url)
                    .success(function (data) {
                        console.log(data);
                        if (data.error) {
                            build.update(100, false);
                        } else {
                            build.update(100, true);
                        }
                    })
                    .error(function () {
                        console.error("Failed to get Build results");
                    });
            }
        };

        build.start();
        return build;
    }
}