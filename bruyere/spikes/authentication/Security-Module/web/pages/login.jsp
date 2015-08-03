<!DOCTYPE html>
<html>
    <head>
        <%
            pageContext.setAttribute("socialEnabled", nz.ac.massey.cs.ig.spikes.core.shiroFilter.isEnableSocial());
            pageContext.setAttribute("ldapEnabled", nz.ac.massey.cs.ig.spikes.core.shiroFilter.isEnableLDAP());
        %>
        <title>Bruyere Login</title>
        <script src="js/ace.js"></script>
        <script src="js/jquery-1.10.2.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootbox.min.js"></script>
        <script src="js/hello.min.js"></script>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/font-awesome.css" rel="stylesheet">
        <link href="css/bootstrap-social.css" rel="stylesheet">
        <link href='css/bruyere.css' rel='stylesheet' type='text/css' />
        
        <script type="text/javascript">
        
            var usingLdap = ${ldapEnabled};
            var usingSocial = ${socialEnabled};
            
            /*
                  Initialize the providers for social authentication
                  these can be called apon by simply having a button with an onclick method as follows
                  for example:
                  onclick="hello('google').login()"
                  hello will automatcially handle popup window, authentication and redirects
            */
           
            hello.init({
                google : "311829343476-s1gtiud9p18v2clnetbt7spg8rqqic4t.apps.googleusercontent.com",
                facebook : "839245052801852"
            }, {scope:'email',redirect_uri:'http://goo.gl/3dfbYf'});
            
           
            
            hello.on('auth.login', function(auth){
               hello( auth.network ).api( '/me' ).then( function(r){
                   var token = JSON.stringify(r);
                   $("#socialFormUser").val("socialLoginRequest"); 
                   $("#socialFormMeta").val(token); 
                   $("#socialForm").submit();
                   
                });
            });
            
            window.onload = function (){ 
                hello.logout();
                
                if (usingLdap){
                    if(!usingSocial){
                        $("#ldapLogin").css("width", "100%");
                    }
                    $("#ldapLogin").fadeIn(400);
                }
                
                if (usingSocial){
                    if(!usingLdap){
                        $("#socialLogin").css("width", "100%");
                    }
                    $("#socialLogin").fadeIn(400);
                }  
            }
        </script>
        
        
    </head>
    <body>
        
	<div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand">Bruyere SaGaCo Project</a>  
                </div>
            </div>
        </div>
    
        <br>
    
        <section>
            
        <div class="loginBlockHeader">
            <h3>Login to Bruyere</h3>
        </div>
        
        <div class="loginContainer">
            
            <div class="authFailure">
                <p>${authMessage}</p>
                <c:remove var="authMessage" scope="session" /> 
            </div>
            
           <div id="ldapLogin" class="loginBlock" style="display:none">
                    
                <div class="paddingBlock"></div>
            
                <h5>Login using LDAP Authentication</h5><br>
                
                <!-- This form is for handling LDAP authentication requests-->
                <form name="loginform" action="" method="POST">  
                    Username:<br/><input type="text" name="username" required placeholder="Username" /><br/> 
                    Password: <br/><input type="password" name="password" required placeholder="Password"/><br/>
                    <input type="submit"  value="Login"><br>
                </form>
             
            </div>
            
            <div id="socialLogin" class="loginBlock" style="display:none">
                <div class="paddingBlock"></div>
                
                <h5>Login using social networks</h5><br>
                
                <!-- Login buttons for social authentication -->
                <div class="googlePlusButton">
                    <p><button type="button" onclick="hello('google').login()"><i class="fa fa-google-plus"></i>&nbsp;&nbsp; Sign in with Google</button></p>
                </div>
                     
                <div class="facebookButton">
                    <p><button type="button" onclick="hello('facebook').login()"><i class="fa fa-facebook"></i>&nbsp;&nbsp; Sign in with Facebook</button></p> 
                </div>
                
                <form id="socialForm" name="loginform" action="" method="POST" style="visibility:hidden">
                    <input id="socialFormUser" type="hidden" name="username"/>
                    <input id="socialFormMeta" type="hidden" name="password"/>
                    <input type="submit" id="ss"  value="Login">
                </form>               
                       
            </div>
        </div>
           
    </section>
    	
</body>
</html>