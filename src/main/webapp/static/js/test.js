angular
    .module("app")
    .controller("testCtrl", function (Bots, Game, NotificationBar, BotSelector, $scope, $rootScope) {

        $scope.notificationBar = NotificationBar();

        //Create a new bot selector that can select 1 bot at a time
        $scope.botSelector = BotSelector(2);

        $rootScope.select = function (bot) {
            $scope.botSelector.select(bot);
        };

        $scope.game = Game($scope.notificationBar);
    });