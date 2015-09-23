<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>

<html ng-app="app" ng-controller="appCtrl">
<head>
    <meta charset="utf-8">
    <title>{{app.name}} Editor</title>
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

    <link rel="stylesheet" type="text/css" href="static/css/editor.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="static/js/app.js"></script>
    <script src="static/js/editor.js"></script>
</head>
<body ng-init="user.initialize('${screenName}','${profilePicture}')">

<toolbox></toolbox>
<blockly-initial></blockly-initial>

<page-header></page-header>

<div class="container_12">
    <div id="content">
        <div id="sidebar_left" class="sidebar left">
            <div id="my_bots" class="sidebar_box">
                <div class="sidebar_box_inner">

                    <div class="sidebar_head">
                        My Bots
                    </div>

                    <div class="sidebar_content">
                        <ul id="userBots" class="list_block" >
                            <li ng-repeat="bot in user.bots">{{bot}}</li>
                            <li ng-if="user.bots.length === 0">
                                No Bots.
                            </li>
                        </ul>
                    </div>

                </div>
            </div>
        </div>

        <div id="main_content" ng-controller="editorCtrl">
            <div class="main-cont-menu">

                <ul>
                    <li>
                        <button class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal"> New </button>
                    </li>
                    <li>
                        <button id="del" class="btn btn-info btn-lg" ng-click="user.bots.delete()" ng-disabled="!user.bots.selected"> Delete </button>
                    </li>
                    <li>
                        <button id="save" class="btn btn-info btn-lg" ng-click="user.bots.save()" ng-disabled="!user.bots.selected"> Save </button>
                    </li>
                </ul>
            </div>
            <div class="main-blockly" ng-init="editor.initialize()">
                <h4 ng-if="!user.bots.selected">Please select a bot or press new</h4>
                <div id="blocklyDiv" ng-if="user.bots.selected != ''" style="height:450px"></div>
            </div>
        </div>

        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Modal Header</h4>
                    </div>
                    <p>name for bot:</p>
                    <input id="botName">

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal" ng-click="user.bots.add('Bob')"> Submit </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>