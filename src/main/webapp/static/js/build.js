angular.module("app")
    .factory("Build", BuildService);

function BuildService($http) {

    return function (notificationBar) {
        var build = {
            //How many bots in queue
            total: 100,
            //position in queue
            position: 100,
            //eg success error etc.
            type: null,
            //The text to show on the bar
            text: '',

            //Rests the build to default,
            reset: function () {
                build.total = 100;
                build.type = null;
                build.text = '';
            },
            //Updates the progress bar
            update: function (position, pass) {
                if (position === -1) {
                    build.position = build.total;
                } else {
                    build.position = position;
                }

                if (build.position !== build.total) {
                    build.type = null;
                    build.text = 'In queue... ' + build.position + '/' + build.total;
                } else if (pass) {
                    build.type = 'success';
                    build.text = 'Build Success';
                } else {
                    build.type = 'failure';
                    build.text = 'Build Failure';
                }
            },
            checkStatus: function () {
                $http.get('buildStatus/' + selectedBot.id)
                    .success(function (data) {
                        if (data.done) {
                            if (data.error) {
                                build.update(100, false);
                            } else {
                                build.update(100, true);
                            }
                        } else {
                            build.update(data.currentPosition, true);
                            build.total = data.queueSize;
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