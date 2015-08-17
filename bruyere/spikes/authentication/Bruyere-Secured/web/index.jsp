<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SaGaCo Project</title>
        <script src="pages/js/ace.js"></script>
        <script src="pages/js/jquery-1.10.2.min.js"></script>
        <script src="pages/js/bootstrap.min.js"></script>
        <script src="pages/js/bootbox.min.js"></script>
        <script src="pages/js/authMethod.js"></script>
        <link href="pages/css/bootstrap.css" rel="stylesheet">
        <link href='pages/css/bruyere.css' rel='stylesheet' type='text/css' />
        <script type="text/javascript">window.onload=function(){showLogoutPane();}</script>
    </head>

    <body>
        <%
            pageContext.setAttribute("screenName", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userName"));
            pageContext.setAttribute("profilePicture", org.apache.shiro.SecurityUtils.getSubject().getSession().getAttribute("userPicture"));
        %>
        
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand">Bruyere SaGaCo Project</a>
                            <div class="profilePictureContent" id="profilePicture3"><img id="profilePictureURL" src="${profilePicture}" alt="Profile Picture" width="40" height="40"></div>
                            <div class="profilePictureContent" id="profilePicture2"><a href="logout">Logout</a></div>
                            <div class="profilePictureContent" id="profilePicture1">${screenName}</div>
                </div>                          
            </div>
        </div>    
        
        <br>
        
        <section>
            <div class="container">
                <div class="newsBox">
                    <div class="newsTitle">Currently Implemented</div>
                    <div class="newsList">PrimeGame</div>    
                    <div class="newsTitle">Planned Additions</div>
                    <div class="newsList">Tic Tac Toe <br> Chinese Checkers<br>FreeCell</div>  
                </div>
                
                
               
                <div class="header">Welcome to Bruyere Project</div> 
                
                <div class="indexContainer">
                    <p>Welcome to the Bruyere Project</p>
                    <p>Login to the system: <a href="pages/login.jsp">Login</a></p>
                    <p>Play the prime game: <a href="pages/primegame/editor.jsp">PrimeGame</a></p>
                    <p>General secure page, secured by the system: <a href="pages/secure/secure.jsp">Secured Page</a></p>
                    <p>Administration page, requires admin role: <a href="pages/secure/admin.jsp">Administration System</a></p>
                
                </div>
               
            </div>
                       
       
        
        </section>
    
    </body>
</html>