angular
    .module("app",[])

    .controller("appCtrl", function ($http, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

    });
