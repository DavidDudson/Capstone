<!DOCTYPE html>
<html>
    <head>
        <title>404 Not Found</title>
        <script src="/Security-Module/pages/js/jquery-1.10.2.min.js"></script>
        <script src="/Security-Module/pages/js/bootstrap.min.js"></script>
        <link href="/Security-Module/pages/css/bootstrap.css" rel="stylesheet">
        <link href='/Security-Module/pages/css/bruyere.css' rel='stylesheet' type='text/css'/>
        <script src="/Security-Module/pages/js/authMethod.js"></script>        
        <script type="text/javascript">window.onload = function(){
            showLogoutPane()
            $("#homeURL").attr("href", "/"+$(location).prop('pathname').split('/')[1]+"/index.jsp");
            $("#logoutURL").attr("href", "/"+$(location).prop('pathname').split('/')[1]+"/logout");
        }
        </script>
        <script src="/Security-Module/pages/js/hello.min.js"></script>
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
                <h3>404 Page Not Found</h3>
            </div>
            
            <div class="container" style="padding-left: 1em;padding-top:2em;padding-bottom:2em;">
            <p>The requested page was not found on the server</p>
            
            <a href="" id="homeURL">Return Home</a>
            
            </div>
            
        </section>
       
    </body>
</html>