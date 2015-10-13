angular
    .module("app", [])

    .controller("appCtrl", function ($http, $rootScope) {

        //The app object
        $rootScope.app = {
            name: "Star Battle"
        };

        $rootScope.getLocation = function(){
            if(window.location.href.indexOf("index.jsp") > -1){
                return window.location.href.slice(0, -10)
            } else{
                return window.location.href.slice(0, -1);
            }
        };

        $rootScope.location = $rootScope.getLocation();

    });
