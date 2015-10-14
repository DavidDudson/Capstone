<%
    pageContext.setAttribute("screenName", session.getAttribute("userName"));
    pageContext.setAttribute("profilePicture", session.getAttribute("userPicture"));
%>

<!DOCTYPE html>
<html ng-app="app" ng-controller="appCtrl">
<head>
    <title>Star Battle How To</title>
    <link rel="icon" type="image/png" href="static/images/favicon.ico" sizes="32x32">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/howto.css">

    <script src="static/js/lib/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular.min.js"></script>
    <script src="./static/js/about.js"></script>
    <script src="./static/js/header.js"></script>
    <script src="./static/js/build.js"></script>
    <script src="./static/js/notification_bar.js"></script>
    <script src="./static/js/bots.js"></script>
    <script src="./static/js/user.js"></script>

</head>
<body ng-init="createUser('${screenName}','${profilePicture}');">

    <!--Navigation bar-->
    <page-header></page-header>
    <!--End Navigation bar-->

    <div class="main_container">
        <h1>How to code a bot using Star Battle:</h1>
        <p>The aim of the game is to create a bot that sinks all of the enemy space ships as fast as possible. <br />
         To do this, you will have to think like a computer and use some clever ways of destroying the ships! <br />
        </p>
        <p>Below is a guide to get you started: </p>
        <p>
            <ol>
                <li>Begin by clicking the new button <img src="/static/images/newbutton.png" alt="New button" height="32" width="64"> <br /> </li>
                <li>Name your bot <img src="/static/images/name.png" alt="Name" height="128" width="256"> <br /> </li>
                <li>You can use a template of a prebuilt bot </li><br /> 
                <li>Use the toolbox in the editor to add more blocks to make your bot smarter <img src="/static/images/toolbox.png" alt="Toolbox" height="64" width="110"> </li> <br /> 
                <li>Click the save button to save your bot <img src="/static/images/savebutton.png" alt="Save button" height="32" width="64"></li> <br /> 
                <li>You can test the bot using the test grid to the right of the editing area <img src="/static/images/testbutton.png" alt="Test button" height="32" width="64"></li> <br /> 
                <li>If you make a mistake, you can drag the unwanted block onto the rubbish bin in the bottom right <img src="/static/images/rubbishbin.png" alt="Rubbish button" height="50" width="42"></li> <br /> 
                <li>To delete your bot, select the name of the bot and click the delete button <img src="/static/images/deletebutton.png" alt="Delete button" height="32" width="64"><br /> </li>
                <li>When you are ready to battle, head over to the battle page to play against other bots</li>
        </p>

    </div>
</body>
</html>
