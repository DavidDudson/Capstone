
//function getUserBots($http){
//    var bots = [];
//    $http.get("userbots/__current_user")
//        .success(function(data){bots = data});
//    return bots;
//}

angular
    .module("app",[])
    .controller("appCtrl", function($http){
        this.name = "Star Battle";
        this.debug = false;
        this.user = {
            "name" : "",
            "profilePictureUrl" : "",
            "bots" : name === "" ? [] : getUserBots()
        };

        this.socialLogin = function(type){
            $http.post('SocialLogin',
                "loginType=" + encodeURIComponent(type) +
                "&callbackurl=" + encodeURIComponent("http://localhost:8080/Capstone/") +
                "&baseURL=" + encodeURIComponent("")
            )
        };

        this.debugLogin = function(){
            $http.post('login', {
                "username" : "Debug",
                "password" : "Debug"
            })
        };

    });