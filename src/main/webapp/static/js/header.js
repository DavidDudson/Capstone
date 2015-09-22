angular
    .module("app")
    .directive("pageHeader", function(){
        return {
            restrict: 'E',
            templateUrl : './static/html/header.html'
        }
    });