<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>

<html ng-app="app" ng-controller="appCtrl">
<head>
    <title>Star Battle Testing</title>

    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/test.css">

    <script src="https://apis.google.com/js/api.js"></script>
    <script src="static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="static/js/lib/jquery-ui.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/lib/slider.js"></script>
    <script src="static/js/test.js"></script>
    <script src="static/js/header.js"></script>
    <script src="static/js/bots.js"></script>
    <script src="static/js/user.js"></script>
    <script src="static/js/sidebar.js"></script>
    <script src="static/js/bot_selector.js"></script>
    <script src="static/js/notification_bar.js"></script>
    <script src="static/js/build.js"></script>
    <script src="static/js/game.js"></script>

</head>
<body ng-init="createUser('${screenName}','${profilePicture}'); createBuiltInBots();">

<!--Navigation bar-->
<page-header></page-header>
<!--End Navigation bar-->


<!--Main container-->
<div class="container-fluid-lg-1400 container-fluid-md-992 container-fluid-sm-768 container-fluid-xs-767" style="margin-left:15px; margin-right:15px">

    <div class="row">

        <section class="col-lg-2 col-md-3 col-sm-3">
            
            <div class="row" style="margin-bottom: 25px;">
            <sidebar user_bots></sidebar>
            </div>
            
            <div class="row">
            <sidebar built_in_bots></sidebar>
            </div>
        </section>

        <section class="col-lg-7 col-lg-offset-0 col-md-9 col-md-pull-0 col-sm-9 col-sm-pull-0">
            <div id="main_content" ><!---main content ends-->

                <div class="row"> <!---Row for the two grids starts-->

                    <section class="col-xs-6"><!---Player1 grid starts-->
                        <div id="player_one" class="">
                            <h3 id="p1Text">{{botSelector.bots[0] ? botSelector.bots[0].name : "Select Bot 1"}} </h3>
                            <ul class="grid_box">
                                <%for (int i = 0; i < 10; i++) {%>
                                <div class="row" style="margin-right:0px; margin-left:0px"> <!--start new grid row-->
                                    <%for (int j = 0; j < 10; j++) {%>
                                    <section class="col-xs-1 square-element" style="width:10%;padding-right:0px; padding-left:0px">
                                        <!--<div id="a<%=i * 10 + j%>" style="width:100%;padding-top:3em">&nbsp;</div>-->
                                        <li class="square-element" id="a<%=i * 10 + j%>" style="width:100%;padding-top:0% ;list-style-type: none;"></li>
                                    </section>
                                    <%}%>
                                </div><!--end new grid row-->
                                <%}%>
                            </ul>
                        </div>
                    </section><!---Player1 grid ends-->

                    <section class="col-xs-6"><!---Player2 grid starts-->
                        <div id="player_two" class="">
                            <h3 id="p2Text">{{botSelector.bots[1] ? botSelector.bots[1].name : "Select Bot 2"}}</h3>
                            <ul class="grid_box">
                                <%for (int i = 0; i < 10; i++) {%>
                                <div class="row" style="margin-right:0px; margin-left:0px"> <!--start new grid row-->
                                    <%for (int j = 0; j < 10; j++) {%>
                                    <section class="col-xs-1 square-element" style="width:10%;padding-right:0px; padding-left:0px">
                                        <!--<div id="a<%=i * 10 + j%>" style="width:100%;padding-top:3em">&nbsp;</div>-->
                                        <li class="square-element" id="b<%=i * 10 + j%>" style="width:100%;padding-top:0% ;list-style-type: none;"></li>
                                    </section>
                                    <%}%>
                                </div><!--end new grid row-->
                                <%}%>
                            </ul>
                        </div>
                    </section><!---Player2 grid emds-->
                </div> <!---Row for the two grids ends-->

                <div class="row status_bar"><!---Row for status bar starts-->
                    <div class="col-xs-12">
                        <div>
                            <progressbar id="notificationBar" class="progress-striped"
                                         ng-class="notificationBar.active"
                                         ng-click="showErrorModal()"
                                         ng-value="notificationBar.progress"
                                         type="{{notificationBar.type}}">
                                <b>{{notificationBar.text}}</b>
                            </progressbar>
                        </div>
                    </div>
                </div><!---Row for status bar starts-->
            </div><!---main content ends-->

        </section>
        <section class="col-lg-3 col-lg-offset-0 col-md-5 col-md-offset-5 col-sm-5 col-sm-offset-5 col-xs-5 col-xs-offset-5>

            <div id="sidebar_right" class="sidebar">

                <div id="game_controls" class="sidebar_box">
                    <div class="right-inner">

                        <div class="sidebar_head">
                            Game Controls
                        </div>

                        <div class="sidebar_content">
                            <div id="controls">
                                <div class="btn-group">
                                    <button type="button" ng-click="game.restart()" class="btn btn-primary"
                                            ng-disabled="!game.moves">
                                        <i class="fa fa-fast-backward"></i></button>
                                    <button type="button" ng-click="game.step_backward()"
                                            ng-disabled="!game.moves"
                                            class="btn btn-primary">
                                        <i class="fa fa-step-backward"></i></button>
                                    <button type="button" ng-click="game.play_pause(botSelector,false)"
                                            ng-disabled="botSelector.bots.length !== 2"
                                            class="btn btn-primary">
                                        <i class="fa"
                                           ng-class="game.inProgress && !game.paused ?  'fa-pause' : 'fa-play'"></i>
                                    </button>
                                    <button type="button" ng-click="game.step_forward()"
                                            ng-disabled="!game.moves"
                                            class="btn btn-primary">
                                        <i class="fa fa-step-forward"></i></button>
                                    <button type="button" ng-click="game.end()" ng-disabled="!game.moves"
                                            class="btn btn-primary">
                                        <i class="fa fa-fast-forward"></i></button>
                                </div>
                            </div>
                            <div>
                                <div ui-slider min="0" max="500" ng-model="game.speed"></div>
                            </div>

                            <h4 class="sidebar_head">{{botSelector.bots[0] ? botSelector.bots[0].name : "Select Bot 1"}}</h4>
                            <div ng-repeat="ship in game.player1ShipList" class="ship_section">
                                <img ng-repeat="coord in ship track by $index" ng-src="{{!coord[2] ? 'static/images/layer2-ship2.png' : coord[2]}}"/>
                            </div>
                            <h4 class="sidebar_head">{{botSelector.bots[1] ? botSelector.bots[1].name : "Select Bot 2"}}</h4>
                            <div ng-repeat="ship in game.player2ShipList" class="ship_section">
                                <img ng-repeat="coord in ship track by $index" ng-src="{{!coord[2] ? 'static/images/layer1-ship1.png' : coord[2]}}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>