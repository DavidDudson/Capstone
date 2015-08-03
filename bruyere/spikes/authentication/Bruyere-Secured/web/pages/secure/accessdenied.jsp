<!DOCTYPE html>
<html>
    <head>
        <title>Unauthorized</title>
        <script src="../js/jquery-1.10.2.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <link href="../css/bootstrap.css" rel="stylesheet">
        <link href='../css/bruyere.css' rel='stylesheet' type='text/css'/>
        <script src="../js/authMethod.js"></script>        
        <script type="text/javascript">window.onload = function(){
            showLogoutPane()
            $("#homeURL").attr("href", "/"+$(location).prop('pathname').split('/')[1]+"/index.jsp");
            $("#logoutURL").attr("href", "/"+$(location).prop('pathname').split('/')[1]+"/logout");
        }
        </script>
        <script src="../js/hello.min.js"></script>
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
                    <div class="profilePictureContent" id="profilePicture2"><a id="logoutURL" href="">Logout</a></div>
                    <div class="profilePictureContent" id="profilePicture1">${screenName}</div>
                </div>
            </div>
        </div>
        
        <br>
        
        <section>
            <div class="loginBlockHeader">
                <h3>You are not authorized to view the requested page</h3>
            </div>
            
            <div class="container" style="padding-left: 1em;padding-top:2em;padding-bottom:2em;">
            <p>Please log in with an administrator account</p>
            
            <a id="homeURL" href="">Return Home</a>
            
            </div>
            
        </section>
       
    </body>
</html>