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
        <p>
            <ol>
                Begin by clicking the new button   <img src="" alt="Smiley face" height="42" width="42"> <br />
                Name your bot <br />
                You can use a template of a prebuilt bot <br />
                Use the toolbox in the editor to add more blocks to make your bot smarter <br />
                Click the save button to save your bot <br />
                You can test the bot using the test grid to the right of the editing area <br />
                If you make a mistake, you can drag the unwanted block onto the rubbish bin in the bottom right <br />
                To delete your bot, select the name of the bot and click the delete button <br />
                When you are ready to battle, head over to the battle page to play against other bots <br />
            </ol>
        </p>

    </div>
</body>
</html>
