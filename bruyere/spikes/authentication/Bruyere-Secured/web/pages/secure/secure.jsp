<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editor</title>      
        <script data-main="../js/main-editor" src="../js/require.js"></script>
        <script src="../js/ace.js"></script>
        <script src="../js/jquery-1.10.2.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/bootbox.min.js"></script>
        <link rel="shortcut icon" type="image/x-icon" href="pipe.png" />
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href='../css/bruyere.css' rel='stylesheet' type='text/css' />
        <script src="../js/authMethod.js"></script>
        <script src="../js/hello.min.js"></script>
        
        <script type="text/javascript">window.onload = function(){showLogoutPane();};</script>
    </head>
    <body>
        <%
            pageContext.setAttribute("screenName", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userName"));
            pageContext.setAttribute("profilePicture", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userPicture"));
        %>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand" href="/primegame">PrimeGame</a>  
                    <ul class="nav">
                        <li class="active"><a href="editor.html">Editor</a></li>
                        <li><a href="test.html">Test</a></li>
                        <li><a href="tournament.html">Tournaments</a></li>              
                    </ul>
                        <div class="profilePictureContent" id="profilePicture3"><img id="profilePictureURL" src="${profilePicture}" alt="Profile Picture" width="40" height="40"></div>
                    <div class="profilePictureContent" id="profilePicture2"><a href="../../logout">Logout</a></div>
                    <div class="profilePictureContent" id="profilePicture1">${screenName}</div>
                </div>
            </div>
        </div>
        <section>
            <div class="programBox">
                <div class="listTitle">My Bots</div>
	            <div id="programList" class="programList">
	                Loading...
	            </div>
            </div>
		<div id="controls">     
            <button id="btnNew" class="btn btn-primary" type="button"><i class="icon-file icon-white"></i> New</button>
            <button id="btnDelete" class="btn btn-primary" type="button"><i class="icon-trash icon-white"></i> Delete</button>
            <button id="btnSave" class="btn btn-primary" type="button"><i class="icon-download-alt icon-white"></i> Save + Compile</button>
            <button id="btnUndo" class="btn btn-primary" type="button"><i class="icon-circle-arrow-left icon-white"></i> Undo</button>
            <button id="btnRedo" class="btn btn-primary" type="button"><i class="icon-circle-arrow-right icon-white"></i> Redo</button>
            <button id="btnProperties" class="btn btn-primary" type="button"><i class="icon-list icon-white"></i> Properties</button>
        </div>
            <pre id="editor"></pre>
            <div id="output"></div>
        </section>
                
    </body>
</html>
