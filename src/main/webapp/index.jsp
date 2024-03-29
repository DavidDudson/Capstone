<%@page import="nz.ac.massey.cs.ig.core.services.Services" %>
<%
    Services services = (Services) application.getAttribute(Services.NAME);
    pageContext.setAttribute("isDebug",services.getConfiguration().isDebug());
%>


<!DOCTYPE html>
<html ng-app="app" ng-init="isDebug=${isDebug}" ng-controller="appCtrl">
<head>
    <title>Star Battle Home</title>
    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" type="text/css" href="static/css/bootstrap-social.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/index.css">

    <script src="./static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="./static/js/index.js"></script>
    <script src="./static/js/header.js"></script>
</head>

<body>
    <div class="main_container">
        <div id="heading">
            <h1>Star Battle <span><img src="static/images/rocketship.png" alt="RocketShip"> </span></h1>
        </div>
    <div class="container">
        <div class="form-signin mg-btm">
            <h3 class="heading-desc">Sign in to Star Battle:</h3>
            <div class="social-box">
                <div class="row">
                    <div class="col-md-12">
                        <div class="facebookButton">
                            <form action="SocialLogin" method="POST">
                                <input type="hidden" name="loginType" value="facebook" />
                                <input type="hidden" name="callbackurl" ng-value="location" />
                                <input type="hidden" name="baseURL" value="" />
                                <button type="submit" class="btn btn-block btn-social btn-facebook" id="facebook_button">
                                    <i class="fa fa-facebook"></i> Sign in with Facebook
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <h4 class="or"> or </h4>

                <div class="row">
                    <div class="col-md-12">
                        <div class="googlePlusButton">
                            <form action="SocialLogin" method="POST">
                                <input type="hidden" name="loginType" value="google" />
                                <input type="hidden" name="callbackurl"  ng-value="location" />
                                <input type="hidden" name="baseURL" value="" />
                                <button type="submit" class="btn btn-block btn-social btn-google-plus" id="google_button">
                                    <i class="fa fa-google"></i> Sign in with Google
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <div ng-if="isDebug" class="debug-mode">
                <form action="login" method="POST">
                    <input type="hidden" name="baseURL" value="" />
                    <input type="hidden" name="username" value="Debug" />
                    <input type="hidden" name="password" value="Debug" />
                    <input type="submit" class="btn btn-block debug_login" id="debug_login"  value="Debug Login"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>