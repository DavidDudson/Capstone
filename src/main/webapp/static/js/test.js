function adjustHeight() {
    var myWidth = jQuery('.square-element').width();
    var myString = myWidth + 'px';
    jQuery('.square-element').css('height', myString);
}

// calls adjustHeight on window load
jQuery(window).load(function() {
    adjustHeight();
});

// calls adjustHeight anytime the browser window is resized
jQuery(window).resize(function() {
    adjustHeight();
});


angular
    .module("app",["ui.bootstrap"])
    .controller("appCtrl", function (User, Bots, Game, NotificationBar, BotSelector, $scope, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //The user object
        $rootScope.user = undefined;

        $rootScope.createBuiltInBots = function () {
            $rootScope.builtInBots = Bots("builtinbots")
        };

        $rootScope.builtInBots = undefined;

        $scope.notificationBar = NotificationBar("Select two bots and press the play to Battle");

        $rootScope.createUser = function (username, profilePictureUrl) {
            $rootScope.user = User(username, profilePictureUrl, $scope.notificationBar)
        };


        //Create a new bot selector that can select 2 bot at a time
        $scope.botSelector = BotSelector(2);

        $scope.game = Game($scope.notificationBar);

        $rootScope.select = function (bot) {
            $scope.game.reset();
            $scope.notificationBar.reset();
            $scope.botSelector.select(bot);
        };

        //Show the error modal
        $scope.displayErrorModal = function () {
            if ($scope.notificationBar.type != 'warning') return;
            $scope.modal = $modal.open({
                animation: true,
                templateUrl: './static/html/GameRunErrorModal.html',
                controller: function ($scope, $modalInstance, bot, error) {
                    $scope.bot = bot;
                    $scope.error = error;

                    $scope.ok = function () {
                        $modalInstance.dismiss('Ok');
                    };
                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {
                    bot: function () {
                        return $scope.botSelector.bots[0]
                    },
                    error: function () {
                        return $scope.notificationBar.error;
                    }

                }
            });
        };

    });