angular
    .module("app",[])
    .controller("appCtrl", function (NotificationBar,User, $scope, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        //The user object
        $rootScope.user = undefined;

        $scope.notificationBar = NotificationBar("Welcome to about");

        $rootScope.createUser = function (username, profilePictureUrl) {
            $rootScope.user = User(username, profilePictureUrl, notificationBar)
        };
    });
