
angular
    .module("app",[])
    //The header, basically it will find <page-header></page-header>
    //and replace it with the contents of header.html
    .directive("pageHeader", function(){
        return {
            restrict: 'E',
            templateUrl : './static/html/header.html',
            replace : true
        }
    })
    .controller("appCtrl", function($scope,$http){
        //The reason i have done it this way is so in the
        //html you type app.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. app.name()
        //Or user.logout
        $scope.app = {
            name : "Star Battle"
        };

        $scope.user = {
            name : "",
            profilePictureUrl : "",
            bots : [],
            //This method is a cheat so that we can use the JSP data
            //that gets added to page context after a user is logged in to populate our user
            initialize : function(username,profilePicture){
                $scope.user.name = username;
                $scope.user.profilePictureUrl = profilePicture;
                $scope.user.updateBots();
            },
            //Logout, this resets the username fields aswell as calling
            //the logout method inside the JSP
            logout : function(){
                $scope.user.name = "";
                $scope.user.profilePictureUrl = "";
                $scope.user.bots = [];
                $http.get('logout')
            },
            //Get the current users bot list from the server
            updateBots : function() {
                $http.get("userbots/__current_user")
                    .success(function (data) {$scope.user.bots = data})
                    .failure(console.error("Update Failure"));
            }
        };
    });