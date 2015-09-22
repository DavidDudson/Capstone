<!DOCTYPE html>
<html ng-app="app" ng-controller="appCtrl as app">
<head>

    <title>{{app.name}} Home</title>

    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-social.css">
    <link rel="stylesheet" type="text/css" href="static/css/font-awesome.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src=""></script>
    <script>
    </script>
</head>

<body>

<div style="clear: both"></div>
<br/>

<div class="main container">
    <div class="row">
        <div class="jumbotron">
            <h1>{{app.name}}</h1>
            <h2>{{index.location}}</h2>
        </div>
    </div>
    <div class="row">
        <button ng-click="app.googleLogin()" class="btn btn-block btn-social btn-google-plus">
            <i class="fa fa-google-plus"></i> Sign in with Google
        </button>

        <button ng-click="app.socialLogin(facebook)" class="btn btn-block btn-social btn-facebook">
            <i class="fa fa-facebook"></i> Sign in with Facebook
        </button>

        <button class="btn btn-block" ng-click="app.debugLogin()" value="Login">
            Debug
        </button>
    </div>
</div>
</body>
</html>