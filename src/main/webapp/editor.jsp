<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="nz.ac.massey.cs.ig.core.services.Services" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="static/js/blockly/blockly_uncompressed.js"></script>
    <script type="text/javascript" src="static/js/blockly/java.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/logic.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/loops.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/math.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/text.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/lists.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/variables.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/procedures.js"></script>
    <script type="text/javascript" src="static/js/blockly/java/customBlocks.js"></script>
    <script type="text/javascript" src="static/js/blockly/messages.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/logic.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/loops.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/math.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/text.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/lists.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/variables.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/procedures.js"></script>
    <script type="text/javascript" src="static/js/blockly/blocks/customBlocks.js"></script>

    <script src="static/js/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.4/ui-bootstrap-tpls.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/bots.js"></script>
    <script src="static/js/user.js"></script>
    <script src="static/js/editor.js"></script>
    <script src="static/js/showErrors.min.js"></script>
</head>
<body ng-init="createUser('${screenName}','${profilePicture}');" ng-app="app">

    <!--Navigation bar-->
    <page-header></page-header>
    <!--End Navigation bar-->

    <toolbox></toolbox>

    <div class="container_10">
        <div id="content">

        <!--Main container-->   
        <div class = "container">
        <div class = "row">

             <!--Left bar-->
            <section class = "col-lg-2 col-md-2 col-sm-2 col-xs-2">       
            <div id="" class="">
                <div id="my_bots" class="sidebar_box">
                    <div class="sidebar_box_inner">

                        <div class="sidebar_head">
                            My Bots
                        </div>

                        <div class="sidebar_content">
                            <ul id="userBots" class="list_block">
                                <li ng-repeat="bot in user.bots.list" class="bot"
                                    ng-style="{'background-color': editor.selectedBot == bot ? 'red' : ''}"
                                    ng-click="user.bots.select(bot)">
                                    {{bot.name}}
                                </li>
                                <li class="bot" style="color:darkred" ng-show="user.bots.list.length === 0"> No Bots</li>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>
            </section>
            
            <!--Blockly bar-->
            <section class = "col-lg-6 col-md-8 col-sm-8 col-xs-8">
            <div id="main_content" ng-controller="editorCtrl" style="color:white">
                <div class="main-cont-menu">
                    
                    <ul>
                        <li>
                            <button class="btn btn-info btn-lg" ng-click="editor.modal.create()">
                                New
                            </button>
                        </li>
                        <li>
                            <button id="del" class="btn btn-info btn-lg" ng-click="user.bots.delete()"
                                    ng-disabled="!editor.selectedBot"> Delete
                            </button>
                        </li>
                        <li>
                            <button id="save" class="btn btn-info btn-lg" ng-click="user.bots.save()"
                                    ng-disabled="!editor.selectedBot"> Save
                            </button>
                        </li>
                        <li>
                            <button id="share" class="btn btn-info btn-lg" ng-click="user.bots.share()"
                                    ng-disabled="!editor.selectedBot || editor.selectedBot.new">
                                {{editor.selectedBot.share === true ? "Unshare" : "Share"}}
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
                                 ng-class="editor.build.active"
                                 ng-value="editor.build.progress" type="{{editor.build.type}}"
                                 style="width:100%; height: 40px"><b>{{editor.build.text}}</b></progressbar>
                </div>
            </div>
            </section>
            
            <!--Test bar-->
            <section class = "col-lg-4 col-lg-offset-0 col-md-6 col-md-offset-2 col-sm-6 col-sm-offset-2 col-xs-6">
            <div class="test_grid_box">

                <div class= "row">
                <div class="sidebar_head">
                    {{!editor.selectedBot ? "Select a bot first" : editor.selectedBot.name}}
                </div>
                </div>
                
                <div class= "row">
                <ul class="test_grid">
                    <%for (int i = 0; i < 10; i++) {%>
                        <%for (int j = 0; j < 10; j++) {%>
                            <li id="a<%=i * 10 + j%>"></li>
                        <%}%>
                        <br/>
                    <%}%>

                </ul>
            </div>
                <div class = "row">
                <button id="test" class="btn btn-info btn-lg" ng-click="editor.game.create()" ng-disabled="!editor.selectedBot || editor.selectedBot.new || editor.selectedBot.position == 0" > Test </button>
                <button id="restart" class="btn btn-info btn-lg" ng-click="editor.game.restart()" ng-disabled="!editor.selectedBot || editor.selectedBot.new || !editor.game.moves"> Restart </button>
                <button id="reset" class="btn btn-info btn-lg" ng-click="editor.game.reset()" ng-disabled="!editor.selectedBot || editor.selectedBot.new || !editor.game.moves"> Reset </button>
            </div>
            </div>
            </section>
        </div>

    </div>

</body>
</html>