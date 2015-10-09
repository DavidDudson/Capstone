<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>

<html ng-app="app" ng-controller="appCtrl">
<head>
    <title>Star Battle Editor</title>
    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/editor.css">

    <script type="text/javascript" src="./static/js/lib/blockly/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/logic.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/loops.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/math.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/text.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/lists.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/variables.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/procedures.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java/customBlocks.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/messages.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/logic.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/loops.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/math.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/text.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/lists.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/variables.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/procedures.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks/customBlocks.js"></script>

    <script src="./static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="./static/js/editor.js"></script>
    <script src="./static/js/header.js"></script>
    <script src="./static/js/bots.js"></script>
    <script src="./static/js/ship.js"></script>
    <script src="./static/js/user.js"></script>
    <script src="./static/js/sidebar.js"></script>
    <script src="./static/js/bot_selector.js"></script>
    <script src="./static/js/notification_bar.js"></script>
    <script src="./static/js/build.js"></script>
    <script src="./static/js/game.js"></script>
    <script src="./static/js/lib/showErrors.min.js"></script>
</head>
<body ng-init=" createBuiltInBots();createUser('${screenName}','${profilePicture}');">

<!--Navigation bar-->
<page-header></page-header>
<!--End Navigation bar-->

<toolbox></toolbox>
    <div class="container-lg-1400 container-md-992 container-sm-768 container-xs-767">
        <div class="row">
    <!--Main container-->
    <div class="main_container">
        <!--Left bar-->
        <section class="col-lg-2 col-md-3 col-sm-3">
        <sidebar user_bots></sidebar>
    </section>
        <!--Blockly bar-->
        <section class="col-lg-6 col-md-9 col-sm-9 col-xs-9">
                <div class="main-cont-menu">
                    <ul>
                        <li>
                            <button class="btn btn-info btn-lg" ng-click="createNewBot()">
                                New
                            </button>
                        </li>
                        <li>
                            <button id="del" class="btn btn-info btn-lg"
                                    ng-click="delete(botSelector.bots[0])"
                                    ng-disabled="!botSelector.bots"> Delete
                            </button>
                        </li>
                        <li>
                            <button id="save" class="btn btn-info btn-lg" ng-click="save()"
                                    ng-disabled="!botSelector.bots"> Save
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="main-blockly">
                    <div id="blocklyDiv"></div>
                </div>
                <br/>

                <div>
                    <progressbar id="notificationBar" class="progress-striped"
                                 ng-class="notificationBar.active"
                                 ng-click="displayErrorModal()"
                                 ng-value="notificationBar.progress"
                                 type="{{notificationBar.type}}">
                        <b>{{notificationBar.text}}</b>
                    </progressbar>
                </div>
        </section>

        <!--Test bar-->
        <section class="col-lg-4 col-lg-offset-0 col-md-5 col-md-offset-3 col-sm-5 col-sm-offset-3 col-xs-5 col-xs-offset-3">
            <div class="sidebar_head">
                {{botSelector.getBots[0] ? "Select a bot first" : botSelector.getBots[0].name}}
            </div>
            <div class="test-grid-content">
                <ul class="test_grid">
                    <%for (int i = 0; i < 10; i++) {%>
                    <%for (int j = 0; j < 10; j++) {%>
                    <li id="a<%=i * 10 + j%>"></li>
                    <%}%>
                    <br/>
                    <%}%>
                </ul>
            </div>
            <div class="test-grid-buttons">
                <button id="test" class="btn btn-info btn-lg"
                        ng-click="game.create(botSelector.bots[0], botSelector.bots[0], true)"
                        ng-disabled="!botSelector.bots || botSelector.bots[0].new">
                    Test
                </button>
                <button id="restart" class="btn btn-info btn-lg" ng-click="game.restart()"
                        ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Restart
                </button>
                <button id="reset" class="btn btn-info btn-lg" ng-click="game.reset()"
                        ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Reset
                </button>
            </div>
        </section>
    </div>
</div>
</div>
</body>
</html>