<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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

    <script type="text/javascript" src="static/js/lib/blockly/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/logic.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/loops.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/math.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/text.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/lists.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/variables.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/procedures.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/java/customBlocks.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/messages.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/logic.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/loops.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/math.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/text.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/lists.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/variables.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/procedures.js"></script>
    <script type="text/javascript" src="static/js/lib/blockly/blocks/customBlocks.js"></script>

    <script src="static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/bots.js"></script>
    <script src="static/js/user.js"></script>
    <script src="static/js/sidebar.js"></script>
    <script src="static/js/bot_selector.js"></script>
    <script src="static/js/notification_bar.js"></script>
    <script src="static/js/build.js"></script>
    <script src="static/js/game.js"></script>
    <script src="static/js/editor.js"></script>
    <script src="static/js/lib/showErrors.min.js"></script>
</head>
<body ng-init="createUser('${screenName}','${profilePicture}'); createBuiltInBots();" ng-app="app">

<page-header></page-header>

<toolbox></toolbox>

<div class="container_10" ng-controller="editorCtrl">
    <div id="content">
        <!--Main container-->
        <div class="container">
            <div class="row">
                <!--Left bar-->
                <sidebar bot_selector="botSelector" user_bots></sidebar>
                <!--Blockly bar-->
                <section class="col-lg-6 col-md-8 col-sm-8 col-xs-8">
                    <div id="main_content" style="color:white">
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
                                <li>
                                    <button id="share" class="btn btn-info btn-lg"
                                            ng-click="user.bots.share(botSelector.bots[0])"
                                            ng-disabled="!botSelector.bots || botSelector.bots[0].new">
                                        {{botSelector.bots[0].share === true ? "Unshare" : "Share"}}
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <div class="main-blockly">
                            <div id="blocklyDiv" style="height:450px"></div>
                        </div>
                        <br/>

                        <div>
                            <progressbar class="progress-striped"
                                         ng-class="notificationBar.active"
                                         ng-value="notificationBar.progress" type="{{notificationBar.type}}"
                                         style="width:100%; height: 40px"><b>{{notificationBar.text}}</b></progressbar>
                        </div>
                    </div>
                </section>

                <!--Test bar-->
                <section class="col-lg-4 col-lg-offset-0 col-md-6 col-md-offset-2 col-sm-6 col-sm-offset-2 col-xs-6">
                    <div class="test_grid_box">

                        <div class="row">
                            <div class="sidebar_head">
                                {{!editor.selectedBot ? "Select a bot first" : editor.selectedBot.name}}
                            </div>
                        </div>
                        <div class="row">
                            <ul class="test_grid">
                                <%for (int i = 0; i < 10; i++) {%>
                                <%for (int j = 0; j < 10; j++) {%>
                                <li id="a<%=i * 10 + j%>"></li>
                                <%}%>
                                <br/>
                                <%}%>
                            </ul>
                        </div>
                        <div class="row">
                            <button id="test" class="btn btn-info btn-lg"
                                    ng-click="game.create(botSelector.bots[0], botSelector.bots[0], true)"
                                    ng-disabled="!botSelector.bots || botSelector.bots[0].new || botSelector.bots[0].position == 0">
                                Test
                            </button>
                            <button id="restart" class="btn btn-info btn-lg" ng-click="game.restart()"
                                    ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Restart
                            </button>
                            <button id="reset" class="btn btn-info btn-lg" ng-click="game.reset()"
                                    ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Reset
                            </button>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div>
</body>
</html>