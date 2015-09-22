angular
    .module("app", [])
    //The header, basically it will find <page-header></page-header>
    //and replace it with the contents of header.html
    .directive("pageHeader", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/header.html',
            replace: true
        }
    })
    //Allows all controllers etc. to add/remove information from the bot list.
    .service('botService', function ($http) {
        var botList = [];
        //Get the current set of bots (returns an empty list if theres no user)
        function getBots(username){
            if(username != ''){
                return botList;
            } else {
                return [];
            }

        }
        //Add a bot to the list
        function addBot(name) {
            botList.push("name")
        }
        //Get the bot source code (XML representation)
        function getBotSource() {
            console.log("Bot Source requested")
        }
        //Syncs the botlist with the server
        function getAllBotsForCurrentUser() {
            $http.get("userbots/__current_user")
                .success(function (data) {
                    botList = data.collection.items
                })
                .error(function () {
                    console.error("Update Failure")
                });
        }
    })
    .controller("appCtrl", function ($scope, $http, botService) {
        //The reason i have done it this way is so in the
        //html you type app.something, rather than just something.
        //This makes it clearer what the intention behind it is. eg. app.name()
        //Or user.logout

        //The app object
        $scope.app = {
            name: "Star Battle"
        };

        //The user object
        $scope.user = {
            name: "",
            profilePictureUrl: "",
            bots: botService.getBots(),
            //This method is a cheat so that we can use the JSP data
            //that gets added to page context after a user is logged in to populate our user
            initialize: function (username, profilePicture) {
                $scope.user.name = username;
                $scope.user.profilePictureUrl = profilePicture;
            },
            //Logout, this resets the username fields aswell as calling
            //the logout method inside the JSP
            logout: function () {
                $scope.user.name = "";
                $scope.user.profilePictureUrl = "";
                $http.get('logout')
            }
        }
    });