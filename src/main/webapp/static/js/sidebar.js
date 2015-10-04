angular
    .module("app")
    // Define the sidebar, note: =? means that the parameter is optional,
    // @ just means it must be there. See the below valid examples
    // eg <sidebar botSelector="someBotSelector"> (User bots would not show up)
    // or <sidebar botSelector="someBotSelector" showUserBots>
    .directive("sidebar", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/sidebar.html',
            replace: true,
            //Define the variables used by this directive
            scope: {
                showBuiltInBots: "=?",
                showUserBots: "=?",
                showSharedBots: "=?",
                builtInBots: "=?",
                userBots: "=?",
                botSelector: "="
            },
            // Declare Variables on directive load
            // The eval simply says to evaluate the expression inside the attribute,
            // rather than passing it as a static string/number
            link: function(scope, element, attrs){

                scope.showUserBots = 'userBots' in attrs;
                scope.showBuiltInBots = 'built_in_bots' in attrs;
                scope.showSharedBots = 'shared_bots' in attrs;
                scope.botSelector =  scope.$eval(attrs.botSelector);
            }
        };
    })
    //Define what a bot group is, require all attributes
    .directive("botGroup", function () {
        return {
            restrict: 'E',
            templateUrl: './static/html/botGroup.html',
            replace: true,
            scope: {
                group_bots : "=",
                group_name : "=",
                selector : "="
            },
            link: function(scope, element, attrs) {
                scope.group_bots = scope.$eval(attrs.bots);
                scope.group_name = attrs.name;
                scope.selector   = scope.$eval(attrs.botSelector);
            }
        };
    });