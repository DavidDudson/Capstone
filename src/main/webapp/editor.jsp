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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/editor.css">

    <%--<script type="text/javascript" src="./static/js/lib/blockly/blockly_uncompressed.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/logic.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/loops.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/math.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/lists.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/variables.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/procedures.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/java/customBlocks.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/messages.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/logic.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/loops.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/math.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/lists.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/variables.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/procedures.js"></script>--%>
    <%--<script type="text/javascript" src="./static/js/lib/blockly/blocks/customBlocks.js"></script>--%>

    <%--Compressed files--%>
    <script type="text/javascript" src="./static/js/lib/blockly/blockly_compressed.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/blocks_compressed.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/java_compressed.js"></script>
    <script type="text/javascript" src="./static/js/lib/blockly/msg/js/en.js"></script>

    <script src="./static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/zeroclipboard/2.1.6/ZeroClipboard.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ng-clip/0.2.6/ng-clip.min.js"></script>
    <script src="./static/js/editor.js"></script>
    <script src="./static/js/header.js"></script>
    <script src="./static/js/bots.js"></script>
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
                <sidebar show_save user_bots></sidebar>
            </section>
            <!--Blockly bar-->
            <section class="col-lg-6 col-md-9 col-sm-9 col-xs-9">
                <div class="main-cont-menu">
                    <ul>
                        <li>
                            <button class="btn btn-info btn-lg" ng-click="createNewBot()" name="create_new_bot">
                                New
                            </button>
                        </li>
                        <li>
                            <button id="del" class="btn btn-info btn-lg" name="delete_bot"
                                    ng-click="delete(botSelector.bots[0])"
                                    ng-disabled="!botSelector.bots"> Delete
                            </button>
                        </li>
                        <li>
                            <button id="save" class="btn btn-info btn-lg" ng-click="save()" name="save_bot"
                                    ng-disabled="!botSelector.bots"> Save
                            </button>
                        </li>
                        <li>
                            <button ng-if="!botSelector.bots[0].shared || botSelector.bots[0].shared == false" class="btn btn-info btn-lg" name = "share_bot"
                                    clip-copy="user.bots.share(botSelector.bots[0])"
                                    clip-click="notificationBar.showSuccess('Share link copied to clipboard')"
                                    ng-disabled="!botSelector.bots">
                                Share
                            </button>
                        </li>
                        <li>
                            <button ng-if="!!botSelector.bots[0].shared && botSelector.bots[0].shared == true" class="btn btn-info btn-lg"
                                    ng-click="user.bots.unshare(botSelector.bots[0])" name="unshare_bot">
                                Unshare
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="main-blockly">
                    <div id="blocklyDiv"></div>
                </div>
                <br/>

                <div>
                    <progressbar id="notificationBar" class="progress-striped" name="notification_bar"
                                 ng-class="notificationBar.active"
                                 ng-click="displayErrorModal()"
                                 ng-value="notificationBar.progress"
                                 type="{{notificationBar.type}}">
                        <b>{{notificationBar.text}}</b>
                    </progressbar>
                </div>
            </section>

            <!--Test bar-->
            <section
                    class="col-lg-4 col-lg-offset-0 col-md-5 col-md-offset-5 col-sm-5 col-sm-offset-5 col-xs-5 col-xs-offset-5">
                
                    <div class="test-grid-content"> <!--test-grid-content starts-->
                        

                        <div class="row" style="margin-bottom:15px; margin-left:15px">
                        <div class="sidebar_head">
                            {{!botSelector.bots[0] ? "Select a bot first" : botSelector.bots[0].name}}
                        </div>
                        </div>

                        <div class="row" style="margin-left:15px">
                        <ul class="test_grid">
                            <%for (int i = 0; i < 10; i++) {%>
                            <%for (int j = 0; j < 10; j++) {%>
                            <li id="a<%=i * 10 + j%>"></li>
                            <%}%>
                            <br/>
                            <%}%>
                        </ul>
                    </div>
                    
                
                <div class="row" style="margin-top:15px; margin-left:15px">
                    <div class="test-grid-buttons">
                        <button id="test" class="btn btn-info btn-lg" name="test_bot"
                                ng-click="game.create(botSelector.bots[0], botSelector.bots[0], true)"
                                ng-disabled="!botSelector.bots || botSelector.bots[0].new">
                            Test
                        </button>
                        <button id="restart" class="btn btn-info btn-lg" ng-click="game.restart()" name="restart_bot"
                                ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Restart
                        </button>
                        <button id="reset" class="btn btn-info btn-lg" ng-click="game.reset()" name = "reset_bot"
                                ng-disabled="!botSelector.bots || botSelector.bots[0].new || !game.moves"> Reset
                        </button>
                    </div>
                </div>
                </div> <!--test-grid-content ends-->
            </section>
        </div>
    </div>
</div>
</body>
</html>