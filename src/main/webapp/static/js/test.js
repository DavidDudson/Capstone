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
    .module("app",["ui.bootstrap",'ui.slider'])
    .controller("appCtrl", function (Search, User, Bots, Game, NotificationBar, BotSelector, $scope, $rootScope) {

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
        $rootScope.search = Search($scope.notificationBar);

        $rootScope.select = function (bot) {
            $scope.game.reset();
            $scope.notificationBar.reset();
            $scope.botSelector.select(bot);
        };

    });