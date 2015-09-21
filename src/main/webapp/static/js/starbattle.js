angular
    .module("app",[])
    .controller("appCtrl", function($http){
        this.name = "Star Battle";
        this.userBots = $http.get("userbots/__current_user");
    });