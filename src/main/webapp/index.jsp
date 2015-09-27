<!DOCTYPE html>
<html ng-app="app" ng-controller="appCtrl">
<head>

    <title>{{app.name}} Home</title>

    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-social.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="static/js/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/showErrors.min.js"></script>
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
        </div>
    </div>

    <div class="row">
        <div class="googlePlusButton">
            <form action="SocialLogin" method="POST">
                <input type="hidden" name="loginType" value="google" />
                <input type="hidden" name="callbackurl" value="http://localhost:8080/Capstone" />
                <input type="hidden" name="baseURL" value="" />
                <button type="submit" class="btn btn-block btn-social btn-google-plus">
                    <i class="fa fa-google"></i> Sign in with Google
                </button>
            </form>
        </div>


        <div class="facebookButton">
            <form action="SocialLogin" method="POST">
                <input type="hidden" name="loginType" value="facebook" />
                <input type="hidden" name="callbackurl" value="http://localhost:8080/Capstone" />
                <input type="hidden" name="baseURL" value="" />
                <button type="submit" class="btn btn-block btn-social btn-facebook">
                    <i class="fa fa-facebook"></i> Sign in with Facebook
                </button>
            </form>
        </div>

        <div>
            <form action="login" method="POST">
                <input type="hidden" name="baseURL" value="" />
                <input type="hidden" name="username" value="Debug" />
                <input type="hidden" name="password" value="Debug" />
                <input type="submit" class="btn btn-block"  value="Debug Login"/>
            </form>
        </div>

		</div>
	</div>

</body>
</html>