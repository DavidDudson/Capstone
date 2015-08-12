<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>StarBattle Test Zone</title>
    <meta name="viewport" content="width=device-width, maximum-scale=1.0, minimum-scale=1.0, initial-scale=1.0" />
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="static/js/jquery-1.7.2.min.js"></script>
    <script src="static/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="static/js/gameState.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link rel="stylesheet" type="text/css" href="static/css/grid.css">
    <script>
        $("#DemoGameRequest").click(makeBotGame());
        function makeBotGame(){
            var url = "creategame_b2b";
            var bot1Id = "FirstSquareBot";
            var bot2Id = "FirstSquareBot";
            var data = "" + bot1Id + "\n" + bot2Id + "\n";
            var jqxhr = $.ajax({
                url : url,
                type : "POST",
                data : data
            }).done(function(src) {
                // get results
                var url2 = jqxhr.getResponseHeader("Location");
                commons.debug("done creating game, results will be available at " + url2);

                $('#progressModal .modal-title').html("Executing game ...");
                $('#progressModal .progress-bar').css('width', '66%');

                self._fetchGame(url2);
            }).fail(function(jqXHR, textStatus, error) {
                var errorLoc = jqXHR.getResponseHeader("Location-Error");
                commons.handleError(textStatus,"cannot post game");
                commons.notifyUser("Server Error",jqXHR.responseText);
            });
        }
    </script>
</head>
<body>
<button id="DemoGameRequest">Demo a Game Request</button>
<h2>Response:</h2>
<div id="Output"></div>
</body>
</html>