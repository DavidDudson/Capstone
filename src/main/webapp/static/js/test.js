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

    });