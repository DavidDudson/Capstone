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
    .service('userBots', function(){
        this.bots = {
            list : [],
            //Get all bots
            get : function(username) {
                return username != '' ? botList : [];
            },
            //Add a bot
            add : function(name) {
                bots.list.push(name);
            },
            //Remove a bot
            remove : function(name){
                $http.delete('delete/' + $scope.editor.selectedBot)
                    .success(function () {
                        var index = bots.list.indexOf(name);
                        if (index > -1) bots.list.splice(index, 1);
                    })
                    .error(function () {
                        console.error("Delete failure")
                    });
            },
            //Reset the bot list to all those that are on the server
            update : function(){
                $http.get("userbots/__current_user")
                    .success(function (data) {
                        bots.list = data.collection.items
                    })
                    .error(function () {
                        console.error("Update Failure")
                    });
            }
        };
    })
    .controller("appCtrl", function ($scope, $http, userBots) {
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
            bots: userBots.bots.get(name),
            //This method is a cheat so that we can use the JSP data
            //that gets added to page context after a user is logged in to populate our user
            initialize: function (username, profilePicture) {
                $scope.user.name = username;
                $scope.user.profilePictureUrl = profilePicture;
                userBots.bots.update();
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