angular
    .module("app",[])

    .controller("appCtrl", function ($http, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        $rootScope.location = window.location.href.slice(0, - 1);

    });
