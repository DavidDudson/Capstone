angular
    .module("app")
    .controller("testCtrl", function (Bots, Game, NotificationBar, BotSelector, $scope, $rootScope) {

        $scope.notificationBar = NotificationBar();

        //Create a new bot selector that can select 2 bot at a time
        $scope.botSelector = BotSelector(2);

        $scope.game = Game($scope.notificationBar);

        $rootScope.select = function (bot) {
            $scope.game.reset();
            $scope.botSelector.select(bot);
        };

    });